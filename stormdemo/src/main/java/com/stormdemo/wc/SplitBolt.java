package com.stormdemo.wc;

import com.stormdemo.util.Util;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 */
public class SplitBolt implements IRichBolt {

    private TopologyContext context ;
    private OutputCollector collector ;
    private long count = 0;

    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        Util.sendToClient(this, "prepare()",8888);
        this.context = context ;
        this.collector = collector ;
    }

    public void execute(Tuple tuple) {
        count++;
        if(count %100 ==0){
            Util.sendToClient(this, "execute()",8888);
        }
        String line = tuple.getString(0);
        String[] arr = line.split(" ");
        for(String s : arr){
            collector.emit(new Values(s,1));
        }
    }

    public void cleanup() {

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word","count"));

    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
