(ns advent-of-code-2021-clj.day02
  (:require [clojure.string :as str]))

(defn- parse-command
  [input]
  (->> input
       (#(str/split % #" "))
       ((fn [[c v]]
          {:command c
           :value (Integer/parseInt v)}))))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-command)))

(defn- update-coords
  [coords {cmd :command v :value}]
  (case cmd
    "forward" (update coords :h + v)
    "up"      (update coords :v - v)
    "down"    (update coords :v + v)
    coords))

(defn- update-coords-w-aim
  [{h :h v :v aim :aim :as coords}
   {cmd :command val :value}]
  (case cmd
    "forward" (assoc coords
                     :h (+ h val)
                     :v (+ v (* aim val)))
    "up"      (update coords :aim - val)
    "down"    (update coords :aim + val)
    coords))

(defn part1
  [input]
  (->> input
       (parse-input)
       (reduce update-coords {:h 0 :v 0})
       ((fn [{h :h v :v}]  (* h v)))))

(defn part2
  [input]
  (->> input
       (parse-input)
       (reduce update-coords-w-aim {:h 0 :v 0 :aim 0})
       ((fn [{h :h v :v}]  (* h v)))))
