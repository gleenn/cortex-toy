(ns cortex-toy.adult
  (:require [cortex.experiment.train :as train]
            [cortex.nn.execute :as execute]
            [cortex.nn.layers :as layers]
            [cortex.nn.network :as network]
            [clojure.java.io :as io]
            [clojure.data.csv :refer [read-csv]]
            [clojure.string :as str]))


(defn greater-than-50K [input]
  (case input
    "<=50K" [1.0 0.0]
    ">50K" [0.0 1.0]
    (throw (Exception. (str "This isn't #{\"<=50K" ">50K\"}:" input)))))

(defn ->int [^String input]
  (if input (Long/parseLong input)))

(defn ?->nil [^String input]
  (if (= input "?")
    nil
    input))

(defn dataset [file-name]
  (let [column-conversion-fns [->int
                               identity
                               ->int
                               identity
                               ->int
                               identity
                               identity
                               identity
                               identity
                               identity
                               ->int
                               ->int
                               ->int
                               identity]]
    (with-open [reader (io/reader file-name)]
      (->> (read-csv reader)
           (map (fn [row]
                  {:x (map (fn [func val] (-> val str/trim ?->nil func))
                           column-conversion-fns
                           (butlast row))
                   :y (greater-than-50K (str/trim (last row)))}))
           doall))))

(defn dataset-numbers-only [file-name]
  (with-open [reader (io/reader file-name)]
    (->> (read-csv reader)
         (map (fn [row] (map #(get row %) [0 2 4 10 11 12 14])))
         (mapv (fn [row]
                 {:x (mapv (fn [val] (-> val str/trim ?->nil ->int)) (butlast row))
                  :y (greater-than-50K (str/trim (last row)))}))
         (remove #(->> % :x (some nil?)))
         doall)))

(defn training-data []
  (dataset-numbers-only "resources/adult.data"))

(defn testing-data []
  (dataset-numbers-only "resources/adult.test"))

(defn neural-network []
  (network/linear-network
    [(layers/input 14 1 1 :id :x)
     (layers/linear->tanh 10)
     (layers/linear 10)
     ;(layers/dropout 0.9)
     ;(layers/linear 10)
     (layers/softmax :id :y)]))

(defn train []
  (let [trained (train/train-n (neural-network)
                               (training-data)
                               (testing-data)
                               ;:batch-size 4
                               :epoch-count 1
                               :simple-loss-print? true)]
    (println "\nresults before training:")
    (clojure.pprint/pprint (execute/run neural-network dataset))
    (println "\nresults after training:")
    (clojure.pprint/pprint (execute/run trained dataset))))

(defn -main []
  (time (train)))

;(-main)
