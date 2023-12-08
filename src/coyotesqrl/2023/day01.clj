;; ## Hanukkah of Data 2023
;; ### Day 1 (https://hanukkah.bluebird.sh/5784/1/)
(ns coyotesqrl.2023.day01
 (:require [clojure.data.json :as json]
           [clojure.string :as str]
           [coyotesqrl.utils :as utils]
           [nextjournal.clerk :as clerk]))

^{::clerk/visibility {:result :hide}}
(defn upper-last-name [name]
  (str/upper-case (last (str/split name #"\s"))))

^{::clerk/visibility {:result :hide}}
(defn names-and-numbers [source]
  (->> source
       (utils/input->seq)
       (map json/read-json)
       (map (juxt :name :phone))
       (map (fn [[n p]] [(upper-last-name n) p]))))

^{::clerk/visibility {:result :hide}}
(defn convert-name [name]
  (let [digitized (for [c name]
                    (condp some [c]
                      (set "ABC") 2
                      (set "DEF") 3
                      (set "GHI") 4
                      (set "JKL") 5
                      (set "MNO") 6
                      (set "PQRS") 7
                      (set "TUV") 8
                      (set "WXYZ") 9))]
    (apply str digitized)))

(let [people (names-and-numbers "coyotesqrl/2023/noahs-customers.jsonl")]
  (->> people
       (filter #(= 10 (count (first %))))
       (filter (fn [[n p]] (= (str/replace p #"-" "") (convert-name n))))
       (map second)
       (first)
       (utils/answer-block)))
