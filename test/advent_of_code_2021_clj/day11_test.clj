(ns advent-of-code-2021-clj.day11_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day11 :as day11]))

(day11/part2 (core/read-input "day11-test"))


(deftest test-part1
  (is (= 1656 (day11/part1 (core/read-input "day11-test")))))

(deftest test-part2
  (is (= 195 (day11/part2 (core/read-input "day11-test")))))