(ns advent-of-code-2021-clj.day09
  (:require [clojure.string :as str]
            [advent-of-code-2021-clj.matrix :as mx]))

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

(defn- is-low-point?
  [[row col] dim m]
  (let [x (mx/get-val [row col] m)
        adj (mx/gen-neighbours [row col] dim)]
    (->> adj
         (filter #(mx/valid-coord? % dim))
         (map #(mx/get-val % m))
         (every? #(> % x)))))

(defn- find-low-points
  [m]
  (let [dim (mx/dimensions m)
        coords (mx/gen-coords dim)]
    (filter #(is-low-point? % dim m) coords)))

(defn- solve1
  [m]
  (->> m
       (find-low-points)
       (map #(mx/get-val % m))
       (map inc)
       (reduce +)))

(defn- basin-size
  [[row col :as coord] dim m prev visited]
  (if (and (mx/valid-coord? coord dim)
           (not (contains? visited coord)))
    (let [x (mx/get-val coord m)]
      (if (or (nil? prev) (> 9 x prev))
        (let [visited (conj visited coord)
              {top :size
               visited :visited}
              (basin-size [(inc row) col] dim m x visited)
              {bottom :size
               visited :visited}
              (basin-size [(dec row) col] dim m x visited)
              {left :size
               visited :visited}
              (basin-size [row (dec col)] dim m x visited)
              {right :size
               visited :visited}
              (basin-size [row (inc col)] dim m x visited)]
          {:size (+ 1 top bottom left right)
           :visited visited})
        {:size 0
         :visited visited}))
    {:size 0
     :visited visited}))

(defn- solve2
  [m]
  (->> m
       (find-low-points)
       (map #(basin-size % (mx/dimensions m) m nil #{}))
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