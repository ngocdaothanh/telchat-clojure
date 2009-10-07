(ns telchat.logic.room
  (:import [java.util.concurrent ScheduledThreadPoolExecutor TimeUnit]))

(def clients (ref #{}))

;-------------------------------------------------------------------------------

(defn resolve-send-msg [client]
  (let [t (:type client)]
    (ns-resolve t 'send-msg)))

(defn send-msg [client msg]
  (let [f (resolve-send-msg client)]
    (f client msg)))

(def reschedule nil)  ; Forward declaration

(defn broadcast [msg]
  (reschedule)
  (doall
    (map
      (fn [client]
        (send-msg client msg))
      @clients)))

;-------------------------------------------------------------------------------

(defn on-connect [client]
  (dosync (alter clients conj client))
  (broadcast "[INFO] Someone has entered\n"))

(defn on-disconnect [client]
  (dosync (alter clients disj client))
  (broadcast "[INFO] Someone has left\n"))

(defn on-msg [client msg]
  (broadcast msg))

;-------------------------------------------------------------------------------
; Broadcast "Idle" message if the room is inactive for a while.
; This is just for testing timer feature of Java.

(def INTERVAL 5)

(let [runtime     (Runtime/getRuntime)
      num-cores   (.availableProcessors runtime)
      num-threads (+ num-cores 2)]
  (def timer (ScheduledThreadPoolExecutor. num-threads)))

(defn task [] (broadcast "[INFO] Idle\n"))

(def future-task false)  ; Forward declaration
(defn reschedule []
  (if future-task (.cancel future-task false))
  (def future-task (.schedule timer task (clojure.core/long INTERVAL) TimeUnit/SECONDS)))
