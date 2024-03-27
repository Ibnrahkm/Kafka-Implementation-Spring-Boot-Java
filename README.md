# Kafka-Implementation-Spring-Boot-Java

start kafka docker

```
docker run -p 9092:9092 apache/kafka:3.7.0 -d
```

start redis docker

```
sudo docker run --name redis -d -p 6379:6379 -e REDIS_PASSWORD=123456 redis sh -c 'exec redis-server --requirepass "$REDIS_PASSWORD"'

```

start rabbit MQ docker

```
docker run -d --hostname myrabbit -p 5000:15672 -p 5672:5672 --name rabitmq-springboot -e RABBITMQ_DEFAULT_USER=user -e RABBITMQ_DEFAULT_PASS=password rabbitmq:3-management

```

from project root directory

```
./gradlew clean build

```
after successful build run the project

```
java -jar <jarfile>

```

Test the APIs from postman

## Note

a kafka topic partition is a way to distribute the load to multiple nodes of a cluster.it also ordered the data according to key.

we can set topic partition,direct topic name on topic listener.topic listener will only listen for the mentioned topic names and partition.

kafka wont delete data automatically after consuming.

kafka group is a consumer group which mainly consisted of consumers.If one or multiple consumers are listed them under a same group id then that group is created automatically and if any message is published to that group id that message will be received by all consumers under this group id.

we can set username and password on kafka

kafka is a distributed pub/sub system

during message sending if we dont mention the partition then one random parition will be selected and message will be sent through that partition

we can customize the kafka template

if there is no multiple nodes into the cluster then there is no benifit of parition feature.all message will be sent to the default node.

we can add nodes dynamically either mentioning into bootstrap nodes in application yml file or there are some more options to add nodes dynamically

we can do all operations using cli kafka bin.for that we need to download kafka sources or binaries and from there we can use them.some examples-

```
./kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
./kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
./kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
./kafka-console-producer.sh --topic quickstart-events
./kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
./kafka-console-consumer.sh -h --topic quickstart-server --from-begining --bootstrap-server localhost:9092
./kafka-console-consumer.sh -h --topic quickstart-server --from-beginning --bootstrap-server localhost:9092
./kafka-console-consumer.sh --topic quickstart-server --from-beginning --bootstrap-server localhost:9092
./kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
```


to download and setup instead of docker we can follow this-


## manual kafka installament
```
download kafka from https://www.apache.org/dyn/closer.cgi?path=/kafka/3.7.0/kafka_2.13-3.7.0.tgz
extract using tar -xzf kafka_2.13-3.7.0.tgz && cd kafka_2.13-3.7.0
start zookeeper bin/zookeeper-server-start.sh config/zookeeper.properties


some actions

bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092

bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092

bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092

bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
```



