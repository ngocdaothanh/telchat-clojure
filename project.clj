(defproject telchat-clojure "0.1-SNAPSHOT"
  :description "Chat program using telnet and Clojure"
  :dependencies [[org.clojure/clojure "1.2.0"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [org.jboss.netty/netty       "3.2.2.Final"]]
  :repositories {"JBoss" "http://repository.jboss.org/nexus/content/groups/public/"}
  :main telchat.app)
