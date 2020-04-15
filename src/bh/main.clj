(ns bh.main
  (:require [cider.nrepl :refer (cider-nrepl-handler)]
            [clojure.repl :as clj-repl]
            [nrepl.server :refer (start-server)]
            [rebel-readline.core :as rebel-core]
            [rebel-readline.clojure.main :as rebel-clj-main]))


(defonce nrepl-server (atom nil))


(defn start-nrepl! [port]
 (spit ".nrepl-port" port)
 (reset! nrepl-server
         (start-server :port port
                       :handler cider-nrepl-handler)))


(defn -handle-sigint-form
  []
  `(let [thread# (Thread/currentThread)]
     (clj-repl/set-break-handler! (fn [_signal#] (.stop thread#)))))

(defn start-repl!
  [& [init-ns]]
  (rebel-core/ensure-terminal
    (rebel-clj-main/repl*
      {:init (fn []
               ; HACK: rebel-readline doesn't have a convenient way to change init ns (it's always `user`).
               ; See https://github.com/bhauman/rebel-readline/issues/157.
               (when (some? init-ns)
                 (let [init-ns (symbol init-ns)]
                   (require init-ns)
                   (in-ns init-ns))))

       :eval (fn [form]
               ; HACK: allows Ctrl+C to interrupt long running tasks.
               ; See https://github.com/bhauman/rebel-readline/issues/180#issuecomment-429057767.
               (eval `(do ~(-handle-sigint-form) ~form)))})))

(defn -main 
  ([] (-main 7888))
  ([port]
   (println "local dev")
   (start-nrepl! (Integer. port))
   (start-repl! 'user.main)))

