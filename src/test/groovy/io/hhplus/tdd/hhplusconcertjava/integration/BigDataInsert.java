package io.hhplus.tdd.hhplusconcertjava.integration;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertPlace;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertTime;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.repository.ConcertRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.entity.ConcertEntity;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository.ConcertJpaRepository;
import io.hhplus.tdd.hhplusconcertjava.concert.infrastructure.repository.IConcertJpaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Repeatable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BigDataInsert {
    private static final Logger log = LoggerFactory.getLogger(BigDataInsert.class);

    private Connection connection;

    public void execute(String sql){

    }

    public static String insertConcertTime(){
        String baseSql = """
        
            insert into concert_time(concert_id, status, price, max_cnt, left_cnt, start_time, end_time)
            
            values 
        
        """;

        List<ConcertTime> concertTimeList = new ArrayList<ConcertTime>();



        for(int i=0; i< 10000;i++){

            int concertId = (int)( Math.random() * 1690000 + 29);

            int month = (int)( Math.random() * 12) + 1;

            int day = (int)( Math.random() * 28) + 1;

            LocalDateTime startDate  = LocalDateTime.of(2024, month, day  , 0, 0, 0);


            concertTimeList.add(ConcertTime.builder()
                            .concert(Concert.builder()
                                    .id((long)concertId )

                                    .build())
                            .price(1000)
                            .startTime(startDate)
                            .endTime(startDate.plusHours(2))
                            .maxCnt(30)
                            .status(ConcertTime.ConcertTimeStatus.ON_SALE)
                    .build());

        }

        String value = "";

        for(ConcertTime concertTime : concertTimeList){
            value += "(";

            value +=  concertTime.concert.getId() + ",";

            value +=  "\"" + concertTime.status.name() + "\",";
            value +=  "\"" + concertTime.price + "\",";
            value +=  "\"" + concertTime.maxCnt + "\",";
            value +=  "\"" + concertTime.leftCnt + "\",";
            value +=  "\"" + concertTime.startTime + "\",";
            value +=  "\"" + concertTime.endTime + "\"";


            value += "),";
        }


        return baseSql + value.substring(0,value.length()-1);

    }

    public static String insertConcertPlace(){

        String baseSql = """
            insert into concert_place(name, address, longitude, latitude, created_at, updated_at, deleted_at) 
            values 
                
        """;
        List<ConcertPlace> concertPlaceList = new ArrayList<ConcertPlace>();

        concertPlaceList.add(ConcertPlace.builder()
                        .name("월드컵경기장")
                        .address("서울 마포구 성산동")
                        .latitude(37.56834)
                        .longitude(126.89731)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                .build());

        concertPlaceList.add(ConcertPlace.builder()
                        .name("인터파크 유니플렉스")
                        .address("서울특별시 종로구 동숭동")
                        .latitude(37.56834)
                        .longitude(126.90731)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                .build());


        concertPlaceList.add(ConcertPlace.builder()
                .name("올림픽공원")
                .address("서울특별시 송파구 올림픽로 424")
                .latitude(37.56834)
                .longitude(126.90731)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        String value = "";

        for(ConcertPlace concertPlace : concertPlaceList){
            value += "(";


            value += "\"" + concertPlace.name + "\", ";
            value += "\"" + concertPlace.address + "\", ";
            value += "\"" + concertPlace.latitude + "\", ";
            value += "\"" + concertPlace.longitude + "\", ";
            value += "\"" + concertPlace.createdAt + "\", ";
            value += "\"" + concertPlace.updatedAt + "\", ";
            value +=  concertPlace.deletedAt;


            value += "),";
        }

        String sql = baseSql + value.substring(0, value.length() - 1);

        log.info(sql);

        return sql;
    }

    public static String insertConcert(Long cnt){

        // sql 작성
        String baseSql = """
                insert into concert(title, status, created_at, updated_at) 
                
                values 
               
                """;


        String value = " ";

        List<String> concertThemeList = List.of(
                "아이유 콘서트",
                "김장훈 콘서트",
                "청춘 드림 콘서트",
                "아이유 1집 콘서트",
                "국민 토론 콘서트",
                "국제 대회 콘서트",
                "국제 해킹 대회 콘서트",
                "북 콘서트",
                "30주년 콘서트",
                "10주년 콘서트",
                "심리상담 콘서트",
                "직업 콘서트",
                "싸이 콘서트",
                "흠뻑쇼 콘서트",
                "블랙핑크 콘서트",
                "러블리즈 콘서트",
                "방탄 소년단 콘서트",
                "윤하 콘서트",
                "성시경 콘서트",
                "윤종신 콘서트",
                "여자친구 콘서트",
                "블락비 콘서트",
                "뉴진스 콘서트"
        );




        for(int i=0; i<cnt; i++){
            value += "(";

            int randomIndex = (int) (Math.random() * concertThemeList.size());
            int count = (int)((Math.random() * 10000) + 1);

            String concertTitle = "제 "+  count + " 회 " + concertThemeList.get(randomIndex);


            Concert concert = Concert.builder()
                    .title(concertTitle)
                    .status(Concert.ConcertStatus.OPEN)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            value += "\"" + concert.getTitle() +"\",";
            value += "\"" + concert.getStatus().name() + "\",";
            value += "\"" + concert.getCreatedAt().toString() + "\",";
            value += "\"" + concert.getUpdatedAt().toString() + "\"";

            value += "),";

        }


        String sql = baseSql + value.substring(0, value.length()-1);
        // log.info(sql);
        return sql;

    }



    public static void main(String[] args)  {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            log.info("driver load success ");

            String url = "jdbc:mysql://localhost:3308/hhplus_concert?serverTimezone=UTC&characterEncoding=UTF-8";
            String username= "root";
            String pwd = "12341234";



            Connection connection = DriverManager.getConnection(url, username, pwd);


            Statement stmt = connection.createStatement();

            log.info("connection established");

            for(int j=0; j<100; j++){
                String sql = insertConcert(1000L);
                // log.info("insert sql: " + sql);
                stmt.executeUpdate(sql);

            }

            // stmt.executeUpdate(insertConcertPlace());

//            for(int j=0; j<100; j++){
//                stmt.executeUpdate(insertConcertTime());
//                log.info("{} insert!", j);
//            }



            if(stmt != null){
                stmt.close();
            }

            if(connection != null){
                connection.close();
            }

        }  catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
