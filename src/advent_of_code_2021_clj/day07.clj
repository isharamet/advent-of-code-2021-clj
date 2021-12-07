(ns advent-of-code-2021-clj.day07
  (:require [clojure.string :as str]))

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #","))
       (map #(Integer/parseInt %))))

(defn round
  [r]
  (Math/round (double r)))

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
        (round m)))))

(defn- fuel-lin
  [coll m]
  (map #(Math/abs (- % m)) coll))

(defn- fuel-exp-dist
  [dist]
  (int (/ (+ (* dist dist) dist) 2)))

(defn- fuel-exp
  [coll m]
  (->> coll
       (map #(Math/abs (- m %)))
       (map fuel-exp-dist)))

(defn- solve-lin
  [coll]
  (let [m (median coll)
        fuel (fuel-lin coll m)]
    (reduce + fuel)))

(defn- mean
  [coll]
  (let [size (count coll)
        sum (reduce + coll)]
    (/ sum size)))

(defn- solve-exp
  [coll]
  (let [m (mean coll)
        mlo (Math/floor m)
        mhi (Math/ceil m)
        flo (fuel-exp coll mlo)
        fhi (fuel-exp coll mhi)]
    (->> [flo fhi]
         (map #(reduce + %))
         (apply min))))

(defn part1
  [input]
  (->> input
       (parse-input)
       (solve-lin)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (solve-exp)))