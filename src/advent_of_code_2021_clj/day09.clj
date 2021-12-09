(ns advent-of-code-2021-clj.day09
  (:require [clojure.string :as str]))

(def zero (int \0))

(defn- parse-line
  [line]
  (->> line
       (to-array)
       (map int)
       (map #(- % zero))
       (into [])))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)
       (into [])))

(defn- get-dim
  [coll]
  (let [h (count coll)
        w (count (first coll))]
    [h w]))

(defn- get-val
  [[row col] coll]
  (nth (nth coll row) col))

(defn- gen-coords
  [[height width]]
  (let [indices (range (* height width))]
    (map (fn [x]
           [(int (/ x width)) (mod x width)])
         indices)))

(defn- valid-coord?
  [[row col] [height width]]
  (and (>= row 0) (< row height)
       (>= col 0) (< col width)))

(defn- gen-adjacent
  [[row col] dim]
  (let [adj [[(dec row) col]
             [(inc row) col]
             [row (dec col)]
             [row (inc col)]]]
    (filter #(valid-coord? % dim) adj)))

(defn- is-low-point?
  [[row col] dim coll]
  (let [x (get-val [row col] coll)
        adj (gen-adjacent [row col] dim)]
    (->> adj
         (filter #(valid-coord? % dim))
         (map #(get-val % coll))
         (every? #(> % x)))))

(defn- find-low-points
  [coll]
  (let [dim (get-dim coll)
        coords (gen-coords dim)]
    (->> coords
         (filter #(is-low-point? % dim coll))
         (map #(get-val % coll)))))

(defn- solve1
  [coll]
  (->> coll
       (find-low-points)
       (map inc)
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