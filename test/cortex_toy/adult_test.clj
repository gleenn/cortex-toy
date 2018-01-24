(ns cortex-toy.adult-test
  (:require [cortex-toy.adult :as adult]
            [clojure.test :refer :all]
            [cortex-toy.adult :refer [dataset]]
            [clojure.string :as str]
            [clojure.java.io :as io]))

(deftest dataset-test
  (testing "turns csv rows into kewl data"
    (is (= [{:x [25
                 "Private"
                 226802
                 "11th"
                 7
                 "Never-married"
                 "Machine-op-inspct"
                 "Own-child"
                 "Black"
                 "Male"
                 0
                 0
                 40
                 "United-States"
                 ]
             :y [1.0 0.0]}
            {:x [38
                 "Private"
                 89814
                 "HS-grad"
                 9
                 "Married-civ-spouse"
                 "Farming-fishing"
                 "Husband"
                 "White"
                 "Male"
                 0
                 0
                 50
                 "United-States"]
             :y [1.0 0.0]}]
           (take 2 (dataset "resources/adult.test"))))))
