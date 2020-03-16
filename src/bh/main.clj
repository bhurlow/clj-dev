(ns bh.main
  (:require [cider.nrepl :refer (cider-nrepl-handler)]
            [nrepl.server :refer (start-server)]
            [rebel-readline.main]))


(defonce nrepl-server (atom nil))


(defn start-nrepl! [port]
 (spit ".nrepl-port" port)
 (reset! nrepl-server
         (start-server :port port
                       :handler cider-nrepl-handler)))

(defn -main 
  ([] (-main 7888))
  ([port]
   (println "local dev")
   (start-nrepl! (Integer. port))
   (rebel-readline.main/-main)))

