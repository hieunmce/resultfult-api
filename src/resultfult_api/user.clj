(ns resultfult-api.user
  (:require [schema.core :as s]
            [buddy.hashers :as hashers]
            [resultfult-api.string-util :as str]
            [clojure.set :refer [rename-keys]]
            [toucan.db :as db]
            [resultfult-api.models.user :refer [User]]
            [ring.util.http-response :refer [created ok]]
            [compojure.api.sweet :refer [POST GET PUT]]))

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
       :id
       id->created))

(defn get-users-handler []
  (->> (db/select User)
       (map #(dissoc % :password_hash))
       ok))

(defn get-user-handler [id]
  (-> (User id)
      (dissoc :password_hash)
      ok))

(defn update-user-handler [id update-user-req]
  (db/update! User id (canonicalize-user-req update-user-req))
  (ok))

(def user-routes
  [(POST "/users" []
     :body [create-user-req UserRequestSchema]
     (create-user-handler create-user-req))
   (GET "/users" []
     (get-users-handler))
   (GET "/users/:id" []
     :path-params [id :- s/Int]
     (get-user-handler id))
   (PUT "/users/:id" []
     :path-params [id :- s/Int]
     :body [update-user-req UserRequestSchema]
     (update-user-handler id update-user-req))])
