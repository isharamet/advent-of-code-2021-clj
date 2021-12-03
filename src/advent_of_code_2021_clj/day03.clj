(ns advent-of-code-2021-clj.day03
  (:require [clojure.string :as str]))

(def zero (int \0))

(defn- binary-array
  [input]
  (->> input
       (.getBytes)
       (map #(- % zero))))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map binary-array)))

(defn- sum-arrays
  [l r]
  (->> (map vector l r)
       (map (fn [[x y]] (+ x y)))))

(defn- parse-int
  [arr]
  (loop [arr arr
         m 1
         acc 0]
    (if (empty? arr)
      acc
      (recur
       (drop-last arr)
       (* m 2)
       (+ acc (* (last arr) m))))))

(defn- most-common
  [sums c]
  (map #(if (> % c) 1 0) sums))

(defn- least-common
  [sums c]
  (map #(if (< % c) 1 0) sums))

(defn- one-filter
  [sums c]
  (map #(if (> % c)
          1
          (if (= % c) 1 0))
       sums))

(defn- zero-filter
  [sums c]
  (map #(if (> % c)
          0
          (if (= % c) 0 1))
       sums))

(defn- power-consumption-from-sums
  [sums c]
  (let [mc (most-common sums c)
        lc (least-common sums c)
        gamma (parse-int mc)
        epsilon (parse-int lc)]
    (* gamma epsilon)))

(defn- power-consumption
  [readings]
  (let [c (/ (count readings) 2)]
    (->> readings
         (reduce sum-arrays)
         (#(power-consumption-from-sums % c)))))

(defn- filter-on-nth
  [readings n v]
  (filter #(= v (nth % n)) readings))

(defn- life-support-rating
  [readings f]
  (loop [i 0
         readings readings]
    (let [sums (reduce sum-arrays readings)
          guide (f sums (/ (count readings) 2))]
      (if (or (= (count readings) 1)
              (= i (count guide)))
        (first readings)
        (recur
         (inc i)
         (filter-on-nth readings i (nth guide i)))))))

(defn- o2-rating
  [readings]
  (life-support-rating readings one-filter))

(defn- co2-rating
  [readings]
  (life-support-rating readings zero-filter))

(defn- life-support
  [readings]
  (let [o2-bin (o2-rating readings)
        co2-bin (co2-rating readings)
        o2 (parse-int o2-bin)
        co2 (parse-int co2-bin)]
    (* o2 co2)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (power-consumption)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (life-support)))
