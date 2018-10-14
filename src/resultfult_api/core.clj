(ns resultfult-api.core
  (:require [toucan.db :as db]
            [toucan.models :as models]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.tools.namespace.repl :as c.t.n.r]

            [resultfult-api.lifecycle :refer [start-system stop-system]]
            [resultfult-api.jetty :refer [create-jetty]]
            [resultfult-api.app :refer [create-app]])
  (:gen-class))

(defonce local-server (atom nil))

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


(defn start []
    (let [system (create-system local-cfg)]
      (reset! local-server system)
      (start-system @local-server)))

(defn stop []
  (when @local-server
    (do
      (stop-system @local-server)
      (reset! local-server nil))))

(defn restart []
  (stop)
  (c.t.n.r/refresh :after 'resultfult-api.core/start))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (start))
