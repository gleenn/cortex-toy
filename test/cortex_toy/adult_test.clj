(ns cortex-toy.adult-test
  (:require [clojure.test :refer :all]
            [cortex-toy.adult :refer [dataset]]))

(deftest dataset-test
  (testing "turns csv rows into kewl data"
    (let [result (dataset "resources/adult.small")]
      (is (= 6 (count result)))
      (is (= (repeat 6 14) (map (comp count :x) result)))
      (is (= (repeat 6 2) (map (comp count :y) result)))

      (is (= [{:x [39 "State-gov" 77516 "Bachelors" 13 "Never-married" "Adm-clerical" "Not-in-family" "White" "Male" 2174 0 40 "United-States"]
               :y [1.0 0.0]}
              {:x [50 "Self-emp-not-inc" nil "Bachelors" 13 "Married-civ-spouse" "Exec-managerial" "Husband" "White" "Male" 0 0 13 "United-States"]
               :y [1.0 0.0]}
              {:x [38 nil 215646 "HS-grad" 9 "Divorced" "Handlers-cleaners" "Not-in-family" "White" "Male" 0 0 40 "United-States"]
               :y [1.0 0.0]}
              {:x [53 "Private" 234721 "11th" 7 nil "Handlers-cleaners" "Husband" "Black" "Male" 0 0 40 "United-States"]
               :y [1.0 0.0]}
              {:x [28 "Private" 338409 "Bachelors" 13 "Married-civ-spouse" nil "Wife" "Black" "Female" 0 0 40 "Cuba"]
               :y [1.0 0.0]}
              {:x [nil nil nil nil nil nil nil nil nil nil nil nil nil nil] :y [1.0 0.0]}]

             result)))))
