package com.yzx.xiaoxiong581.projectexample.kafka.adapter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author xiaoxiong581
 */
public class KafkaConsumerAdapter extends Thread {
    private KafkaConsumer<String, String> consumer;

    private String topic;

    public KafkaConsumerAdapter(String url, String topic) {
        this.topic = topic;
        Properties props = new Properties();
        props.put("bootstrap.servers", url);
        props.put("group.id", "test");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("receive message from kafka, topic: %s, key: %s, value: %s, offset: %d\n", topic,
                        record.key(), record.value(), record.offset());
            }
        }
    }
}
