package com.example.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private KafkaProducerExample producer;
    private KafkaConsumerExample consumer;

    @Override
    public void start(Stage primaryStage) {
        TextArea messageArea = new TextArea();
        producer = new KafkaProducerExample("test-topic");
        consumer = new KafkaConsumerExample("test-topic", messageArea);

        TextField inputField = new TextField();
        Button sendButton = new Button("Send");

        sendButton.setOnAction(event -> {
            String message = inputField.getText();
            producer.sendMessage(message);
            inputField.clear();
        });

        // Thread pour consommer les messages
        Thread consumerThread = new Thread(() -> {
            consumer.consumeMessages();
        });
        consumerThread.setDaemon(true);
        consumerThread.start();

        VBox vbox = new VBox(inputField, sendButton, messageArea);
        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Kafka Flink JavaFX App");
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        producer.close();
        consumer.close();
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
