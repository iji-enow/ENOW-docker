from enow/storm
MAINTAINER writtic <writtic@gmail.com>

ADD target/enow-storm-1.0.jar $STORM_HOME/extlib/enow-storm-1.0.jar

WORKDIR /usr/share/storm

ADD target/enow-storm-1.0.jar topology.jar

# add startup script
ADD entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh

ENTRYPOINT ["/usr/share/storm/entrypoint.sh"]
