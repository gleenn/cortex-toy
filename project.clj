(defproject cortex-toy "0.1.0-SNAPSHOT"
  :main cortex-toy.adult
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [thinktopic/cortex "0.9.22"]
                 [thinktopic/experiment "0.9.22"]
                 [org.clojure/data.csv "0.1.3"]
                 [environ "1.1.0"]]

  ;:plugins [[lein-environ "1.1.0"]]

  :profiles {:test         [:project/test #_:profiles/test]
             :project/test {:dependencies [[expound "0.7.1"]
                                           [ring/ring-mock "0.3.0"]
                                           [audiogum/picomock "0.1.11"]]
                            :plugins      [[venantius/ultra "0.5.1" :exclusions [org.clojure/clojure]]
                                           [lein-auto "0.1.3"]
                                           ;[pjstadig/humane-test-output "0.8.1"]
                                           ]}})
