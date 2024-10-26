package com.example.demo;

import javafx.application.Platform;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import javafx.scene.control.TextArea;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    private KafkaConsumer<String, String> consumer;
    private TextArea messageArea; // Champ pour afficher les messages

    public KafkaConsumerExample(String topic, TextArea messageArea) {
        this.messageArea = messageArea;
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("group.id", "group1");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(topic));
    }

    public void consumeMessages() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                String message = record.value();
                // Utiliser Platform.runLater pour mettre à jour le TextArea
                Platform.runLater(() -> messageArea.appendText(message + "\n"));
            }
        }
    }

    public void close() {
        consumer.close();
    }
}

