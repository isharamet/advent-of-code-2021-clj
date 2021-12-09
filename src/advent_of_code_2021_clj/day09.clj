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
    (filter #(is-low-point? % dim coll) coords)))

(defn- solve1
  [coll]
  (->> coll
       (find-low-points)
       (map #(get-val % coll))
       (map inc)
       (reduce +)))

(defn- basin-size
  [[row col :as coord] dim coll prev visited]
  (if (and (valid-coord? coord dim)
           (not (contains? visited coord)))
    (let [x (get-val coord coll)]
      (if (or (nil? prev) (> 9 x prev))
        (let [visited (conj visited coord)
              {top :size
               visited :visited}
              (basin-size [(inc row) col] dim coll x visited)
              {bottom :size
               visited :visited}
              (basin-size [(dec row) col] dim coll x visited)
              {left :size
               visited :visited}
              (basin-size [row (dec col)] dim coll x visited)
              {right :size
               visited :visited}
              (basin-size [row (inc col)] dim coll x visited)]
          {:size (+ 1 top bottom left right)
           :visited visited})
        {:size 0
         :visited visited}))
    {:size 0
     :visited visited}))

(defn- solve2
  [coll]
  (->> coll
       (find-low-points)
       (map #(basin-size % (get-dim coll) coll nil #{}))
       (map :size)
       (sort-by #(- %))
       (take 3)
       (reduce *)))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (solve2 (parse-input input)))