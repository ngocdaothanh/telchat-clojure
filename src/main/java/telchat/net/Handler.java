package telchat.net;

import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

// We need this blank class just because Clojure does not support annotation!
@ChannelPipelineCoverage("one")
public class Handler extends SimpleChannelUpstreamHandler {
}
