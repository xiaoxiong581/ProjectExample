package com.yzx.xiaoxiong581.projectexample.kafka;

import com.yzx.xiaoxiong581.projectexample.kafka.adapter.KafkaConsumerAdapter;
import com.yzx.xiaoxiong581.projectexample.kafka.adapter.KafkaProducerAdapter;

public class KafkaFunctionMain {
    public static void main(String[] args) throws Exception {
        String kafkaBootstrapServer = "localhost:9092";
        int sendMessageNum = 10000;
        String topic = "kafka_xiaoxiong_test";
        if (null != args) {
            if (1 <= args.length) {
                kafkaBootstrapServer = args[0];
            }

            if (2 <= args.length) {
                sendMessageNum = Integer.parseInt(args[1]);
            }

            if (3 <= args.length) {
                topic = args[2];
            }
        }

        KafkaConsumerAdapter consumerAdapter = new KafkaConsumerAdapter(kafkaBootstrapServer, topic);
        consumerAdapter.start();

        KafkaProducerAdapter producerAdapter = new KafkaProducerAdapter(kafkaBootstrapServer, topic, sendMessageNum);
        producerAdapter.start();
    }
}
