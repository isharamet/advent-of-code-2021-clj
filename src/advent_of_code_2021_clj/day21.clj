(ns advent-of-code-2021-clj.day21
  (:require [clojure.string :as str]))

(defn- parse-position
  [input]
  (let [matcher (re-matcher
                 #"Player (\d+) starting position: (\d+)"
                 input)
        [_ _ pos] (re-find matcher)]
    (Integer/parseInt pos)))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-position)
       (into [])))

(def dice
  (iterate (fn [x] (if (< x 100) (inc x) 1)) 1))

(defn- gen-game
  [pos1 pos2]
  (iterate
   (fn [{scores :scores
         turn :turn
         dice :dice}]
     (let [[pos score] (nth scores turn)
           roll (apply + (take 3 dice))
           m (mod (+ pos roll) 10)
           pos (if (= 0 m) 10 m)
           score (+ score pos)]
       {:scores (assoc scores turn [pos score])
        :turn (if (= turn 1) 0 1)
        :dice (drop 3 dice)}))
   {:scores [[pos1 0] [pos2 0]]
    :turn 0
    :dice dice}))

(defn- play
  [[pos1 pos2]]
  (loop [game (drop 2 (gen-game pos1 pos2))
         turns 1
         prev-score 0]
    (let [{scores :scores turn :turn} (first game)
          [_ score] (nth scores turn)]
      (if (>= score 1000)
        (do (println prev-score turns)
            (* prev-score (* turns 3)))
        (recur (rest game) (inc turns) score)))))

(defn part1
  [input]
  (play (parse-input input)))

;; (defn part2
;;   [input]
;;   (solve (parse-input input) 50))
