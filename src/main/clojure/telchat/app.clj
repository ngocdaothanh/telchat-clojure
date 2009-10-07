(ns telchat.app
  (:gen-class)
  (:require [telchat.net.server :as server]))

(def PORT 3000)

(defn -main
  ;"Application's entry point."
  [& args]
  (server/start PORT))
