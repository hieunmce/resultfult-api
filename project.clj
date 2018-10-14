(defproject resultfult-api "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ; WEB
                 [prismatic/schema "1.1.9"]
                 [metosin/compojure-api "2.0.0-alpha26"]
                 [ring "1.7.0"]
                 [ring/ring-jetty-adapter "1.6.3"]
                 [org.clojure/tools.namespace "0.2.11"]
                 ; Database
                 [toucan "1.1.9"]
                 [org.postgresql/postgresql "42.2.4"]
                 ; Password Hashing
                 [buddy/buddy-hashers "1.3.0"]]
  :main ^:skip-aot resultfult-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
