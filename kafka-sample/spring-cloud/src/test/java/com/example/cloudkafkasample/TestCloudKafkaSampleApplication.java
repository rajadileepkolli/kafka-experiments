/* (C)2023 */
package com.example.cloudkafkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
class TestCloudKafkaSampleApplication {

    @Bean
    @ServiceConnection
    KafkaContainer kafkaContainer() {
        return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka").withTag("7.4.1")).withKraft();
    }

    public static void main(String[] args) {
        SpringApplication.from(CloudKafkaSampleApplication::main)
                .with(TestCloudKafkaSampleApplication.class)
                .run(args);
    }
}