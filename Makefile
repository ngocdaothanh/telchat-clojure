java:
	mvn compiler:compile

# Optional, try to compile .clj files for static syntax check
clojure: java
	mvn clojure:compile

start: java
	mvn clojure:run

