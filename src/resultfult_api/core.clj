(ns resultfult-api.core
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [ring.adapter.jetty :refer [run-jetty]]

            [resultfult-api.lifecycle :refer [start-system stop-system]]
            [resultfult-api.jetty :refer [create-jetty]]
            [resultfult-api.app :refer [create-app]])
  (:gen-class))

(def local-cfg
  {:db-spec
   {:dbtype "postgres"
    :dbname "restful-crud"
    :user "postgres"
    :password "example"}
   :port 3000})

(defn create-system [cfg]
  {:jetty (create-jetty cfg)
   :order [:jetty]})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [system (create-system local-cfg)]
    (start-system system)))
