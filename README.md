ENOW-Storm ![travis](https://travis-ci.org/ENOW-IJI/enow-storm.svg?branch=master)
=========================


Environment setup with [Docker](https://www.docker.io/)
------------------------------

If you are using a Mac follow the instructions [here](https://docs.docker.com/installation/mac/) to setup a docker environment.

- Install [docker-compose](http://docs.docker.com/compose/install/)

- Install [storm](https://storm.incubator.apache.org/downloads.html) (so you can upload your topology to the test cluster)

- Start the test environment
    - ```docker-compose -p storm up```
- Start a kafka shell and From within the shell, create a topic
    - ```start-kafka-shell.sh <Docker Ip> <Zookeeper>```
    - ```$KAFKA_HOME/bin/kafka-topics.sh --create --topic storm-sentence --partitions 2 --zookeeper $ZK --replication-factor 1```
- Or just add ```environment: KAFKA_CREATE_TOPICS: "test:3:1"``` in kafka of docker-compose
- And when the storm ui is available create another window then Start the topology builder
    - ```docker-compose -p storm -f submitter.yml build```
    - ```docker-compose -p storm -f submitter.yml up```

- For more details and troubleshooting see [https://github.com/enow/kafka-docker](https://github.com/enow/kafka-docker) </br>
and </br> [https://github.com/enow/storm-docker](https://github.com/enow/storm-docker)

## Build for running locally:

- ```mvn clean package```

## Build for running on a Storm cluster:

- ```mvn clean package -P cluster```

## Running the test topologies locally

- ```java -cp target/enow-storm-1.0.jar com.enow.storm.trident.SentenceAggregationTopology <kafkaZookeeper>```
- ```java -cp target/enow-storm-1.0.jar com.enow.storm.KafkaSpoutTestTopology <kafkaZookeeper>```
- ```java -cp target/enow-storm-1.0.jar com.enow.storm.TestTopologyStaticHosts```

## Running the test topologies on a storm cluster


- ```storm jar target/enow-storm-1.0.jar com.enow.storm.trident.SentenceAggregationTopology <kafkaZookeeper> sentences <dockerIp>```
- ```storm jar target/enow-storm-1.0.jar com.enow.storm.KafkaSpoutTestTopology <kafkaZookeeper> sentences <dockerIp>```

The Storm UI will be available under: ```http://<dockerIp>:8080/```

The Logviewer will be available under: ```http://<dockerIp>:8000/``` e.g. ```http://<dockerIp>:8000/log?file=supervisor.log```

## Producing data

To feed the topologies with data, start the StormProducer (built in local mode)

- ```java -cp target/enow-storm-1.0.jar com.enow.storm.tools.StormProducer <dockerIp>:<kafkaPort>```

Alternatively use the kafka console producer from within the kafka shell (see above)

- ```$KAFKA_HOME/bin/kafka-console-producer.sh --topic=storm-sentence --broker-list=<dockerIp>:<kafkaPort>```

## Consuming data

To run a DRPC query, start the DrpcClient (built in local mode)

- ```java -cp target/enow-storm-1.0.jar com.enow.storm.tools.DrpcClient <dockerIp> 3772```


References
----------

Test project for enow-storm based on information provided in and referenced by:

- [https://github.com/nathanmarz/storm-contrib/blob/master/storm-kafka/src/jvm/storm/kafka/TestTopology.java](https://github.com/nathanmarz/storm-contrib/blob/master/storm-kafka/src/jvm/storm/kafka/TestTopology.java)
- [https://github.com/nathanmarz/storm/wiki/Trident-tutorial](https://github.com/nathanmarz/storm/wiki/Trident-tutorial)
- [https://github.com/nathanmarz/storm/wiki/Trident-state](https://github.com/nathanmarz/storm/wiki/Trident-state)
- [https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+Producer+Example](https://cwiki.apache.org/confluence/display/KAFKA/0.8.0+Producer+Example)
- [https://github.com/wurstmeister/storm-kafka-0.8-plus-test](https://github.com/wurstmeister/storm-kafka-0.8-plus-test)

Also contains an attempt at a sample implementation of trident state based on [Hazelcast](http://www.hazelcast.com/)
