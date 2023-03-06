package ua.sirkostya009.javastuff.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicConfig {
    private final KafkaTopics topics;

    @Bean
    public NewTopic mailTopic() {
        return new NewTopic(topics.getMailTopic(), 2, (short) 1);
    }
}
