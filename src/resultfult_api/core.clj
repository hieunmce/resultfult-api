(ns resultfult-api.core
  (:require [toucan.db :as db]
            [clojure.tools.namespace.repl :as tn]
            [toucan.models :as models]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.route]
            [compojure.api.sweet :refer [api routes undocumented]]
            [resultfult-api.user :refer [user-routes]])
  (:gen-class))

(defonce jetty-server (atom nil))

(def db-spec
  {:dbtype "postgres"
   :dbname "restful-crud"
   :user "postgres"
   :password "example"})

(def not-found-routes
  (compojure.route/not-found
   (ring.util.http-response/not-found {:not "found"})))

(defonce jetty-server (atom nil))

(def create-app
  (api
   (wrap-reload (apply routes user-routes
                       (undocumented not-found-routes)))))
(defn start []
  (let [app create-app]
    (db/set-default-db-connection! db-spec)
    (models/set-root-namespace! 'resultfult-api.models)
    (reset! jetty-server (run-jetty (wrap-reload app) {:port 3000 :join? false}))))

(defn stop []
  (when @jetty-server
    (.stop @jetty-server)
    (reset! jetty-server nil)))

(defn restart []
  (stop)
  (tn/refresh :after 'resultfult-api.core/start))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start))
