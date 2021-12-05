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
  [[[_ y1] [_ y2]]]
  (= y1 y2))

(defn- vertical?
  [[[x1 _] [x2 _]]]
  (= x1 x2))

(defn- inc-nth
  [coll n]
  (let [x (inc (nth coll n))]
    (assoc coll n x)))

(defn- draw-h-line
  [[[x1 y] [x2 _]] field]
  (->> (range (min x1 x2) (inc (max x1 x2)))
       (reduce #(inc-nth %1 (+ (* y field-size) %2)) field)))

(defn- draw-v-line
  [[[x y1] [_ y2]] field]
  (->> (range (min y1 y2) (inc (max y1 y2)))
       (reduce #(inc-nth %1 (+ (* %2 field-size) x)) field)))

(defn- line-points
  [[[x1 y1] [x2 y2]]]
  (if (> x1 x2)
    (let [xs (reverse (range x2 (inc x1)))]
      (if (> y1 y2)
        (map vector xs (reverse (range y2 (inc y1))))
        (map vector xs (range y1 (inc y2)))))
    (let [xs (range x1 (inc x2))]
      (if (> y1 y2)
        (map vector xs (reverse (range y2 (inc y1))))
        (map vector xs (range y1 (inc y2)))))))

(defn- draw-d-line
  [line field]
  (->> line
       (line-points)
       (map (fn [[x y]] (+ (* y field-size) x)))
       (reduce #(inc-nth %1 %2) field)))

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

(defn- draw-line-w-diagonal
  [line field]
  (if (horizontal? line)
    (draw-h-line line field)
    (if (vertical? line)
      (draw-v-line line field)
      (draw-d-line line field))))

(defn- draw-lines-w-diagonal
  [lines field]
  (reduce #(draw-line-w-diagonal %2 %1) field lines))

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
       (parse-input)
       (#(draw-lines-w-diagonal % empty-field))
       (overlapping-points)))