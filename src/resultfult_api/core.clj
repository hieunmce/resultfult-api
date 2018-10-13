(ns resultfult-api.core
  (:require [toucan.db :as db]
            [toucan.models :as models])
  (:gen-class))

(def db-spec
  {:dbtype "postgres"
   :dbname "restful-crud"
   :user "postgres"
   :password "example"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (db/set-default-db-connection! db-spec)
  (models/set-root-namespace! 'resultfult-api.models))
