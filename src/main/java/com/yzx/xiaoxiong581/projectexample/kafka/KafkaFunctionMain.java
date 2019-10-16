package com.yzx.xiaoxiong581.projectexample.kafka;

import com.yzx.xiaoxiong581.projectexample.kafka.adapter.KafkaConsumerAdapter;
import com.yzx.xiaoxiong581.projectexample.kafka.adapter.KafkaProducerAdapter;

public class KafkaFunctionMain {
    public static void main(String[] args) throws Exception {
        String kafkaBootstrapServer = "localhost:9092";
        String topic = "kafka_xiaoxiong_test";
        KafkaConsumerAdapter consumerAdapter = new KafkaConsumerAdapter(kafkaBootstrapServer, topic);
        consumerAdapter.start();

        KafkaProducerAdapter producerAdapter = new KafkaProducerAdapter(kafkaBootstrapServer, topic);
        producerAdapter.start();
    }
}
