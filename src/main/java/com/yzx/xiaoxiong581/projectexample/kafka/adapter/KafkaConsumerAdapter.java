package com.yzx.xiaoxiong581.projectexample.kafka.adapter;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

/**
 * @author xiaoxiong581
 */
public class KafkaConsumerAdapter extends Thread {
    private KafkaConsumer<String, String> consumer;

    private String topic;

    public KafkaConsumerAdapter(String url, String topic, String consumerGroupId) {
        this.topic = topic;
        Properties props = new Properties();
        props.put("bootstrap.servers", url);
        props.put("group.id", consumerGroupId);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList(topic));
        System.out.printf("%s init kafka consumer, bootstrapServer: %s\n",
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), url);
    }

    @Override
    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                for (ConsumerRecord<String, String> record : records) {
                    System.out
                            .printf("%s receive, topic: %s, key: %s, value: %s, partition: %d, offset: %d\n",
                                    DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
                                    topic,
                                    record.key(), record.value(), record.partition(), record.offset());
                }
            }
        } catch (Exception e) {
            System.out.printf("catch exception when r, exception: %s\n", e.getMessage());
        }
    }
}
