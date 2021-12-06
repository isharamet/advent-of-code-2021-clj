(ns advent-of-code-2021-clj.day06
  (:require [clojure.string :as str]))

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #","))
       (map #(Integer/parseInt %))))

(defn- inc-nth
  [coll n]
  (let [x (inc (nth coll n))]
    (assoc coll n x)))

(defn- add-to-nth
  [coll n a]
  (let [x (+ (nth coll n) a)]
    (assoc coll n x)))

(defn- to-age-array
  [school]
  (let [ages (into [] (repeat 9 0))]
    (reduce #(inc-nth %1 %2) ages school)))

(defn- progress
  [school]
  (let [newborns (first school)]
    (->> school
         (rest)
         (into [])
         (#(conj % newborns))
         (#(add-to-nth % 6 newborns)))))

(defn- progress-to-age
  [school age]
  (reduce (fn [s _] (progress s)) school (range age)))

(defn- solve
  [input age]
  (->> input
       (parse-input)
       (to-age-array)
       (#(progress-to-age % age))
       (reduce +)))

(defn part1
  [input]
  (solve input 80))

(defn part2
  [input]
  (solve input 256))