(ns cortex-toy.adult
  (:require [cortex.experiment.train :as train]
            [cortex.nn.execute :as execute]
            [cortex.nn.layers :as layers]
            [cortex.nn.network :as network]
            [clojure.java.io :as io]
            [clojure.data.csv :refer [read-csv]]
            [clojure.string :as str]))


;(def dataset [{:x [5.1 3.5 1.4 0.2] :y [1.0 0.0 0.0]}])

(def greater-than-50K
  {"<=50K." [1.0 0.0]
   ">50K."  [0.0 1.0]})

(defn ->int [^String input]
  (Long/parseLong (str/trim input)))

(defn dataset [file-name]
  (with-open [reader (io/reader file-name)]
    (->> (read-csv reader)
         (reduce (fn [result row]
                   (let [converted-columns (map #(%1 %2) [->int str/trim ->int str/trim ->int
                                              str/trim str/trim str/trim str/trim
                                              str/trim ->int ->int ->int str/trim]
                                    (butlast row))]
                     (if (some nil? converted-columns)
                       result
                       (hash-map :x converted-columns
                                 :y (greater-than-50K (str/trim (last row))))))
                   []))
         doall)))

(def training-data
  (dataset "resources/adult.data"))

(def testing-data
  (dataset "resources/adult.test"))

(def neural-network
  (network/linear-network
    [(layers/input 15 1 1 :id :x)
     (layers/linear->tanh 10)
     (layers/linear 3)
     (layers/softmax :id :y)
     ]))

(defn train []
  (let [trained (train/train-n neural-network
                               training-data
                               testing-data
                               :batch-size 4
                               :epoch-count 300
                               :simple-loss-print? true)]
    (println "\nresults before training:")
    (clojure.pprint/pprint (execute/run neural-network dataset))
    (println "\nresults after training:")
    (clojure.pprint/pprint (execute/run trained dataset))))

(defn -main []
  (time (train)))

(-main)