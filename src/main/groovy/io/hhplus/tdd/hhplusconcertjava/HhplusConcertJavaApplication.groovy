package io.hhplus.tdd.hhplusconcertjava

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableAsync
@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
@EnableJpaRepositories(repositoryImplementationPostfix = "CustomImpl")
class HhplusConcertJavaApplication {

    static void main(String[] args) {
        SpringApplication.run(HhplusConcertJavaApplication, args)
    }

}
