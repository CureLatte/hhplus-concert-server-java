```mermaid
sequenceDiagram
    title 예약 가능 날짜 조회 API



User -> Server: request : user_id, concert_id

activate Server #98ff8c

Server -> WaitQueueTable: token 조회 요청
activate WaitQueueTable


WaitQueueTable -> Server:  return token

deactivate WaitQueueTable


Server -> Server: token 확인
deactivate Server
activate Server #98ff8c
activate Server #fad0ca
activate Server #fad0ca


alt token 없을 경우 
 
  Server -> User: 400 Response: 잘못된 요청입니다.
  deactivate Server #fad0ca
else wait 일경우 
  Server -> User: 400 Response: 접근권한이 없습니다.
  deactivate Server #fad0ca
end


Server->ConcertTable: concert 조회 요청
activate ConcertTable
ConcertTable -> Server: return concert 
deactivate ConcertTable

Server -> Server: 콘서트 check
activate Server #fad0ca

alt 존재하지 않은 콘서트
  Server -> User: 400 Response: 존재하지 않은 콘서트 입니다.
  deactivate Server #fad0ca
end


Server-> ConcertTimeTable: 예약 가능한 날짜 조회
activate ConcertTimeTable
ConcertTimeTable -> Server: return 날짜 리스트
deactivate ConcertTimeTable



Server -> User: 200 Response
deactivate Server #98ff8c

```