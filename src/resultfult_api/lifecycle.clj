(ns resultfult-api.lifecycle
  (:require [ring.adapter.jetty :refer [run-jetty]]))


(defprotocol LifeCycle
  (start [this])
  (stop [this]))


(defn start-system [system]
  (doseq [s (->> system :order (map system))]
    (stop s)))

;; (defrecord JettyServer [cfg state]
;;   LifeCycle
;;   (start [_]
;;     (reset! state (run-jetty ))))
