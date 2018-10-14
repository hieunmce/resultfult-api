(ns resultfult-api.app
  (:require [ring.middleware.reload :refer [wrap-reload]]
            [compojure.route :refer [not-found]]
            [compojure.api.sweet :refer [api routes undocumented]]
            [resultfult-api.user :refer [user-routes]])
  (:gen-class))

(def not-found-routes
  (not-found
   (ring.util.http-response/not-found {:not "found"})))

(def create-app
  (api
   (wrap-reload (apply routes user-routes
                       (undocumented not-found-routes)))))
