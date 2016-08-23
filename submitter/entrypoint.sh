#!/bin/bash

CMD="exec bin/storm jar ./topology.jar com.enow.storm.TestTopologyStaticHosts enow "

echo "$CMD"
eval "$CMD"
