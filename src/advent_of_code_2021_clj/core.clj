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
            [advent-of-code-2021-clj.day08 :as day08]
            [advent-of-code-2021-clj.day09 :as day09]
            [advent-of-code-2021-clj.day10 :as day10]
            [advent-of-code-2021-clj.day11 :as day11]
            [advent-of-code-2021-clj.day12 :as day12]
            [advent-of-code-2021-clj.day13 :as day13]
            [advent-of-code-2021-clj.day14 :as day14]
            [advent-of-code-2021-clj.day15 :as day15]
            [advent-of-code-2021-clj.day16 :as day16]
            [advent-of-code-2021-clj.day17 :as day17]
            [advent-of-code-2021-clj.day18 :as day18]
            [advent-of-code-2021-clj.day19 :as day19]
            [advent-of-code-2021-clj.day20 :as day20]
            [advent-of-code-2021-clj.day21 :as day21]))

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
    "day09" (println
             (day09/part1 (read-input "day09"))
             (day09/part2 (read-input "day09")))
    "day10" (println
             (day10/part1 (read-input "day10"))
             (day10/part2 (read-input "day10")))
    "day11" (println
             (day11/part1 (read-input "day11"))
             (day11/part2 (read-input "day11")))
    "day12" (println
             (day12/part1 (read-input "day12"))
             (day12/part2 (read-input "day12")))
    "day13" (println
             (day13/part1 (read-input "day13"))
             (day13/part2 (read-input "day13")))
    "day14" (println
             (day14/part1 (read-input "day14"))
             (day14/part2 (read-input "day14")))
    "day15" (println
             (day15/part1 (read-input "day15"))
             (day15/part2 (read-input "day15")))
    "day16" (println
             (day16/part1 (read-input "day16"))
             (day16/part2 (read-input "day16")))
    "day17" (println
             (day17/part1 (read-input "day17"))
             (day17/part2 (read-input "day17")))
    "day18" (println
             (day18/part1 (read-input "day18"))
             (day18/part2 (read-input "day18")))
    "day19" (println
             (day19/part1 (read-input "day19"))
             (day19/part2 (read-input "day19")))
    "day20" (println
             (day20/part1 (read-input "day20"))
             (day20/part2 (read-input "day20")))
    "day21" (println
             (day21/part1 (read-input "day21"))
             (day21/part2 (read-input "day21")))
    (println (str "No solution for " day " yet..."))))
