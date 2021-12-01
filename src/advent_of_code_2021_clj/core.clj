(ns advent-of-code-2021-clj.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [advent-of-code-2021-clj.day01 :as day01]))


(defn read-input
  [day]
  (slurp (io/resource (str day ".txt"))))

(defn -main
  [day]
  (case day
    "day01" (println
             (day01/part1 (read-input "day01"))
             (day01/part2 (read-input "day01")))))

