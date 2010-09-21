(ns telchat.app
  (:require [telchat.net.server :as server])
  (:gen-class))

(def *port* 3000)

(defn -main [& args]
  (server/start *port*)
  (println (str "Server started on port " *port*)))
