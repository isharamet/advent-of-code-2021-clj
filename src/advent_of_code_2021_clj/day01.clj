(ns advent-of-code-2021-clj.day01
  (:require [clojure.string :as str]))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map #(Integer/parseInt %))))

(defn- count-increase
  [xs]
  (->> xs
       (partition 2 1)
       (map (fn [[l r]] (- r l)))
       (filter pos-int?)
       (count)))))

(defn part1
  [input]
  (->> input
       (parse-input)
       (partition 2 1)
       (map (fn [[l r]] (- r l)))
       (filter pos-int?)
       (count))))

(defn part2
  [input]
  (->> input
       (parse-input)
       (partition 3 1)
       (map #(reduce + %))))