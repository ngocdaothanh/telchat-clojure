(ns telchat.net.server
  (:require [telchat.net.handler :as handler])
  (:import
        [java.net                           InetSocketAddress]
        [java.util.concurrent               Executors]
        [org.jboss.netty.bootstrap          ServerBootstrap]
        [org.jboss.netty.channel            Channels ChannelPipelineFactory]
        [org.jboss.netty.channel.socket.nio NioServerSocketChannelFactory]))

(defn make-channel-factory
  "Returns a Netty channel factory."
  []
  (NioServerSocketChannelFactory.
    (Executors/newCachedThreadPool) (Executors/newCachedThreadPool)))

(defn make-pipeline-factory
  "Returns a Netty pipeline factory."
  []
  (proxy [ChannelPipelineFactory] []
    (getPipeline []
      (let [pipeline (Channels/pipeline)]
        (.addLast pipeline "handler" (handler/make-handler))
        pipeline))))

(defn start
  "Returns a Netty server."
  [port]
  (let [bootstrap (ServerBootstrap. (make-channel-factory))]
    (.setPipelineFactory bootstrap (make-pipeline-factory))
    (.bind               bootstrap (InetSocketAddress. port))))

