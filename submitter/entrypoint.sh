#!/bin/bash

CMD="exec bin/storm jar ./topology.jar com.enow.storm.TestTopologyStaticHosts"

echo "$CMD"
eval "$CMD"
