(defproject cortex-toy "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :main cortex-toy.core
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [thinktopic/cortex "0.9.22"]
                 [thinktopic/experiment "0.9.22"]
                 [org.clojure/data.csv "0.1.3"]]
  :profiles {:dev {:plugins [[com.jakemccrary/lein-test-refresh "0.19.0"]]}})
