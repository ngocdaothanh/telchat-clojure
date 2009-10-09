java:
	mvn compiler:compile

# Optional, try to compile .clj files for static syntax check
clojure: java
	mvn clojure:compile

start: java
	(cd target/classes; if ! test -e telchat; then ln -s ../../src/main/clojure/telchat telchat; fi) && \
	mvn exec:exec
