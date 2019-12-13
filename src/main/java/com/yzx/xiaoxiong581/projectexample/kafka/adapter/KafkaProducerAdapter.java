package com.yzx.xiaoxiong581.projectexample.kafka.adapter;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * @author xiaoxiong581
 */
public class KafkaProducerAdapter extends Thread {
    private KafkaProducer<String, String> producer;

    private String topic;

    private String producerInfo;

    public KafkaProducerAdapter(String url, String topic, String producerInfo) {
        this.topic = topic;
        this.producerInfo = producerInfo;
        Properties props = new Properties();
        props.put("bootstrap.servers", url);
        props.put("acks", "all");
        props.put("retries", 3);
        props.put("enable.idempotence", true);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
        System.out.printf("%s init kafka producer, bootstrapServer: %s\n",
                DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), url);
    }

    @Override
    public void run() {
        int sendMessageNum = 10000;
        int valueLength = 32;
        String[] producerInfos = producerInfo.split(":");
        if (1 <= producerInfos.length) {
            sendMessageNum = Integer.parseInt(producerInfos[0]);
        }

        if (2 <= producerInfos.length) {
            valueLength = Integer.parseInt(producerInfos[1]);
        }

        String value = RandomStringUtils.randomAlphanumeric(valueLength);

        if (0 == sendMessageNum) {
            while (true) {
                sendMessage(value);
            }
        }

        for (int i = 0; i < sendMessageNum; i++) {
            sendMessage(value);
        }
        producer.close();
    }

    private void sendMessage(String value) {
        String key = UUID.randomUUID().toString();

        try {
            producer.send(new ProducerRecord<>(topic, key, value));
            System.out.printf("%s send, topic: %s, key: %s, value: %s\n",
                    DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), topic, key, value);
        } catch (Exception e) {
            System.out
                    .printf("catch exception when s, topic: %s, key: %s, value: %s, exception: %s\n", topic, key, value,
                            e.getMessage());
        }
    }
}
