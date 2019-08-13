(ns bh.main
  (:require [cider.nrepl :refer (cider-nrepl-handler)]
            [nrepl.server :refer (start-server stop-server)]
            [rebel-readline.main]))


(defonce nrepl-server (atom nil))

(defn start-nrepl! []
 (spit ".nrepl-port" 7888)
 (reset! nrepl-server
         (start-server :port 7888
                       :handler cider-nrepl-handler)))

(defn -main []
  (println "local dev")
  (start-nrepl!)
  (rebel-readline.main/-main))

