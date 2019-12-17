package com.yzx.xiaoxiong581.projectexample.kafka;

import com.yzx.xiaoxiong581.projectexample.kafka.adapter.KafkaConsumerAdapter;
import com.yzx.xiaoxiong581.projectexample.kafka.adapter.KafkaProducerAdapter;

import java.util.stream.IntStream;

public class KafkaFunctionMain {
    public static void main(String[] args) throws Exception {
        String kafkaBootstrapServer = "localhost:9092";
        String producerInfo = "10000:32";
        String topic = "kafka_xiaoxiong_test";
        String consumerGroupId = "test";
        if (null != args) {
            if (1 <= args.length) {
                kafkaBootstrapServer = args[0];
            }

            if (2 <= args.length) {
                producerInfo = args[1];
            }

            if (3 <= args.length) {
                topic = args[2];
            }

            if (4 <= args.length) {
                consumerGroupId = args[3];
            }
        }

        KafkaConsumerAdapter consumerAdapter = new KafkaConsumerAdapter(kafkaBootstrapServer, topic, consumerGroupId);
        consumerAdapter.start();
        System.out.println("wait producer to start");
        IntStream.range(1, 11).forEach(i -> {
            try {
                System.out.println(i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        });
        KafkaProducerAdapter producerAdapter = new KafkaProducerAdapter(kafkaBootstrapServer, topic, producerInfo);
        producerAdapter.start();
    }
}
