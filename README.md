ENOW-Storm ![travis](https://travis-ci.org/ENOW-IJI/docker.svg?branch=master) ![Maven Version](https://maven-badges.herokuapp.com/maven-central/org.apache.storm/storm-core/badge.svg)
=========================


Environment setup with [Docker](https://www.docker.io/)
------------------------------

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

- When the storm topology can not follow and find the nimbus seeds that is a little bit slow to start try to change the `initial_delay_seconds` below longer in topology of `docker-compose.yml`:
```yaml
enviroment:
    - initial_delay_seconds=20
```
e.g. initial_delay_seconds: "10" to "30"

For more details and troubleshooting see:
- [https://github.com/enow/docker-kafka](https://github.com/enow/docker-kafka) </br>
and </br>
- [https://github.com/enow/docker-storm](https://github.com/enow/docker-storm)

## Build for running locally:

- `mvn clean package`

## Build for running on a Storm cluster:

- `mvn clean package -P cluster`

## Running the test topologies locally

We'd recommand you to use IDE like Eclipse or IntelliJ but you can also run the test topologies locally with commands below:

```bash
java -cp target/enow-storm-1.0.jar \
com.enow.storm.main.main Action Trigger \
-c storm.local.hostname=\"nimbus\" \
-c nimbus.seeds=\"[\\\"192.168.99.100\\\"]\"
```

But we recommand below method more.

## Running the test topologies on a storm cluster

Local topology can not communicate with other services. If you want storm to connect the others, you'd better run the test topologies on a storm cluster.

- `docker-compose -p storm -f submitter.yml build`
- `docker-compose -p storm -f submitter.yml up`</br>

The Storm UI will be available under: `http://<dockerIp>:8080/`
<br>
The Logviewer will be available under: `http://<dockerIp>:8000/` <br>
__e.g.__ `http://<dockerIp>:8000/log?file=supervisor.log`<br>
__e.g.__ The default `<dockerIp>` is `192.168.99.100` if you do not change anything on docker-machine.


## Automatically create topics

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

## Producing data

To feed the topologies with data, start the StormProducer (built in local mode)

- `java -cp target/enow-storm-1.0.jar com.enow.storm.tools.StormProducer <dockerIp>:<kafkaPort>`

Alternatively use the kafka console producer from within the kafka shell (see above)

- `$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list <dockerIp>:<kafkaPort> --topic <kafkaTopic>`

## Consuming data

To run a DRPC query, start the DrpcClient (built in local mode)

- `$KAFKA_HOME/bin/kafka-console-consumer.sh --zookeeper <dockerIp>:<zookeeperPort> --topic <kafkaTopic>`

References
----------

Test project for enow-storm based on information provided in and referenced by:

- [https://github.com/wurstmeister/storm-kafka-0.8-plus-test](https://github.com/wurstmeister/storm-kafka-0.8-plus-test)
