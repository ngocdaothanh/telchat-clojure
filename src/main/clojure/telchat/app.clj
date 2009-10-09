(ns telchat.app
  (:require [telchat.net.server :as server]))

(def PORT 3000)

(server/start PORT)
(println (str "Server started on port " PORT))
