package io.hhplus.tdd.hhplusconcertjava.integration;

import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.Concert;
import io.hhplus.tdd.hhplusconcertjava.concert.domain.entity.ConcertPlace;
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

public class BigDataInsert {
    private static final Logger log = LoggerFactory.getLogger(BigDataInsert.class);

    private Connection connection;

    public void execute(String sql){

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

        for(int i=0; i<cnt; i++){
            value += "(";

            Concert concert = Concert.builder()
                    .title("concert_" + i)
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
        log.info(sql);
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
                log.info("insert sql: " + sql);
                stmt.executeUpdate(sql);

            }

            stmt.executeUpdate(insertConcertPlace());

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
