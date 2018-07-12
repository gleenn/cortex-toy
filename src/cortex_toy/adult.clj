(ns cortex-toy.adult
  (:require [cortex.experiment.train :as train]
            [cortex.nn.execute :as execute]
            [cortex.nn.layers :as layers]
            [cortex.nn.network :as network]
            [clojure.java.io :as io]
            [clojure.data.csv :refer [read-csv]]
            [clojure.string :as str]
            [cortex.util :as util]))


(defn ->int [^String input]
  (if-not (str/blank? input) (Long/parseLong input) nil))

(defn ?->nil [^String input]
  (if (= input "?")
    nil
    input))

(def column-conversion-fns [->int
                            #(util/one-hot-encode #{"Federal-gov" "Local-gov" "Never-worked" "Private" "Self-emp-inc" "Self-emp-not-inc" "State-gov" "Without-pay"} %)
                            ->int
                            #(util/one-hot-encode #{"10th" "11th" "12th" "1st-4th" "5th-6th" "7th-8th" "9th" "Assoc-acdm" "Assoc-voc" "Bachelors" "Doctorate" "HS-grad" "Masters" "Preschool" "Prof-school" "Some-college"} %)
                            ->int
                            #(util/one-hot-encode #{"Divorced" "Married-AF-spouse" "Married-civ-spouse" "Married-spouse-absent" "Never-married" "Separated" "Widowed"} %)
                            #(util/one-hot-encode #{"Adm-clerical" "Armed-Forces" "Craft-repair" "Exec-managerial" "Farming-fishing" "Handlers-cleaners" "Machine-op-inspct" "Other-service" "Priv-house-serv" "Prof-specialty" "Protective-serv" "Sales" "Tech-support" "Transport-moving"} %)
                            #(util/one-hot-encode #{"Husband" "Not-in-family" "Other-relative" "Own-child" "Unmarried" "Wife"} %)
                            #(util/one-hot-encode #{"Amer-Indian-Eskimo" "Asian-Pac-Islander" "Black" "Other" "White"} %)
                            #(util/one-hot-encode #{"Male" "Female"} %)
                            ->int
                            ->int
                            ->int
                            #(util/one-hot-encode #{"Cambodia" "Canada" "China" "Columbia" "Cuba"
                                                    "Dominican-Republic" "Ecuador" "El-Salvador" "England" "France"
                                                    "Germany" "Greece" "Guatemala" "Haiti" "Honduras" "Hong"
                                                    "Hungary" "India" "Iran" "Ireland" "Italy" "Jamaica" "Japan"
                                                    "Laos" "Mexico" "Nicaragua" "Outlying-US(Guam-USVI-etc)" "Peru"
                                                    "Philippines" "Poland" "Portugal" "Puerto-Rico" "Scotland"
                                                    "South" "Taiwan" "Thailand" "Trinadad&Tobago" "United-States"
                                                    "Vietnam" "Yugoslavia"} %)])

(defn map-row [row]
  (let [result (->> row
                    (map
                      (fn [func val]
                        (some-> val str/trim ?->nil func))
                      column-conversion-fns)
                    flatten
                    (into []))]
    (if (some nil? result) nil result)))

(defn dataset [file-name]
  (with-open [reader (io/reader file-name)]
    (->> (read-csv reader)
         (map (fn [row]
                {:x (map-row row)
                 :y (some->> row last str/trim ?->nil (util/one-hot-encode #{">50K" "<=50K"}))}))
         (remove #(->> % :x nil?))
         (remove #(->> % :x (some nil?)))
         (remove #(->> % :y nil?))
         (remove #(->> % :y (some nil?)))
         (into []))))

#_(defn dataset-numbers-only [file-name]
  (with-open [reader (io/reader file-name)]
    (->> (read-csv reader)
         (map (fn [row] (map #(get row %) [0 2 4 10 11 12 14])))
         (map (fn [row]
                {:x (map (fn [val] (some-> val str/trim ?->nil ->int)))
                 :y (some-> row last str/trim greater-than-50K)}))
         (remove #(->> % :x (some nil?)))
         doall)))

(defn training-data []
  (dataset "resources/adult.data"))

(defn testing-data []
  (dataset "resources/adult.test"))

#_(defn neural-network-numbers-only []
  (network/linear-network
    [(layers/input #_14 6 1 1 :id :x)
     (layers/linear->tanh 10)
     (layers/linear 2)
     ;(layers/dropout 0.9)
     ;(layers/linear 10)
     (layers/softmax :id :y)]))

(defn neural-network []
  (network/linear-network
    [(layers/input 104 1 1 :id :x)
     (layers/linear->tanh 10)
     (layers/dropout 0.9)
     (layers/linear->tanh 10)
     (layers/linear 2)
     (layers/softmax :id :y)]))

(defn train []
  (let [trained (train/train-n (neural-network)
                               (training-data)
                               (testing-data)
                               :epoch-count 30
                               :simple-loss-print? true)]
    (println "\nresults before training:")
    (clojure.pprint/pprint (execute/run (neural-network) (testing-data)))
    (println "\nresults after training:")
    (clojure.pprint/pprint (execute/run trained (testing-data)))))

(defn -main []
  (time (train)))

;(-main)
