(ns telchat.net.handler
  (:require [telchat.logic.room :as room])
  (:require [clojure.contrib.logging :as logging])
  (:import
    [telchatnet             Handler]
    [org.jboss.netty.buffer ChannelBuffers]))

(defn client
  "Returns a client to be used at the logic part."
  [channel]
  {:type 'telchat.net.handler, :channel channel})

(defn channel-buffer
  "Returns a channel buffer from string data."
  [msg]
  (ChannelBuffers/copiedBuffer msg "UTF-8"))

(defn send-msg
  "Sends string data to the channel of the client."
  [client msg]
  (let [channel (:channel client)
        cb      (channel-buffer msg)]
    (.write channel cb)))

;-------------------------------------------------------------------------------

(defn handler
  "Returns a Netty handler."
  []
  (proxy [Handler] []
    (channelConnected [ctx e]
      (let [c (client (.getChannel e))]
        (room/on-connect c)))

    (channelDisconnected [ctx e]
      (let [c (client (.getChannel e))]
        (room/on-disconnect c)))

    (messageReceived [ctx e]
      (let [c   (client (.getChannel e))
            cb  (.getMessage e)
            msg (.toString cb "UTF-8")]  ; Get string data from channel buffer
        (room/on-msg c msg)))

    ;; When there is any error, close the channel. channelDisconnected will
    ;; be called.
    (exceptionCaught
      [ctx e]
      (let [throwable (.getCause e)]
        (logging/warn "@exceptionCaught" throwable))
      (-> e .getChannel .close))))
