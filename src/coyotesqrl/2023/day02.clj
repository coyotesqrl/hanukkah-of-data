;; ## Hanukkah of Data 2023
;; ### Day 2 (https://hanukkah.bluebird.sh/5784/2/)
(ns coyotesqrl.2023.day02
  (:require [clojure.data.json :as json]
            [clojure.string :as str]
            [coyotesqrl.utils :as utils]
            [nextjournal.clerk :as clerk]))

^{::clerk/visibility {:result :hide}}
(defn customer-initials [c]
  (->> (str/split c #"\s")
       (map first)
       (apply str)))

^{::clerk/visibility {:result :hide}}
(def jp-customers
  (->> "coyotesqrl/2023/noahs-customers.jsonl"
       (utils/input->seq)
       (map json/read-json)
       (filter #(= "JP" (customer-initials (:name %))))))

^{::clerk/visibility {:result :hide}}
(defn filter-orders [customer-ids item-skus]
  (->> "coyotesqrl/2023/noahs-orders.jsonl"
       (utils/input->seq)
       (map json/read-json)
       (filter #(str/starts-with? (:ordered %) "2017"))
       (filter #(customer-ids (:customerid %)))
       (filter #(some item-skus (map :sku (:items %))))))

(let [jp-candidates (->> jp-customers
                         (map :customerid)
                         (set))
      jp (->> (filter-orders jp-candidates #{"DLI8820" "BKY1573" "BKY5717"})
              (map :customerid)
              (set))]
  (->> jp-customers
       (filter #(jp (:customerid %)))
       (map :phone)
       (first)
       (utils/answer-block)))
