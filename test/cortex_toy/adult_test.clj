(ns cortex-toy.adult-test
  (:require [clojure.test :refer :all]
            [cortex-toy.adult :refer :all]
            [clojure.data.csv :refer [read-csv]]
            [clojure.java.io :as io]))

(deftest dataset-test
  (testing "turns csv rows into kewl data"
    (let [result (dataset "resources/adult.small")]
      (let [num-rows-expected 1]
        (is (= num-rows-expected (count result)))
        (is (= (repeat num-rows-expected 104) (map (comp count :x) result)))
        (is (= (repeat num-rows-expected 5) (map (comp count :y) result)))

        (is (= [{:x [39 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 77516 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0
                  0.0 13 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 1.0 0.0
                  0.0 0.0 0.0 0.0 0.0 1.0 0.0 0.0 1.0 0.0 2174 0 40 0.0 0.0 1.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
                  0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0 0.0
                  0.0 0.0]
                 :y [0.0 0.0 0.0 0.0 0.0]}] result))
        #_(is (= [{:x [39 "State-gov" 77516 "Bachelors" 13 "Never-married" "Adm-clerical" "Not-in-family" "White" "Male" 2174 0 40 "United-States"]
                   :y [1.0 0.0]}
                  {:x [50 "Self-emp-not-inc" nil "Bachelors" 13 "Married-civ-spouse" "Exec-managerial" "Husband" "White" "Male" 0 0 13 "United-States"]
                   :y [1.0 0.0]}
                  {:x [38 nil 215646 "HS-grad" 9 "Divorced" "Handlers-cleaners" "Not-in-family" "White" "Male" 0 0 40 "United-States"]
                   :y [1.0 0.0]}
                  {:x [53 "Private" 234721 "11th" 7 nil "Handlers-cleaners" "Husband" "Black" "Male" 0 0 40 "United-States"]
                   :y [0.0 1.0]}
                  {:x [28 "Private" 338409 "Bachelors" 13 "Married-civ-spouse" nil "Wife" "Black" "Female" 0 0 40 "Cuba"]
                   :y [0.0 1.0]}
                  {:x [nil nil nil nil nil nil nil nil nil nil nil nil nil nil] :y [1.0 0.0]}
                  {:x [nil nil nil nil nil "\"\"" nil nil nil nil nil nil nil nil] :y [1.0 0.0]}
                  {:x [nil nil nil nil nil "" nil nil nil nil nil nil nil nil] :y [1.0 0.0]}]

                 result))))))

#_(deftest dataset-numbers-only-test
  (testing "turns csv rows into kewl data"
    (let [result (dataset-numbers-only "resources/adult.small")]
      (is (= 4 (count result)))
      (is (= (repeat 4 6) (map (comp count :x) result)))
      (is (= (repeat 4 2) (map (comp count :y) result)))

      (is (= [{:x [39 77516 13 2174 0 40] :y [1.0 0.0]}
              #_{:x [50 nil 13 0 0 13] :y [1.0 0.0]}
              {:x [38 215646 9 0 0 40] :y [1.0 0.0]}
              {:x [53 234721 7 0 0 40] :y [0.0 1.0]}
              {:x [28 338409 13 0 0 40] :y [0.0 1.0]}
              #_{:x [nil nil nil nil nil nil] :y [1.0 0.0]}]

             result)))))

(def data
  [["39" " State-gov" " 77516" " Bachelors" " 13" " Never-married" " Adm-clerical" " Not-in-family" " White" " Male" " 2174" " 0" " 40" " United-States" " <=50K"]
   ["50" " Self-emp-not-inc" " ?" " Bachelors" " 13" " Married-civ-spouse" " Exec-managerial" " Husband" " White" " Male" " 0" " 0" " 13" " United-States" " <=50K"]
   ["?" "?" "?" "?" "?" "?" "?" "?" "?" "?" "?" "?" "?" "?" "?"]
   ])

(deftest map-row-test
  (let [result (->> data (map map-row))]
    (is (= [104 0 0] (map count result)))))

#_(deftest testing-data-test
  (testing "no nils"
    (let [result (testing-data)]
      (doseq [row result]
        (if (not= 104 (count (:x row)))
          (prn :failed-wrong-length (count (:x row)) " " row))
        (if (some nil? row)
          (prn :failed-has-nil row))))))
