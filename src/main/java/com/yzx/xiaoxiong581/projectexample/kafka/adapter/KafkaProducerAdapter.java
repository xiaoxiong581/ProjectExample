package com.yzx.xiaoxiong581.projectexample.kafka.adapter;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author xiaoxiong581
 */
public class KafkaProducerAdapter extends Thread {
    private KafkaProducer<String, String> producer;

    private String topic;

    public KafkaProducerAdapter(String url, String topic) {
        this.topic = topic;
        Properties props = new Properties();
        props.put("bootstrap.servers", url);
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            String currentStr = String.valueOf(i);
            String key = currentStr;
            String value = currentStr;
            System.out.printf("send message to kafka, topic: %s, key: %s, value: %s\n", topic, key, value);
            producer.send(new ProducerRecord<>(topic, key, value));
        }
        producer.close();
    }
}
