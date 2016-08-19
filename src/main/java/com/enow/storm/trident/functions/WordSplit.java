package com.enow.storm.trident.functions;

/**
 * Created by writtic on 2016. 8. 15..
 */
/*
import org.apache.storm.tuple.Values;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;

public class WordSplit extends BaseFunction {
    public void execute(TridentTuple tuple, TridentCollector collector) {
        String sentence = (String) tuple.getValue(0);
        if (sentence != null) {
            sentence = sentence.replaceAll("\r", "");
            sentence = sentence.replaceAll("\n", "");
            for (String word : sentence.split(" ")) {
                collector.emit(new Values(word));
            }
        }
    }
}
*/