(ns advent-of-code-2021-clj.day06
  (:require [clojure.string :as str]))

(def target-age 80)

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #","))
       (map #(Integer/parseInt %))))

(defn- update-age
  [age]
  (if (> age 0) (dec age) 6))

(defn- progress
  [school]
  (let [newborns (count (filter zero? school))
        school (map update-age school)]
    (concat school (repeat newborns 8))))

(defn- progress-to-age
  [school age]
  (reduce (fn [s _] (progress s)) school (range age)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (#(progress-to-age % target-age))
       count))

(defn part2
  [input]
  (->> input
       (parse-input)))