(ns advent-of-code-2021-clj.day07
  (:require [clojure.string :as str]))

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #","))
       (map #(Integer/parseInt %))))

(defn- median
  [coll]
  (let [coll (sort coll)
        size (count coll)
        mid (/ size 2)]
    (if (odd? size)
      (nth coll mid)
      (let [a (nth coll (- mid 1))
            b (nth coll mid)
            m (/ (+ a b) 2)]
        (Math/round (double m))))))

(defn- solve
  [coll]
  (let [m (median coll)
        fuel (map #(Math/abs (- % m)) coll)]
    (reduce + fuel)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (solve)))

;; (defn part2
;;   [input]
;;   (->> input
;;        (parse-input)
;;        (median)))