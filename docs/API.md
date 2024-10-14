# API DOCS

## 토큰 발급 API 
***
### Description
유저 토큰 발급

### Request
* `URL` : `/wait`
* `METHOD`: `GET`
* `HEADER`
    
    ```json
    {
      "Content-Type": "application/json"
    }
    ```

* `QUERY`
    
    ```json
    
    {
      "userId": 1
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
* `URL` : `/concert/time`
* `METHOD`: `GET`
* `HEADER`

    ```json
    {
      "Content-Type": "application/json",
      "Authorization": "Bearer Token"
    }
    ```

* `QUERY`

    ```json
    
    {
      "concertId": 1
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
 


