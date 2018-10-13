(ns resultfult-api.core
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [ring.adapter.jetty :refer [run-jetty]]
            [compojure.route]
            [compojure.api.sweet :refer [api routes undocumented]]
            [resultfult-api.user :refer [user-routes]])
  (:gen-class))

(def db-spec
  {:dbtype "postgres"
   :dbname "restful-crud"
   :user "postgres"
   :password "example"})

(def app (api (apply routes user-routes (undocumented (compojure.route/not-found (ring.util.http-response/not-found {:not "found"}))))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (db/set-default-db-connection! db-spec)
  (models/set-root-namespace! 'resultfult-api.models)
  (run-jetty app {:port 3000}))
