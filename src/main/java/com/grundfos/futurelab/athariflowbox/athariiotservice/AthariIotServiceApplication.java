package com.grundfos.futurelab.athariflowbox.athariiotservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info(description = "Athari.Flowbox IoT Service API", version = "1.0", title = "Athari.Flowbox IoT Service API"))
public class AthariIotServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AthariIotServiceApplication.class, args);
    }

}
