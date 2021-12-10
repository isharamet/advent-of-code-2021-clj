(ns advent-of-code-2021-clj.day10
  (:require [clojure.string :as str]
            [clojure.set :as cset]))

(def opening-to-closing
  {\( \)
   \[ \]
   \{ \}
   \< \>})

(def closing-to-opening
  (cset/map-invert opening-to-closing))

(def opening-brackets
  (set (keys opening-to-closing)))

(def closing-brackets
  (set (vals opening-to-closing)))

(defn- opening?
  [bracket]
  (contains? opening-brackets bracket))

(defn- closing?
  [bracket]
  (contains? closing-brackets bracket))

(def points
  {\) 3
   \] 57
   \} 1197
   \> 25137})

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map char-array)
       (map seq)))

(defn- corrupted?
  [line]
  (loop [line line
         open '()]
    (if (empty? line)
      {:corrupted false}
      (let [x (first line)
            line (rest line)]
        (if (closing? x)
          (if (= x (opening-to-closing (first open)))
            (recur line (rest open))
            {:corrupted true
             :last x})
          (recur line (conj open x)))))))

(defn- solve1
  [lines]
  (->> lines
       (map corrupted?)
       (filter :corrupted)
       (map :last)
       (map points)
       (reduce +)))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (parse-input input))