package com.example.springbootkafkasample;

import static com.example.springbootkafkasample.SpringBootKafkaSampleApplication.TOPIC_TEST_1;
import static com.example.springbootkafkasample.SpringBootKafkaSampleApplication.TOPIC_TEST_2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springbootkafkasample.dto.MessageDTO;
import com.example.springbootkafkasample.listener.Receiver2;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@EmbeddedKafka(
        topics = {TOPIC_TEST_1, TOPIC_TEST_2},
        brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@DirtiesContext
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Disabled
class SpringBootKafkaSampleApplicationTests {

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaTemplate<String, MessageDTO> template;

    @Autowired
    private Receiver2 receiver2;

    @BeforeAll
    public void setUp() {
        // wait until the partitions are assigned
        for (MessageListenerContainer messageListenerContainer :
                kafkaListenerEndpointRegistry.getListenerContainers()) {
            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
        }
    }

    @Test
    void sendAndReceiveData() throws InterruptedException {
        template.send(TOPIC_TEST_1, UUID.randomUUID().toString(), new MessageDTO(TOPIC_TEST_1, "foo"));
        receiver2.getLatch().await(5, TimeUnit.SECONDS);
        assertEquals(0, receiver2.getLatch().getCount());
    }
}
