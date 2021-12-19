(ns advent-of-code-2021-clj.day19
  (:require [clojure.string :as str]))

(defn- parse-line
  [line]
  (->> line
       (#(str/split % #","))
       (map #(Integer/parseInt %))
       (into [])))

(defn- parse-readings
  [input]
  (->> input
       (str/split-lines)
       (rest)
       (map parse-line)))

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #"\n\n"))
       (map parse-readings)))

(def translation-functions
  [(fn [[x y z]]  [x y z])
   (fn [[x y z]]  [x (- z) y])
   (fn [[x y z]]  [x (- y) (- z)])
   (fn [[x y z]]  [x z (- y)])

   (fn [[x y z]]  [(- x) (- y) z])
   (fn [[x y z]]  [(- x) z y])
   (fn [[x y z]]  [(- x) y (- z)])
   (fn [[x y z]]  [(- x) (- z) (- y)])

   (fn [[x y z]]  [y z x])
   (fn [[x y z]]  [y (- x) z])
   (fn [[x y z]]  [y (- z) (- x)])
   (fn [[x y z]]  [y x (- z)])

   (fn [[x y z]]  [(- y) (- z) x])
   (fn [[x y z]]  [(- y) x z])
   (fn [[x y z]]  [(- y) z (- x)])
   (fn [[x y z]]  [(- y) (- x) (- z)])

   (fn [[x y z]]  [z x y])
   (fn [[x y z]]  [z (- y) x])
   (fn [[x y z]]  [z (- x) (- y)])
   (fn [[x y z]]  [z y (- x)])

   (fn [[x y z]]  [(- z) (- x) y])
   (fn [[x y z]]  [(- z) y x])
   (fn [[x y z]]  [(- z) x (- y)])
   (fn [[x y z]]  [(- z) (- y) (- x)])])

(defn- distance
  [[x1 y1 z1] [x2 y2 z2]]
  [(- x1 x2) (- y1 y2) (- z1 z2)])

(defn- add-coords
  [[x1 y1 z1] [x2 y2 z2]]
  [(+ x1 x2) (+ y1 y2) (+ z1 z2)])

(defn- distances
  [a b]
  (reduce
   (fn [acc pa]
     (reduce
      (fn [acc pb]
        (conj acc (distance pa pb)))
      acc
      b))
   '()
   a))

(defn- find-offset
  [a b]
  (->> (distances a b)
       (group-by identity)
       (map (fn [[o coll]] [o (count coll)]))
       (sort-by (fn [[_ c]] (- c)))
       (first)
       ((fn [[o c]] (if (>= c 12) o nil)))))

(defn- find-offset-rotated
  [a b]
  (->> translation-functions
       (map (fn [f] (map f b)))
       (map (fn [x] [(find-offset a x) x]))
       (filter (fn [[o _]] (not (nil? o))))
       (first)))

(defn- normalize-one
  [normalized non-normalized]
  (reduce
   (fn [[normalized non-normalized] x]
     (->> normalized
          (map (fn [{beacons :beacons}]
                 (find-offset-rotated beacons x)))
          (filter (fn [[_ [offset _]]] (not (nil? offset))))
          (first)
          ((fn [res]
             (if (nil? res)
               [normalized (conj non-normalized x)]
               (let [[offset beacons] res
                     normalized (conj normalized
                                      {:coord (add-coords [0 0 0] offset)
                                       :beacons (map #(add-coords % offset) beacons)})]
                 [normalized non-normalized]))))))
   [normalized '()]
   non-normalized))

(defn- normalize
  [readings]
  (loop [normalized (list {:coord [0 0 0]
                           :beacons (first readings)})
         non-normlized (rest readings)]
    (if (empty? non-normlized)
      normalized
      (let [[normalized non-normlized] (normalize-one normalized non-normlized)]
        (recur normalized non-normlized)))))

(defn- manhattan-distance
  [p1 p2]
  (->> (map vector p1 p2)
       (map #(apply - %))
       (map #(Math/abs %))
       (reduce +)))

(defn- manhattan-distances
  [ps]
  (reduce
   (fn [acc p1]
     (reduce
      (fn [acc p2]
        (conj acc (manhattan-distance p1 p2)))
      acc
      ps))
   '()
   ps))

(defn part1
  [input]
  (->> input
       (parse-input)
       (normalize)
       (map :beacons)
       (apply concat)
       (into #{})
       (count)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (normalize)
       (map :coord)
       (manhattan-distances)
       (apply max)))
