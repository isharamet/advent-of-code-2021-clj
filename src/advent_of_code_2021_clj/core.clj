(ns advent-of-code-2021-clj.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [advent-of-code-2021-clj.day01 :as day01]
            [advent-of-code-2021-clj.day02 :as day02]
            [advent-of-code-2021-clj.day03 :as day03]
            [advent-of-code-2021-clj.day04 :as day04]
            [advent-of-code-2021-clj.day05 :as day05]
            [advent-of-code-2021-clj.day06 :as day06]
            [advent-of-code-2021-clj.day07 :as day07]
            [advent-of-code-2021-clj.day08 :as day08]))

(defn read-input
  [day]
  (slurp (io/resource (str day ".txt"))))

(defn -main
  [day]
  (case day
    "day01" (println
             (day01/part1 (read-input "day01"))
             (day01/part2 (read-input "day01")))
    "day02" (println
             (day02/part1 (read-input "day02"))
             (day02/part2 (read-input "day02")))
    "day03" (println
             (day03/part1 (read-input "day03"))
             (day03/part2 (read-input "day03")))
    "day04" (println
             (day04/part1 (read-input "day04"))
             (day04/part2 (read-input "day04")))
    "day05" (println
             (day05/part1 (read-input "day05"))
             (day05/part2 (read-input "day05")))
    "day06" (println
             (day06/part1 (read-input "day06"))
             (day06/part2 (read-input "day06")))
    "day07" (println
             (day07/part1 (read-input "day07"))
             (day07/part2 (read-input "day07")))
    "day08" (println
             (day08/part1 (read-input "day08"))
             (day08/part2 (read-input "day08")))
    (println (str "No solution for " day " yet..."))))
