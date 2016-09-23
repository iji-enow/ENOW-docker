ENOW-Docker
===========
docker-storm status:<br>
[![Docker Pulls](https://img.shields.io/docker/pulls/enow/storm.svg)](https://hub.docker.com/r/enow/storm/)
[![](https://images.microbadger.com/badges/image/enow/storm.svg)](https://hub.docker.com/r/enow/storm/) [![](https://images.microbadger.com/badges/version/enow/storm.svg)](https://hub.docker.com/r/enow/storm/)<br><br>
docker-kafka status:<br>
[![Docker Pulls](https://img.shields.io/docker/pulls/enow/kafka.svg)](https://hub.docker.com/r/enow/kafka/)
[![](https://images.microbadger.com/badges/image/enow/kafka.svg)](https://hub.docker.com/r/enow/kafka/)
[![](https://images.microbadger.com/badges/version/enow/kafka.svg)](https://hub.docker.com/r/enow/kafka/)<br><br>
docker-zookeeper status:<br>
[![Docker Pulls](https://img.shields.io/docker/pulls/enow/zookeeper.svg)](https://hub.docker.com/r/enow/zookeeper/)
[![](https://images.microbadger.com/badges/image/enow/zookeeper.svg)](https://hub.docker.com/r/enow/zookeeper/)
[![](https://images.microbadger.com/badges/version/enow/zookeeper.svg)](https://hub.docker.com/r/enow/zookeeper/)<br>
Environment setup with [Docker](https://www.docker.io/)
-------------------------------

If you are using a Mac follow the instructions [here](https://docs.docker.com/installation/mac/) to setup a docker environment.

- Install [docker-compose](http://docs.docker.com/compose/install/)
- Install [storm](https://storm.incubator.apache.org/downloads.html) (so you can upload your topology to the test cluster)
- Create the custom test network for Apache Storm
    - `docker network create storm`
- Start the test environment:
    - `docker-compose -p storm up`
- Start a kafka shell and From within the shell, create a topic in kafka of `docker-compose.yml`:
```yaml
  environment:
    KAFKA_CREATE_TOPICS: "test:3:1"
```

- When the storm topology can not follow and find the nimbus seeds that is a little bit slow to start you would try this to change the `initial_delay_seconds` below longer in topology of `docker-compose.yml`:
```yaml
enviroment:
    - initial_delay_seconds=20
```

For more details and troubleshooting see:
- [https://github.com/enow/docker-kafka](https://github.com/enow/docker-kafka) </br>
and </br>
- [https://github.com/enow/docker-storm](https://github.com/enow/docker-storm)

##### Build for running locally:

- `mvn clean package`

##### Build for running on a Storm cluster:

- `mvn clean package -P cluster`

##### Running the test topologies locally

We'd recommend you to use IDE like Eclipse or IntelliJ but you can also run the test topologies locally with commands below:

```bash
java -cp target/enow-storm-1.0.jar \
com.enow.storm.main.main Action Trigger \
-c storm.local.hostname=\"nimbus\" \
-c nimbus.seeds=\"[\\\"192.168.99.100\\\"]\"
```

But we recommend below method more.

Apache Storm config
-------------------
##### Scale out supervisors

You can start more than one supervisors with following command, e.g. for 3 instances.
```
docker-compose scale supervisor=3
```
##### Running the test topologies on a storm cluster

Local topology can not communicate with other services. If you want storm to connect the others, you'd better run the test topologies on a storm cluster.

- `docker-compose -p storm -f submitter.yml build`
- `docker-compose -p storm -f submitter.yml up`</br>

The Storm UI will be available under: `http://<dockerIp>:8080/`
<br>
The Logviewer will be available under: `http://<dockerIp>:8000/` <br>
__e.g.__ `http://<dockerIp>:8000/log?file=supervisor.log`<br>
__e.g.__ The default `<dockerIp>` is `192.168.99.100` if you do not change anything on docker-machine.

Apache Kafka config
-------------------
##### Automatically create topics

If you want to have kafka-docker automatically create topics in Kafka during
creation, a `KAFKA_CREATE_TOPICS` environment variable can be
added in `docker-compose.yml`.

Here is an example snippet from `docker-compose.yml`:
```yaml
environment:
    KAFKA_CREATE_TOPICS: "Topic1:2:3,Topic2:3:1"
```
`Topic 1` will have 2 partition and 3 replicas, <br>
`Topic 2` will have 3 partition and 1 replica.

##### Producing data

Use the kafka console producer from within the kafka shell (see below)

- `$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list <dockerIp>:<kafkaPort> --topic <kafkaTopic>`

##### Consuming data

Use the kafka console consumer from within the kafka shell (see below)

- `$KAFKA_HOME/bin/kafka-console-consumer.sh --zookeeper <dockerIp>:<zookeeperPort> --topic <kafkaTopic>`

Apache Zookeeper config
-----------------------
##### Remote Zookeeper server

If you would like to use remote Zookeeper server instead of local.
You'd better use Docker Swarm and this below url is one of the examples.

[https://github.com/Baqend/tutorial-swarm-storm](https://github.com/Baqend/tutorial-swarm-storm)

FYI, The basic configuration is same as zookeeper setting on `docker-compose.yml` but if you would like to build multiple zookeeper server, see below repository as a reference.

[https://github.com/denverdino/docker-storm](https://github.com/denverdino/docker-storm)

If you have no idea about Docker Swarm, follow this tutorial below url.

[https://docs.docker.com/engine/swarm/swarm-tutorial](https://docs.docker.com/engine/swarm/swarm-tutorial/)

References
----------

Test project for ENOW-docker based on information provided in and referenced by:

- [https://github.com/wurstmeister/storm-kafka-0.8-plus-test](https://github.com/wurstmeister/storm-kafka-0.8-plus-test)
- [https://github.com/wurstmeister/storm-docker](https://github.com/wurstmeister/storm-docker)
- [https://github.com/wurstmeister/kafka-docker](https://github.com/wurstmeister/kafka-docker)
- [https://github.com/wurstmeister/zookeeper-docker](https://github.com/wurstmeister/zookeeper-docker)
- [https://github.com/denverdino/docker-storm](https://github.com/denverdino/docker-storm)
- [https://github.com/Baqend/docker-storm](https://github.com/Baqend/docker-storm)
