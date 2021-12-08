(ns advent-of-code-2021-clj.day08
  (:require [clojure.string :as str]))

(def digit-segments
  {1 2
   2 5
   3 5
   4 4
   5 5
   6 6
   7 3
   8 7
   9 6
   0 6})

(def unique-lengths
  #{2, 3, 4, 7})

(defn- parse-line
  [line]
  (->> line
       (#(str/split % #" \| "))
       (map #(str/split % #" "))))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)))

(defn- solve1
  [signals]
  (->> signals
       (map (fn [[_ xs]] xs))
       (map (fn [xs]
              (filter #(contains? unique-lengths (count %)) xs)))
       (map count)
       (reduce +)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (solve1)))

;; (defn part2
;;   [input]
;;   (->> input
;;        (parse-input)))