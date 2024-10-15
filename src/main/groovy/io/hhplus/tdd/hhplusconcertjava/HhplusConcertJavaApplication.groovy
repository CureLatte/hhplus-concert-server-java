package io.hhplus.tdd.hhplusconcertjava

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableJpaRepositories(repositoryImplementationPostfix = "CustomImpl")
class HhplusConcertJavaApplication {

    static void main(String[] args) {
        SpringApplication.run(HhplusConcertJavaApplication, args)
    }

}
