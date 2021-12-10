(ns advent-of-code-2021-clj.day10
  (:require [clojure.string :as str]))

(def opening-to-closing
  {\( \)
   \[ \]
   \{ \}
   \< \>})

(def closing-brackets
  (set (vals opening-to-closing)))

(defn- closing?
  [bracket]
  (contains? closing-brackets bracket))

(def points1
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

(defn- find-corrupted
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

(defn- find-incomplete
  [line]
  (loop [line line
         open '()]
    (if (empty? line)
      open
      (let [x (first line)
            line (rest line)]
        (if (closing? x)
          (if (= x (opening-to-closing (first open)))
            (recur line (rest open))
            '())
          (recur line (conj open x)))))))

(def points2
  {\) 1
   \] 2
   \} 3
   \> 4})

(defn- score2
  [line]
  (reduce (fn [acc x] (+ (* acc 5) (points2 x))) 0 line))

(defn- solve1
  [lines]
  (->> lines
       (map find-corrupted)
       (filter :corrupted)
       (map :last)
       (map points1)
       (reduce +)))

(defn- solve2
  [lines]
  (->> lines
       (map find-incomplete)
       (filter #(not (empty? %)))
       (map #(map opening-to-closing %))
       (map score2)
       (sort)
       (#(nth % (/ (count %) 2)))))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (solve2 (parse-input input)))