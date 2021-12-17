(ns advent-of-code-2021-clj.day17)

(def input-re
  #"target area: x=(\d+)\.\.(\d+), y=(-*\d+)\.\.(-*\d+)")

(defn- parse-input
  [input]
  (let [matcher (re-matcher input-re input)
        [_ x1 x2 y1 y2] (re-find matcher)]
    (map #(Integer/parseInt %) [x1 x2 y1 y2])))

(defn- solve1
  [[x1 x2 y1 y2]]
  (let [y (Math/abs y1)]
    (/ (* y (dec y)) 2)))


(defn part1
  [input]
  (solve1 (parse-input input)))

;; (defn part2
;;   [input]
;;   input)
