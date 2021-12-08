(ns advent-of-code-2021-clj.day08
  (:require [clojure.string :as str]
            [clojure.set :as cset]))

(def digits-mapping
  {1 (fn [x _]
       (= 2 (count x)))
   2 (fn [x m]
       (and (= 5 (count x))
            (not (cset/subset? x (m 9)))))
   3 (fn [x m]
       (and (= 5 (count x))
            (cset/subset? (m 7) x)))
   4 (fn [x _]
       (= 4 (count x)))
   5 (fn [x m]
       (and (= 5 (count x))
            (cset/subset? x (m 6))))
   6 (fn [x m]
       (and (= 6 (count x))
            (not (cset/subset? (m 7) x))))
   7 (fn [x _]
       (= 3 (count x)))
   8 (fn [x _]
       (= 7 (count x)))
   9 (fn [x m]
       (and (= 6 (count x))
            (cset/subset? (m 4) x)))
   0 (fn [x m]
       (and (= 6 (count x))
            (and (cset/subset? (m 7) x)
                 (not (cset/subset? (m 4) x)))))})

(def mapping-order
  [1 4 7 8 6 9 0 2 3 5])

(defn- find-signal
  [signals i raw-signals]
  (let [f (digits-mapping i)
        s (first (filter #(f % signals) raw-signals))]
    (assoc signals i s)))

(defn- parse-line
  [line]
  (->> line
       (#(str/split % #" \| "))
       (map #(str/split % #" "))
       (map #(map to-array %))
       (map #(map set %))))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)))

(def unique-lengths
  #{2 3 4 7})

(defn- solve1
  [signals]
  (->> signals
       (map (fn [[_ xs]] xs))
       (map (fn [xs]
              (filter #(contains? unique-lengths (count %)) xs)))
       (map count)
       (reduce +)))

(defn- map-output
  [[signals output]]
  (->> mapping-order
       (reduce #(find-signal %1 %2 signals) {})
       (cset/map-invert)
       (#(map % output))))

(defn- build-number
  [coll]
  (loop [coll coll
         acc 0]
    (if (empty? coll)
      acc
      (recur (rest coll)
             (+ (* acc 10) (first coll))))))

(defn- solve2
  [input]
  (->> input
       (map map-output)
       (map build-number)
       (reduce +)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (solve1)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (solve2)))