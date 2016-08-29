FROM enow/storm
MAINTAINER writtic <writtic@gmail.com>

ADD ./enow-storm-1.0.jar $STORM_HOME/extlib/enow-storm-1.0.jar
ADD ./enow-storm-1.0.jar $STORM_HOME/topology.jar
WORKDIR /usr/share/storm

RUN /usr/bin/config-supervisord.sh jar ./topology.jar com.enow.storm.TestTopologyStaticHosts

ADD wait-for-it.sh $STORM_HOME/wait-for-it.sh
RUN chmod +x $STORM_HOME/wait-for-it.sh


ENTRYPOINT ["/usr/share/storm/wait-for-it.sh"]
