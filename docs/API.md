# API DOCS

## 토큰 발급 API 
***
### Description
유저 토큰 발급

### Request
* `URL` : `/wait/{userId}`
* `METHOD`: `GET`
* `HEADER`
    
    ```json
    {
      "Content-Type": "application/json"
    }
    ```

### Response

* `STATUS`: `200`
    ```json
    { 
      "token": "uuid",
      "status": "wait"
    }
    ```

<br>
<br>


## 예약 가능한 날짜 조회 API
***
### Description
예약 가능한 날짜 조회 

### Request
* `URL` : `/concert/time/{concertId}`
* `METHOD`: `GET`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json",
      "Authorization": "Bearer uuid"
    }
    ```

### Response

* `STATUS`: `200`
    ```json
    { 
      "date": [
        {
            "concert_time_id": 1,
            "start_time": "2024-02-11",
            "max_cnt": 30,
            "left_cnt": 9
        },
        {
            "concert_time_id": 2,
            "start_time": "2024-02-11",
            "max_cnt": 30,
            "left_cnt": 9
        }
      ]   
    }
    ```

* `STATUS`: `400`
    ```json
    { 
      "message": "잘못된 접근 입니다 / 존재하지 않은 콘서트 입니다. / 예약 가능한 날짜가 없습니다"
    }
    ```



## 좌석 조회 API
***
### Description
예약 가능한 좌석 조회

### Request
* `URL` : `/concert/seat/{concertTimeId}`
* `METHOD`: `GET`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json",
      "Authorization": "Bearer uuid"
    }
    ```

### Response

* `STATUS`: `200`
    ```json
    { 
      "seat": [
        {
            "concert_seat_id": 1,
            "number": "2024-02-11",
            "avail_yn": 30
        
        },
        {
            "concert_seat_id": 1,
            "number": "2024-02-11",
            "avail_yn": 30
        }
      ]   
    }
    ```

* `STATUS`: `400`
    ```json
    { 
      "message": "잘못된 접근 입니다 / 예약 가능한 좌석이 없습니다."
    }
    ```

<br>
<br>
<br>
<br>

## 좌석 예약 하기 API
***
### Description
예약 가능한 좌석 조회

### Request
* `URL` : `/concert/resservation`
* `METHOD`: `POST`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json",
      "Authorization": "Bearer uuid"
    }
    ```
  

* `BODY`
  ```json
  {
      "concert_id": 1,
      "concert_time_id": 1,
      "concert_seat_id": 1
  }
  ```

### Response

* `STATUS`: `200`
    ```json
    { 
      "reservation": {
        "id": 1,
        "status": "예약 완료",
        "seatNumber": 1,
        "concertId": "dd"
       } 
    }
    ```

* `STATUS`: `400`
    ```json
    { 
      "message": "잘못된 접근 입니다 / 에약에 실패했습니다 / 존재하지 않은 아이디 입니다.."
    }
    ```
  
<br>
<br>
<br>
<br>




## 포인트 조회 하기 API
***
### Description
포인트 조회 

### Request
* `URL` : `/point/{userId}`
* `METHOD`: `GET`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json",
    }
    ```


### Response

* `STATUS`: `200`
    ```json
    { 
      "balance": 10000
    }
    ```


<br>
<br>
<br>
<br>



## 포인트 충전 API
***
### Description
포인트 충전

### Request
* `URL` : `/point`
* `METHOD`: `POST`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json"
    }
    ```


* `BODY`
  ```json
  {
      "user_id": 1,
      "point": 10000
  }
  ```

### Response

* `STATUS`: `200`
    ```json
    { 
      "balance": 19909
    }
    ```

* `STATUS`: `400`
    ```json
    { 
      "message": "잘못된 접근 입니다 / 에약에 실패했습니다 / 존재하지 않은 아이디 입니다.."
    }
    ```

<br>
<br>
<br>
<br>




## 결제 API
***
### Description
좌석 결제 

### Request
* `URL` : `/pay/reservation`
* `METHOD`: `POST`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json",
      "Authozation": "Bearer uuid"
    }
  
    ```


* `BODY`
  ```json
  {
      "user_id": 1,
      "reservation_id": 1,
      "pay_amount": 1000
  }
  ```

### Response

* `STATUS`: `200`
    ```json
    { 
      "ok": true
    }
    ```

* `STATUS`: `400`
    ```json
    { 
      "message": "잘못된 접근 입니다 / 결제 실패했습니다."
    }
    ```

<br>
<br>
<br>
<br>

