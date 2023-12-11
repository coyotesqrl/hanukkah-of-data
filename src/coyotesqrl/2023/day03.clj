;; ## Hanukkah of Data 2023
;; ### Day 3 (https://hanukkah.bluebird.sh/5784/3/)
(ns coyotesqrl.2023.day03
  (:require [clojure.data.json :as json]
            [clojure.string :as str]
            [coyotesqrl.utils :as utils]
            [nextjournal.clerk :as clerk]
            [tick.core :as t]
            [tick.alpha.interval :as ti]))

^{::clerk/visibility {:result :hide}}
(def rabbit-years
  #{(ti/new-interval (t/date "1903-01-29") (t/date "1904-02-15"))
    (ti/new-interval (t/date "1915-02-14") (t/date "1916-02-03"))
    (ti/new-interval (t/date "1927-02-02") (t/date "1928-01-22"))
    (ti/new-interval (t/date "1939-02-19") (t/date "1940-02-07"))
    (ti/new-interval (t/date "1951-02-06") (t/date "1952-01-26"))
    (ti/new-interval (t/date "1963-01-25") (t/date "1964-02-12"))
    (ti/new-interval (t/date "1975-02-11") (t/date "1976-01-30"))
    (ti/new-interval (t/date "1987-01-29") (t/date "1988-02-16"))
    (ti/new-interval (t/date "1999-02-16") (t/date "2000-02-04"))
    (ti/new-interval (t/date "2011-02-03") (t/date "2012-01-22"))
    (ti/new-interval (t/date "2023-01-22") (t/date "2024-02-09"))})

^{::clerk/visibility {:result :hide}}
(def cancer
  (ti/new-interval (t/date "2023-06-21") (t/date "2023-07-23")))

^{::clerk/visibility {:result :hide}}
(defn rabbit? [{:keys [birthdate]}]
  (some #(ti/contains? % (t/date birthdate)) rabbit-years))

^{::clerk/visibility {:result :hide}}
(defn cancer?
  "Set the year to this year for Cancer Zodiac check to simplify."
  [{:keys [birthdate]}]
  (let [this-year (t/new-date 2023 (t/month birthdate) (t/day-of-month birthdate))]
    (ti/contains? cancer this-year)))

^{::clerk/visibility {:result :hide}}
(defn jp-neighborhood?
  [{:keys [citystatezip]}]
  (str/starts-with? citystatezip "Jamaica, NY"))

(->> "coyotesqrl/2023/noahs-customers.jsonl"
     (utils/input->seq)
     (map json/read-json)
     (filter cancer?)
     (filter rabbit?)
     (filter jp-neighborhood?)
     (map :phone)
     (first)
     (utils/answer-block))
