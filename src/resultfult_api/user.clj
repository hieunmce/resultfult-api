(ns resultfult-api.user
  (:require [schema.core :as s]
            [buddy.hashers :as hashers]
            [resultfult-api.string-util :as str]
            [clojure.set :refer [rename-keys]]
            [toucan.db :as db]
            [resultfult-api.models.user :refer [User]]
            [ring.util.http-response :refer [created]]
            [compojure.api.sweet :refer [POST]]))

(defn valid-username? [name]
  (str/non-blank-with-max-length? 50 name))

(defn valid-password? [password]
  (str/length-in-range? 5 50 password))

(s/defschema UserRequestSchema
  {:username (s/constrained s/Str valid-username?)
   :password (s/constrained s/Str valid-password?)
   :email (s/constrained s/Str str/email?)})

(defn id->created [id]
  (created (str "/users/" id) {:id id}))

(defn canonicalize-user-req [user-req]
  (-> (update user-req :password hashers/derive)
      (rename-keys {:password :password_hash})))

(def spy #(do (println "DEBUG: " %) %))

(defn create-user-handler [create-user-req]
  (->> (canonicalize-user-req create-user-req)
       (db/insert! User)
       spy
       :id
       id->created))

(def user-routes
  [(POST "/users" []
     :body [create-user-req UserRequestSchema]
     (create-user-handler create-user-req))])
