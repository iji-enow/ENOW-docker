#!/bin/bash

CMD="exec bin/storm jar ./topology.jar com.enow.storm.main.main Action Trigger"

echo "$CMD"
eval "$CMD"
