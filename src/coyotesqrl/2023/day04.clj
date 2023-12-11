;; ## Hanukkah of Data 2023
;; ### Day 4 (https://hanukkah.bluebird.sh/5784/4/)
(ns coyotesqrl.2023.day04
  (:require [clojure.data.json :as json]
            [clojure.string :as str]
            [coyotesqrl.utils :as utils]
            [nextjournal.clerk :as clerk]
            [tick.core :as t]
            [tick.locale-en-us]))

^{::clerk/visibility {:result :hide}}
(def date-parser (t/formatter "yyyy-MM-dd HH:mm:ss"))

^{::clerk/visibility {:result :hide}}
(defn morning-order? [{:keys [shipped ordered]}]
  (and (= 4 (t/hour (t/parse-date-time shipped date-parser)))
       (= 4 (t/hour (t/parse-date-time ordered date-parser)))))

^{::clerk/visibility {:result :hide}}
(defn after-jp-order? [{:keys [shipped]}]
  (t/> (t/parse-date shipped date-parser) (t/date "2017-04-05")))

^{::clerk/visibility {:result :hide}}
(defn bakery-order? [{:keys [items]}]
  (->> items
       (map :sku)
       (filter #(str/starts-with? % "BKY"))
       (count)
       (<= 1)))

^{::clerk/visibility {:result :hide}}
(def early-morning-bakery-order-from-2017
  (->> "coyotesqrl/2023/noahs-orders.jsonl"
       (utils/input->seq)
       (map json/read-json)
       (filter morning-order?)
       (filter after-jp-order?)
       (filter bakery-order?)
       (first)))

(->> "coyotesqrl/2023/noahs-customers.jsonl"
     (utils/input->seq)
     (map json/read-json)
     (filter #(= (:customerid %) (:customerid early-morning-bakery-order-from-2017)))
     (map :phone)
     (first)
     (utils/answer-block))
