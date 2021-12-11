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
  [[row col] dim coll]
  (let [x (mx/get-val [row col] coll)
        adj (mx/gen-neighbours [row col] dim)]
    (->> adj
         (filter #(mx/valid-coord? % dim))
         (map #(mx/get-val % coll))
         (every? #(> % x)))))

(defn- find-low-points
  [coll]
  (let [dim (mx/dimensions coll)
        coords (mx/gen-coords dim)]
    (filter #(is-low-point? % dim coll) coords)))

(defn- solve1
  [coll]
  (->> coll
       (find-low-points)
       (map #(mx/get-val % coll))
       (map inc)
       (reduce +)))

(defn- basin-size
  [[row col :as coord] dim coll prev visited]
  (if (and (mx/valid-coord? coord dim)
           (not (contains? visited coord)))
    (let [x (mx/get-val coord coll)]
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
       (map #(basin-size % (mx/dimensions coll) coll nil #{}))
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