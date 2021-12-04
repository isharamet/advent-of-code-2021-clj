(ns advent-of-code-2021-clj.day04_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day04 :as day04]))

(day04/part1 (core/read-input "day04"))

(deftest test-part1
  (is (= 4512 (day04/part1 (core/read-input "day04-test")))))

(deftest test-part2
  (is (= 4512 (day04/part1 (core/read-input "day04-test")))))