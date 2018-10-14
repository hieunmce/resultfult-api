(ns resultfult-api.jetty
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [toucan.models :as models]
            [toucan.db :as db]
            [ring.adapter.jetty :refer [run-jetty]]

            [resultfult-api.lifecycle :refer [LifeCycle]]
            [resultfult-api.app :refer [create-app]]))

(defrecord JettyServer [cfg state]
  LifeCycle
  (start [_]
    (let [app create-app]
      (db/set-default-db-connection! (:db-spec cfg))
      (models/set-root-namespace! 'resultfult-api.models)
      (reset! state (run-jetty app {:port (:port cfg) :join? false}))))
  (stop [_]
    (when @state
      (.stop @state)
      (reset! state nil))))

(defn create-jetty [cfg]
  (->JettyServer cfg (atom nil)))
