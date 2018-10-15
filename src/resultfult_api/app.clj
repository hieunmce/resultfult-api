(ns resultfult-api.app
  (:require [compojure.route :refer [not-found]]
            [compojure.api.sweet :refer [api routes undocumented]]
            [resultfult-api.user :refer [user-routes]])
  (:gen-class))

(def swagger-config
  {:ui "/swagger"
   :spec "/swagger.json"
   :options {:ui {:validatorUrl nil}
             :data {:info {:version "1.0.0", :title "Restful CRUD API"}}}})

(def not-found-routes
  (not-found
   (ring.util.http-response/not-found {:not "found"})))

(def create-app
  (api {:swagger swagger-config}
       (apply routes user-routes
              (undocumented not-found-routes))))
