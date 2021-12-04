(ns advent-of-code-2021-clj.day04
  (:require [clojure.string :as str]))

(def mark "X")

(def board-size 5)

(defn- parse-draw
  [input]
  (str/split input #","))

(defn- parse-board
  [input]
  (->> input
       (str/split-lines)
       (map str/trim)
       (map #(str/split % #"\s+"))
       (apply concat)
       (into [])))

(defn- parse-bingo
  [[draw & boards]]
  (let [draw (parse-draw draw)
        boards (map parse-board boards)]
    {:draw draw
     :boards boards}))

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #"\n\n"))
       (parse-bingo)))

(defn- mark-draw
  [board draw]
  (map (fn [x] (if (= x draw) mark x)) board))

(defn- is-complete?
  [coll]
  (->> coll
       (map (fn [x] (every? #(= % mark) x)))
       (some true?)
       some?))

(defn- has-complete-rows?
  [board]
  (->> board
       (partition board-size board-size)
       is-complete?))

(defn- has-complete-cols?
  [board]
  (->> (range board-size)
       (map #(take-nth board-size (drop % board)))
       is-complete?))

(defn- bingo?
  [board]
  (or (has-complete-rows? board)
      (has-complete-cols? board)))

(some bingo?
      ["X" "X" "X" "X" "X"
       "1" "X" "3" "4" "5"
       "1" "X" "3" "4" "5"
       "1" "2" "3" "4" "5"
       "1" "X" "3" "4" "5"])

(defn- find-winner
  [{draw :draw
    boards :boards}]
  (loop [draw draw
         boards boards]
    (if (empty? draw)
      nil
      (let [d (first draw)
            boards (map #(mark-draw % d) boards)
            winner (first (filter bingo? boards))]
        (if (some? winner)
          {:winning-board winner
           :last-draw d}
          (recur
           (rest draw)
           boards))))))

(defn- board-sum
  [board]
  (->> board
       (filter #(not (= % mark)))
       (map #(Integer/parseInt %))
       (reduce +)))

(board-sum
 ["X" "X" "X" "X" "X"
  "1" "X" "3" "4" "5"
  "1" "X" "3" "4" "5"
  "1" "2" "3" "4" "5"
  "1" "X" "3" "4" "5"])

(defn part1
  [input]
  (->> input
       (parse-input)
       (find-winner)))

(defn part2
  [input]
  (->> input
       (parse-input)))