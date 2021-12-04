(ns advent-of-code-2021-clj.day04
  (:require [clojure.string :as str]))

(def mark "X")

(def board-size 5)

(defn- parse-draws
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
  [[draws & boards]]
  (let [draws (parse-draws draws)
        boards (map parse-board boards)]
    {:draws draws
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

(defn- first-winner
  [{draws :draws
    boards :boards}]
  (loop [draws draws
         boards boards]
    (if (empty? draws)
      nil
      (let [draw (first draws)
            boards (map #(mark-draw % draw) boards)
            winner (first (filter bingo? boards))]
        (if (some? winner)
          {:winning-board winner
           :last-draw draw}
          (recur
           (rest draws)
           boards))))))

(defn- last-winner
  [{draws :draws
    boards :boards}]
  (loop [draws draws
         boards boards
         res nil]
    (if (empty? draws)
      res
      (let [draw (first draws)
            boards (map #(mark-draw % draw) boards)
            winners (filter bingo? boards)
            boards (remove bingo? boards)]
        (recur
         (rest draws)
         boards
         (if (empty? winners)
           res
           {:winning-board (last winners)
            :last-draw draw}))))))

(defn- board-sum
  [board]
  (->> board
       (filter #(not (= % mark)))
       (map #(Integer/parseInt %))
       (reduce +)))

(defn compute-score
  [{winning-board :winning-board
    last-draw :last-draw}]
  (* (board-sum winning-board)
     (Integer/parseInt last-draw)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (first-winner)
       (compute-score)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (last-winner)
       (compute-score)))