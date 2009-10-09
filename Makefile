java:
	mvn compiler:compile

# Optional, try to compile .clj files for static syntax check
clojure: java
	mvn clojure:compile

# Compiling .clj files is not necessary. For rapid development, we should not
# compile .clj files AOT.
#
# Because we cannot set classpath to Clojure source files, we must create symbolic
# link to Clojure files in target/classes.
start: java
	(cd target/classes; if ! test -e telchat; then ln -s ../../src/main/clojure/telchat telchat; fi) && \
	mvn exec:exec
