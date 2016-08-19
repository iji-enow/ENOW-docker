package com.enow.storm.tools;

/**
 * Created by writtic on 2016. 8. 15..
 */
/*
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

public class StormProducer {


    private static String[] sentences = new String[]{
            "the cow jumped over the moon",
            "the man went to the store and bought some candy",
            "four score and seven years ago",
            "how many apples can you eat",
    };

    public static void main(String args[]) throws InterruptedException {
        Properties props = new Properties();

        props.put("metadata.broker.list", args[0]);
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("request.required.acks", "1");
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        while (true) {
            for (String sentence : sentences) {
                ProducerRecord<String, String> data = new ProducerRecord<String, String>("storm-sentence", sentence);
                producer.send(data);
                Thread.sleep(10);
            }
        }

    }
}
*/