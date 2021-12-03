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

(defn most-common
  [sums c]
  (->> sums
       (map #(if (> % c) 1 0))
       (parse-int)))

(defn least-common
  [sums c]
  (->> sums
       (map #(if (< % c) 1 0))
       (parse-int)))

(defn- rates
  [sums c]
  (let [gamma (most-common sums c)
        epsilon (least-common sums c)]
    {:gamma gamma
     :epsilon epsilon}))

(defn- generate-report
  [readings]
  (let [c (/ (count readings) 2)]
    (->> readings
         (reduce sum-arrays)
         (#(rates % c)))))

(defn- xyz
  [readings f]
  (loop [i 0
         readings readings]))
(defn part1
  [input]
  (->> input
       (parse-input)
       (generate-report)
       ((fn [{gamma :gamma
              epsilon :epsilon}]
          (* gamma epsilon)))))

(defn part2
  [input]
  (->> input
       (parse-input)))
