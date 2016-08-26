package com.enow.storm;

/**
 * Created by writtic on 2016. 8. 15..
 */

import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnowTopology {

    public static void main(String[] args) throws Exception {

        String zkUrl = "192.168.99.100:2181";
        String brokerUrl = "192.168.99.100:9092";
        System.out.println("Using Kafka zookeeper url: " + zkUrl + " broker url: " + brokerUrl);

        Config config = new Config();
        config.setDebug(true);
        config.put(Config.TOPOLOGY_MAX_SPOUT_PENDING, 1);

        String topic = "test";
        ZkHosts hosts = new ZkHosts(zkUrl);
        SpoutConfig kafkaConfig = new SpoutConfig(hosts, topic, "/" + topic, UUID.randomUUID().toString());

        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        kafkaConfig.startOffsetTime = -1;

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("kafka-spout", new KafkaSpout(kafkaConfig), 10);
        builder.setBolt("read-write-mongo-bolt", new ReadWriteMongoDBBolt()).allGrouping("kafka-spout");
        builder.setBolt("execute-bolt", new ExecuteBolt()).allGrouping("read-write-mongo-bolt");
        builder.setBolt("python-bolt", new PythonBolt()).allGrouping("execute-bolt");
        //builder.setBolt("read-mongo-bolt", new ReadMongoDBBolt()).allGrouping("write-mongo-bolt");
        //builder.setBolt("read-mongo-bolt", new ReadMongoDBBolt()).allGrouping("write-mongo-bolt");
        //builder.setBolt("write-mongo-bolt", new WriteMongoDBBolt()).allGrouping("read-mongo-bolt");
        //builder.setBolt("kafka-bolt", new KafkaSpoutTestBolt()).allGrouping("python-bolt");

        //builder.setBolt("print", new PrinterBolt()).shuffleGrouping("words");
        //If there are arguments, we are running on a cluster
        // nimbus urls
        List<String> nimbus_seeds = new ArrayList<String>();
        nimbus_seeds.add("192.168.99.100");
        nimbus_seeds.add("172.17.0.4");
        // zookeeper urls
        List<String> zookeeper_servers = new ArrayList<String>();
        zookeeper_servers.add("192.168.99.100");
        zookeeper_servers.add("172.17.0.2");
        config.setDebug(true);

        config.setNumWorkers(5);
        //config.setMaxTaskParallelism(5);
        config.put(Config.NIMBUS_SEEDS, nimbus_seeds);
        config.put(Config.NIMBUS_THRIFT_PORT, 6627);
        config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
        config.put(Config.STORM_ZOOKEEPER_SERVERS, zookeeper_servers);
        StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        //Thread.sleep(600000);

    }
}
