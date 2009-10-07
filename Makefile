compile:
	mvn compiler:compile && mvn clojure:compile

start:
	mvn exec:java -Dexec.mainClass="telchat.app"
