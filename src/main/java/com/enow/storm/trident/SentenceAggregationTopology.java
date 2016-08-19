package com.enow.storm.trident;

/**
 * Created by writtic on 2016. 8. 15..
 */
/*
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.kafka.trident.TransactionalTridentKafkaSpout;
import org.apache.storm.kafka.trident.TridentKafkaConfig;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import com.enow.storm.trident.functions.WordSplit;
import com.enow.storm.trident.state.HazelCastStateFactory;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.testing.Split;

import java.util.Arrays;

public class SentenceAggregationTopology {

    private final BrokerHosts brokerHosts;

    public SentenceAggregationTopology(String kafkaZookeeper) {
        brokerHosts = new ZkHosts(kafkaZookeeper);
    }

    public StormTopology buildTopology() {
        return buildTopology(null);
    }

    public StormTopology buildTopology(LocalDRPC drpc) {
        TridentKafkaConfig kafkaConfig = new TridentKafkaConfig(brokerHosts, "storm-sentence", "storm");
        kafkaConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
        TransactionalTridentKafkaSpout kafkaSpout = new TransactionalTridentKafkaSpout(kafkaConfig);
        TridentTopology topology = new TridentTopology();

        TridentState wordCounts = topology.newStream("kafka", kafkaSpout).shuffle().
                each(new Fields("str"), new WordSplit(), new Fields("word")).
                groupBy(new Fields("word")).
                persistentAggregate(new HazelCastStateFactory(), new Count(), new Fields("aggregates_words")).parallelismHint(2);


        topology.newDRPCStream("words", drpc)
                .each(new Fields("args"), new Split(), new Fields("word"))
                .groupBy(new Fields("word"))
                .stateQuery(wordCounts, new Fields("word"), new MapGet(), new Fields("count"))
                .each(new Fields("count"), new FilterNull())
                .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

        return topology.build();
    }

    public static void main(String[] args) throws Exception {

        String kafkaZk = args[0];
        SentenceAggregationTopology sentenceAggregationTopology = new SentenceAggregationTopology(kafkaZk);
        Config config = new Config();
        config.put(Config.TOPOLOGY_TRIDENT_BATCH_EMIT_INTERVAL_MILLIS, 2000);

        if (args != null && args.length > 1) {
            String name = args[1];
            String dockerIp = args[2];
            config.setNumWorkers(2);
            config.setMaxTaskParallelism(5);
            config.put(Config.NIMBUS_THRIFT_PORT, 6627);
            config.put(Config.STORM_ZOOKEEPER_PORT, 2181);
            config.put(Config.STORM_ZOOKEEPER_SERVERS, Arrays.asList(dockerIp));
            StormSubmitter.submitTopology(name, config, sentenceAggregationTopology.buildTopology());
        } else {
            LocalDRPC drpc = new LocalDRPC();
            config.setNumWorkers(2);
            config.setMaxTaskParallelism(2);
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("kafka", config, sentenceAggregationTopology.buildTopology(drpc));
            while (true) {
                System.out.println("Word count: " + drpc.execute("words", "the"));
                Utils.sleep(1000);
            }

        }
    }
}
*/


