(ns telchat.net.server
  (:require [telchat.net.handler :as handler])
  (:import
        [java.net                           InetSocketAddress]
        [java.util.concurrent               Executors]
        [org.jboss.netty.bootstrap          ServerBootstrap]
        [org.jboss.netty.channel            Channels ChannelPipelineFactory]
        [org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory]))

(defn channel-factory
  "Returns a Netty channel factory."
  []
  (NioServerSocketChannelFactory.
    (Executors/newCachedThreadPool) (Executors/newCachedThreadPool)))

(defn pipeline-factory
  "Returns a Netty pipeline factory."
  []
  (proxy [ChannelPipelineFactory] []
    (getPipeline []
      (let [pipeline (Channels/pipeline)]
        (.addLast pipeline "handler" (handler/handler))
        pipeline))))

(defn start
  "Returns a Netty server."
  [port]
  (let [bootstrap (ServerBootstrap. (channel-factory))]
    (.setPipelineFactory bootstrap (pipeline-factory))
    (.bind               bootstrap (InetSocketAddress. port))))

