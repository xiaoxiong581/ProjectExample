#!/bin/bash

java -jar ProjectExample-1.0-SNAPSHOT-jar-with-dependencies.jar {optional: bootstrap-server, default: localhost:9092} {optional: sendNum:valueLength, default: 10000:32} {optional: topicName, default: kafka_xiaoxiong_test} {optional: consumerGroupID, default: test}

like:
java -jar ProjectExample-1.0-SNAPSHOT-jar-with-dependencies.jar localhost:9092 1000:5 test-topic-1 consumer-1

PS:
# 传参一定要前面的传递了后面的才能生效