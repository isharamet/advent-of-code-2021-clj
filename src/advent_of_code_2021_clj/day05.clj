(ns advent-of-code-2021-clj.day05
  (:require [clojure.string :as str]))

(def field-size 1000)

(def empty-field
  (vec (repeat (* field-size field-size) 0)))

(defn- parse-coord
  [input]
  (->> input
       (#(str/split % #","))
       (map #(Integer/parseInt %))))

(defn- parse-line
  [input]
  (->> input
       (#(str/split % #" -> "))
       (#(map parse-coord %))))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)))

(defn- horizontal?
  [[[x1 _] [x2 _]]]
  (= x1 x2))

(defn- vertical?
  [[[_ y1] [_ y2]]]
  (= y1 y2))

(defn- inc-nth
  [coll n]
  (let [x (inc (nth coll n))]
    (assoc coll n x)))

(defn- draw-h-line
  [[[x y1] [_ y2]] field]
  (->> (range (min y1 y2) (inc (max y1 y2)))
       (reduce #(inc-nth %1 (+ (* x field-size) %2)) field)))

(defn- draw-v-line
  [[[x1 y] [x2 _]] field]
  (->> (range (min x1 x2) (inc (max x1 x2)))
       (reduce #(inc-nth %1 (+ (* %2 field-size) y)) field)))

(defn- draw-line
  [line field]
  (if (horizontal? line)
    (draw-h-line line field)
    (if (vertical? line)
      (draw-v-line line field)
      field)))

(defn- draw-lines
  [lines field]
  (reduce #(draw-line %2 %1) field lines))


(defn- overlapping-points
  [field]
  (->> field
       (filter #(> % 1))
       (count)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (#(draw-lines % empty-field))
       (overlapping-points)))

(defn part2
  [input]
  (->> input
       (parse-input)))