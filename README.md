
## 시나리오 선정
### Concert Scenario
* 대기열 구현이 매력적이라 선택했습니다.

## configuration

* JAVA 17
* JPA
* MYSQL

## Documents

* ### [PROJECT MILESTONE](https://github.com/users/CureLatte/projects/4/views/1?sortedBy%5Bdirection%5D=asc&sortedBy%5BcolumnId%5D=Milestone)
* ### [SEQUENCE Diagram](docs/sequence.md)
* ### [ERD Diagram](docs/ERD.md)
* ### [API DOCS](docs/API.md)
* ![img.png](docs/swagger.png)




```javascript


=====================================
2024-10-30 14:50:08 281472904261376 INNODB MONITOR OUTPUT
=====================================
Per second averages calculated from the last 44 seconds
-----------------
BACKGROUND THREAD
-----------------
srv_master_thread loops: 1285 srv_active, 0 srv_shutdown, 434721 srv_idle
srv_master_thread log flush and writes: 0
----------
SEMAPHORES
----------
OS WAIT ARRAY INFO: reservation count 14517
OS WAIT ARRAY INFO: signal count 14274
RW-shared spins 0, rounds 0, OS waits 0
RW-excl spins 0, rounds 0, OS waits 0
RW-sx spins 0, rounds 0, OS waits 0
Spin rounds per wait: 0.00 RW-shared, 0.00 RW-excl, 0.00 RW-sx
------------------------
LATEST DETECTED DEADLOCK
------------------------
2024-10-30 14:46:23 281472963440384
*** (1) TRANSACTION:
TRANSACTION 57617, ACTIVE 0 sec inserting
mysql tables in use 1, locked 1
LOCK WAIT 7 lock struct(s), heap size 1128, 4 row lock(s)
MySQL thread id 32958, OS thread handle 281472767807232, query id 529085 192.168.65.1 root update
insert into reservation2 (concert_id,concert_seat_id,concert_time_id,created_at,status,updated_at,user_id,id) values (1502,1553,1502,'2024-10-30 14:46:23.972744','RESERVATION','2024-10-30 14:46:23.972744',312,1)

*** (1) HOLDS THE LOCK(S):
RECORD LOCKS space id 99 page no 4 n bits 72 index PRIMARY of table `hhplus_concert`.`reservation2` trx id 57617 lock_mode X
Record lock, heap no 1 PHYSICAL RECORD: n_fields 1; compact format; info bits 0
 0: len 8; hex 73757072656d756d; asc supremum;;


*** (1) WAITING FOR THIS LOCK TO BE GRANTED:
RECORD LOCKS space id 99 page no 4 n bits 72 index PRIMARY of table `hhplus_concert`.`reservation2` trx id 57617 lock_mode X insert intention waiting
Record lock, heap no 1 PHYSICAL RECORD: n_fields 1; compact format; info bits 0
 0: len 8; hex 73757072656d756d; asc supremum;;


*** (2) TRANSACTION:
TRANSACTION 57608, ACTIVE 0 sec inserting
mysql tables in use 1, locked 1
LOCK WAIT 7 lock struct(s), heap size 1128, 4 row lock(s)
MySQL thread id 32961, OS thread handle 281472331370240, query id 529087 192.168.65.1 root update
insert into reservation2 (concert_id,concert_seat_id,concert_time_id,created_at,status,updated_at,user_id,id) values (1502,1556,1502,'2024-10-30 14:46:23.977449','RESERVATION','2024-10-30 14:46:23.977449',315,2)

*** (2) HOLDS THE LOCK(S):
RECORD LOCKS space id 99 page no 4 n bits 72 index PRIMARY of table `hhplus_concert`.`reservation2` trx id 57608 lock_mode X
Record lock, heap no 1 PHYSICAL RECORD: n_fields 1; compact format; info bits 0
 0: len 8; hex 73757072656d756d; asc supremum;;


*** (2) WAITING FOR THIS LOCK TO BE GRANTED:
RECORD LOCKS space id 99 page no 4 n bits 72 index PRIMARY of table `hhplus_concert`.`reservation2` trx id 57608 lock_mode X insert intention waiting
Record lock, heap no 1 PHYSICAL RECORD: n_fields 1; compact format; info bits 0
 0: len 8; hex 73757072656d756d; asc supremum;;

*** WE ROLL BACK TRANSACTION (2)
------------
TRANSACTIONS
------------
Trx id counter 57635
Purge done for trx's n:o < 57635 undo n:o < 0 state: running but idle
History list length 0
LIST OF TRANSACTIONS FOR EACH SESSION:
---TRANSACTION 562948478708896, not started
0 lock struct(s), heap size 1128, 0 row lock(s)
---TRANSACTION 562948478704856, not started
0 lock struct(s), heap size 1128, 0 row lock(s)
---TRANSACTION 562948478704048, not started
0 lock struct(s), heap size 1128, 0 row lock(s)
---TRANSACTION 562948478703240, not started
0 lock struct(s), heap size 1128, 0 row lock(s)
--------
FILE I/O
--------
I/O thread 0 state: waiting for completed aio requests (insert buffer thread)
I/O thread 1 state: waiting for completed aio requests (read thread)
I/O thread 2 state: waiting for completed aio requests (read thread)
I/O thread 3 state: waiting for completed aio requests (read thread)
I/O thread 4 state: waiting for completed aio requests (read thread)
I/O thread 5 state: waiting for completed aio requests (read thread)
I/O thread 6 state: waiting for completed aio requests (write thread)
I/O thread 7 state: waiting for completed aio requests (write thread)
I/O thread 8 state: waiting for completed aio requests (write thread)
I/O thread 9 state: waiting for completed aio requests (write thread)
Pending normal aio reads: [0, 0, 0, 0, 0] , aio writes: [0, 0, 0, 0] ,
 ibuf aio reads:
Pending flushes (fsync) log: 0; buffer pool: 0
2062 OS file reads, 213065 OS file writes, 176799 OS fsyncs
0.00 reads/s, 0 avg bytes/read, 0.00 writes/s, 0.00 fsyncs/s
-------------------------------------
INSERT BUFFER AND ADAPTIVE HASH INDEX
-------------------------------------
Ibuf: size 1, free list len 0, seg size 2, 0 merges
merged operations:
 insert 0, delete mark 0, delete 0
discarded operations:
 insert 0, delete mark 0, delete 0
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
Hash table size 34679, node heap has 0 buffer(s)
0.00 hash searches/s, 0.00 non-hash searches/s
---
LOG
---
Log sequence number          46260570
Log buffer assigned up to    46260570
Log buffer completed up to   46260570
Log written up to            46260570
Log flushed up to            46260570
Added dirty pages up to      46260570
Pages flushed up to          46260570
Last checkpoint at           46260570
Log minimum file id is       11
Log maximum file id is       14
74986 log i/o's done, 0.00 log i/o's/second
----------------------
BUFFER POOL AND MEMORY
----------------------
Total large memory allocated 0
Dictionary memory allocated 701737
Buffer pool size   8192
Free buffers       6564
Database pages     1628
Old database pages 580
Modified db pages  0
Pending reads      0
Pending writes: LRU 0, flush list 0, single page 0
Pages made young 6391, not young 1036
0.00 youngs/s, 0.00 non-youngs/s
Pages read 1129, created 505, written 66640
0.00 reads/s, 0.00 creates/s, 0.00 writes/s
No buffer pool page gets since the last printout
Pages read ahead 0.00/s, evicted without access 0.00/s, Random read ahead 0.00/s
LRU len: 1628, unzip_LRU len: 0
I/O sum[0]:cur[0], unzip sum[0]:cur[0]
--------------
ROW OPERATIONS
--------------
0 queries inside InnoDB, 0 queries in queue
0 read views open inside InnoDB
Process ID=1, Main thread ID=281472803860224 , state=sleeping
Number of rows inserted 10301, updated 2443, deleted 8514, read 136064
0.00 inserts/s, 0.00 updates/s, 0.00 deletes/s, 0.00 reads/s
Number of system rows inserted 1118, updated 1377, deleted 744, read 127374
0.00 inserts/s, 0.00 updates/s, 0.00 deletes/s, 0.00 reads/s
----------------------------
END OF INNODB MONITOR OUTPUT
============================


```