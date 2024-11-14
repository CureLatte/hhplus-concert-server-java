# 서비스 확장에 대한 서비스 설계 문서
*** 

서비스를 운영하다보면 좋은 서비스, 마케팅 전략 등등 을 통해

유저수가 급증하게 되면서 동시에 트래픽이 올라가는 상황에 놓이게 된다.

이때 초반에 시작한 기존의 서비스는 문제가 없었지만 큰 트래픽이 몰리게 되면서

기존의 서비스의 성능이 낮아지는 경우가 생기게 된다.

또는 기존의 서비스에서 조금 더 나은 서비스를 추가할 수 있도록 기획이 되거나

임시로 기존 서비스를 확장해야하는 일이 생기게 된다.

이때 성능을 높이거나 기존서비스를 확장 시키기 위해 기존 서비스를 어떻게 분리를 할지 

고민하게 된다.


이러한 고민 거리를 미리 산정하여 대비를 할 수있도록 이번 보고서에서 작성하려고 한다.


<br>

## 확장 방식 분석 
서비스를 확장하기 위한 방식은 우선 기존의 서비스를 제대로 볼 줄 알아야한다.  
성능 즉 시간이 오래 걸리는 방식에는 서버 하드웨어 성능, 네트워크 성능, 용량 성능 등등 이 존재히자만

이번엔 애플리케이션 단에서 조절할 수 있는 성능 향상을 하려고 한다.

즉, DB 에 걸릴 수 있는 부하를 기반으로 성능을 향상시키는 것이 이번 설계의 중점이 되어야 한다.

그런 관점에서 보자면 우선 DB 성능을 고려해야하는 부분은 크게 2가지로 나뉜다.

* 조회 성능 ( `Select` )
* 쓰기 성능 ( `Create`, `Update`, `Delete`)


조회 성능의 경우는 이전 `Index` 를 활용하여 성능을 높이는 방식이나

`읽기전용 DB` 를 확장하는 등등 해결하라 수 있는 방법이 많다.

그러므로 이번에는 쓰기 성능을 높이는 것이 적절하다고 생각이 된다.

<br>

쓰기 성능중에서도 단순히 작성하는 부분은 문제가 되질 않지만 

`DB` 작성시 항상 조심해야하는 부분은 바로 `Transaction` 이다.

해당 기능은 정확한 정보를 업데이트 하기 위해 `LOCK` 이라는 기능을 이용해 

읽기/ 쓰기를 제한하고 다른 요청들을 기다리거나 `ERROR` 로 처리할 수 있도록 하는 기술이다.


해당 기술 때문에 `Transaction` 이 시작되는 동안은 다른 요청들에게 영향이 가므로 

트랜잭션이 적용된는 범위가 넓게 잡힌다면 병목현상이 일어나는 주요 원인이 된다.

따라서 `Transaction` 을 적절하게 사용하기 위한 범위를 적절하게 나누는 것 부터가 성능을 올릴 수 있는 방법 중 하나이다.



## Transaction 범위를 파악해 보자 
해당 서버에서 `Transaction` 사용하는 쓰기 `API` 리스트 들이다.

* `좌석 예약 API `
* `결제 API`


### 좌석 예약 API
해당 `API` 의 `Transaction` 의 시작은 `ConcertFacade` 에서이다.


``` java
class ConcertFacade{
    // DI
    IConcertService concertService;
    IUserService userService;
    IWaitService waitService;
    
    
    @Transactional // 트랜 잭션의 시작
    public PostReserveSeatResponseDto postReserveSeatV2(Long concertSeatId, String uuid, Long userId){
        try {
            
            Reservation reservation = this.concertService.reserveV2(concertSeatId, userId, uuid); // Service 호출

            return new PostReserveSeatResponseDto(new PostReserveSeatResponseDto.ReservationDto(reservation.id, reservation.status.name(), reservation.concertSeat.number, reservation.concert.getId()));

        } catch( BusinessError businessError){

            log.error("[ConcertFacade] postReserveSeatV2: {}", businessError.getMessage());
            throw businessError;
        }

    }
    

}

```

`Facade` 인데도 불구하고 `Service` 함수는 하나밖에 호출되지 않는다.

이 경우는 굳이 범위를 축소시킬 이유가 없다


### 결제 API 

```java 
public class PaymentFacade {
    PaymentService paymentService;
    UserService userService;
    ConcertService concertService;
    PointService pointService;
    WaitService waitService;

    @Transactional
    public PostPayReservationResponseDto payReservation(Long userId, Long reservationId, int payAmount, String uuid){

        try {

            User user = this.userService.getUser(userId); // 유저 서비스 호출

            Reservation reservation = this.concertService.getReservation(reservationId); // 콘서트 서비스 호출
            Point point = this.pointService.getPoint(user);  // 포인트 서비스 호출

            PointHistory pointHistory = this.pointService.use(point, payAmount); // 포인트 서비스 호출

            Payment payment = this.paymentService.payReservation(user, reservation, pointHistory); // 결제 서비스 호출


            this.waitService.deleteActivateToken(uuid); // 대기열 서비스 호출

            return new PostPayReservationResponseDto(payment != null);

        } catch (BusinessError businessError){
            log.error("[PaymentFacade] payReservation: {}" , businessError.getMessage());
            throw businessError;
        }


    }

}


```
결제 `Facade` 에서는 앞선 `Facade` 와는 다른 도메인의 서비스들을 자주 사용하는 부분이 보인다.

이럴 경우 `Transaction` 의 범위가 `User Table`, `Point Table`, `Concert` 관련 Table 전체가  걸리므로 

`Transaction` 의 영향을 받는 테이블이 길어지므로 

트래픽이 몰리게 되면 해당 `API` 덕분에 병목현상이 발생할 가능성이 높다.




## STEP 1 Transaction 범위 줄이기

`Facade` 를 정리하자면 다음과 같다.

* `------- Transaction 시작 -------`
* `유저 조회` - `UserService` [조회]
* `예약 조회` - `ConcertService` [조회]
* `포인트 조회` - `PointService` [조회]
* `포인트 사용` - `PointService` [생성]
* `예약 서비스 상태 수정` - `ConcertService` [수정]
* `대기열 만료` - `WaitService` [삭제]
* `------- Transaction 끝  -------`


이 중에서 서비스의 가장 중요한 부분은 바로 `결제`을 제대로 생성하는 `포인트 사용`,` 예약 서비스 상태 수정` 이다.

이 부분을 기준으로 위쪽의 조회 로직은 트랜잭션이 걸리지 않더라도 조회가 가능할 것이다.

* 유저 조회
>예약시 유저의 상태가 변경되지도 않고 `Commit`, `Rollback` 이 되어도 그대로 유지가 될 것이다. 
* 예약 조회
>서비스 특성상 하나의 예약은 한사람의 유저에 의해 결정이 되고(동시성 적용) 한 유저가 결제와 예약을 동시에 할 수 없다.  
> 따라서 결제를 시도하려는 예약은 동일 유저일 수밖에 없으며 이미 끝난 상태이므로 트랜잭션을 통해 상태를 업데이트할 이유가 없다.

* 포인트 조회
> 포인트도 동일하게 포인트 사용과 포인트 충전을 한 유저가 동시에 할 수가 없다. 
> 트랜잭션을 통해 바로 업데이트할 이유가 없다.

* 대기열 만료
> 수정을 한 이후 로직인 대기열 만료는 `Commit` 여부에 따라 달라질 수도 있고 
> DB 로 구성할 때는 영향이 있었지만 Redis 를 이용한 걸로 바뀐 이후에는 Transaction 과 무관하게 적용이된다.


따라서 트랜잭션 범위를 다시 수정해본다면



* `유저 조회` - `UserService` [조회]
* `예약 조회` - `ConcertService` [조회]
* `포인트 조회` - `PointService` [조회]
* `------- Transaction 시작 -------`
* `포인트 사용` - `PointService` [생성]
* `예약 서비스 상태 수정` - ConcertService [수정]
* `------- Transaction 끝  -------`
* `대기열 만료`  - `WaitService` [삭제]

이렇게 수정한다면 트랜잭션 범위가 많이 줄 수 있기 때문에 병목현상을 조금 줄일 수 있다.


<br>

### EVENT 등록 
트랜잭션의 범위를 축소함으로써 병목현상을 줄였으나 한가지 더 고려해야할 부분이있다.

바로 `Transaction` 의 결과에 상관 없이 
대기열 만료 서비스인 `WaitService` 에서 `Error` 가 터진다면.. `DB` 는 이미 결제 완료가 되었지만   
`RESPONSE` 는 실패한 것으로 요청 결과가 나가므로 
데이터가 일치하지 않는 문제가 발생한다.

따라서 중요한 트랜잭션이 성공한 이후 해당 부분과 관련이 없는 행위는 결과와 무관하게 끔 처리해야하는데 

이 때 사용한는 것이 `EventHandler`, `EventListener` 이다.

트랜잭션이 끝난 이후에 대기열을 만료하는 부분을 단순히 Event 로서 `Trigger` 를 호출만 한다면 

해당 로직에서는 그 결과에 영향을 받지 않고 결과를 `Return` 할 것이고, 

메인 로직과 분리가 되면서 관심사를 분리하여 더 명확해진 비지니스 로직을 가져갈 수도 있는 장점이 있다.


따라서 `Event` 까지 업데이트 한다면


* `유저 조회` - `UserService` [조회]
* `예약 조회` - `ConcertService` [조회]
* `포인트 조회` - `PointService` [조회]
* `------- Transaction 시작 -------`
* `포인트 사용` - `PointService` [생성]
* `예약 서비스 상태 수정` - ConcertService [수정]
* `------- Transaction 끝  -------`
* `대기열 만료 Event Call` 
*  `return`
* `대기열 만료`  - `WaitService` [삭제]


이제 서비스를 서비스를 확장하더라도 
`Event Trigger` 만 이용한다면 확장도 문제없이 적용할 수 있을 것이다.


#### EX) 결제 정보를 고객사에게 넘기기 위해 외부 API 를 호출 해야한다면..?
* `유저 조회` - `UserService` [조회]
* `예약 조회` - `ConcertService` [조회]
* `포인트 조회` - `PointService` [조회]
* `------- Transaction 시작 -------`
* `포인트 사용` - `PointService` [생성]
* `예약 서비스 상태 수정` - ConcertService [수정]
* `------- Transaction 끝  -------`
* `대기열 만료 Event Call`
* `고객사 API 호출 Event Call`
*  `return`
* `대기열 만료`  - `WaitService` [삭제]
* `고객사 API 호출` 


이런식으로 추가할 수 있다!

<br>

## STEP 2. 보상 트랜잭션 사용하기
이런 방식을 사용하게 된다면 한가지 불편한 상황이 발생한다.

바로 로직 중간에 `Transaction` 을 적용을 해야 하는 것이다.

해당 `Transaction` 을 수동으로 받고 마지막에 `Commit`, `Rollback` 핸들링을 해줘야 하는 것이다. 

이 부분을 해소하려면 각각의 `Transaction` 으로 쪼개는 방식이 있다.

1. `Service` 함수 마다 `Transaction` 을 상용한다.
2. `Service` 에러 시 앞단의 모든 로직을 `Rollback` 하도록 하는 `보상 트랜잭션`을 사용한다.

수정된 `Transaction` 에서도 다음과 같이 진행할 수 있다.

```java

public pay(){
    
    PointHistory pointHistory = this.pointService.use(point, payAmount); // 포인트 서비스 호출
    
    try {
        Payment payment = this.paymentService.payReservation(user, reservation, pointHistory); // 결제 서비스 호출
    } catch (Exception e){
        this.pointService.useRollback(point, payAmount); // 보상 Transaction 진행
    }
    
}


```


즉 최초 생성인 포인트 사용 서비스는 정상적으로 이루어졌지만   
결제내역 상태 변경시 문제가 생겨서 `Rollback` 이 된다면   
앞에서 했던 로직을 전부 삭제하는 `보상 트랜잭션`을 사용한다.

이로써 보상 트랜잭션을 사용하게 된다면 서비스 별로 비지니스 로직을 분리할 수가 있다.

<br>

### STEP 3 MSA

이렇게 분리된 방식을 도멘인 별로 다른 서버를 둔다면 그게 바로 그 유명한 `MSA(Micro Service Architecture)` 라고 불리운다.

해당 서비스를 만약! 도메인 별로 `MSA` 를 구축 한다면 서비스 확장을 하기도 더욱 편리하다.

다만 여려가지 구현과 설정해야할 것이 많다는 것이 단점이다.

간단하게 알아보자면

* 우선 `Event` 기반으로 진행이 되므로 `EDA(Event-Driven-Architecture)` 형식으로 바뀐다.
* `RabbitMQ`, `kafka` 같은 `이벤트 큐`를 사용해야한다.
* 각 서비스별 `Database` 는 한번에 같은 `Transaction` 을 걸수 없으므로 `보상 Transaction` 을 호출해야한다.
* 이벤트를 받지 못할 경우을 대비하여 확인할 수 있는 스케쥴러를 생성한다.
* 구현 방식은 `SAGA 패턴`, `오케스트라 패턴`으로 분리된다.
  


### `SAGA 패턴` ->  각 서비스별로 다음 트랜잭션에 다음 목적지가 결정된다.
> [정상]  
> [A Service] `트랜잭션 시작` -> `A 로직 진행` -> `트랜잭션 종료` ->` B Service 호출`  
> [B Service] `트랜잭션 시작` -> `B 로직 진행` -> `트랜잭션 종료` ->` C Service 호출`  
> [C Service] `트랜잭션 시작` -> `C 로직 진행` -> `트랜잭션 종료` -> `끝`   
> <br>
> [에러 발생]  
> [A Service] `트랜잭션 시작` -> `A 로직 진행` -> `트랜잭션 종료` -> `B Service 호출`     
> [B Service] `트랜잭션 시작` -> `B 로직 진행` -> `트랜잭션 종료` -> `C Service 호출`     
> [C Service] `트랜잭션 시작` -> `C 로직 진행` -> `서비스 에러`-> `트랜잭션 Rollback` -> `B Service 보상 트랜잭션 호출`  
> [B Service] `트랜잭션 시작` -> `B 보상 로직 진행` -> `트랜잭션 종료` -> `A Service 보상 트랜잭션  호출  `   
> [A Service] `트랜잭션 시작` -> `A 보상 로직 진행` ->` 트랜잭션 종료 `-> `끝`


이처럼 `SAGA 패턴`은 서비스안에서 다음 서비스의 목적지가 결정되므로 일일이 이벤트를 따라가면서 진행이된다.

그래서 전체적인 그림은 따로 정리하여야하는 단점이 있지만 

하나의 서비스가 죽더라도 해당 서비스를 이용하지 않는 다른 서비스의 경우 

나머지는 정상 작동이 되는 장점이 있다.


### `오케트스트라 패턴` -> 중앙 집권식으로 이벤트의 전체적인 흐름을 알 기 쉽다.

> [정상]  
> [MAIN Service] `A Service 호출` -> `B Service 호출` -> `C Service 호출 `
> <br>  
> [에러]  
> [MAIN Service] `A Service 호출` -> `B Service 호출` -> `C Service 호출` -> `C Service 에러 발생` -> `A Service 보상 호출` -> `B Service 보상 호출`

단점은 전체 이벤트를 발행할 별도의 서버를 다시 구축해야한다는 점과 해당 서비스가 멈추게  되면 다른 서비스는 별도 이용이 불가하다는 점이다.




## 마무리
정리하자면 현재의 서비스에서의 트랜잭션 범위는 너무 크다 

따라서 서비스 확장을 하기엔 부적합하므로 우선 트랜잭션의 범위를 줄이려면 다음 방식 단계가 예상된다.

### STEP 1
* Transaction 을 범위를 실제 쓰기만 하는 부분으로 줄이고 
* Event 를 통해 메인 로직에 영향이 없도로 비동기로 처리한다.

### STEP 2
보상 트랜잭션을 추가한다면 서비스 별로 분리가 가능하다.

### STEP 3
서비스 별로 분리가 가능하다면 MSA 까지 서비스를 분리하므로서 
서비스 확장을 더 늘릴 수 있다.

### 이런 단계를 통해 앞으로 서비스의 확장을 노려불 수 있다!
