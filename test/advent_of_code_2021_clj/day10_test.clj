(ns advent-of-code-2021-clj.day10_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day10 :as day10]))

(deftest test-part1
  (is (= 26397 (day10/part1 (core/read-input "day10-test")))))

(deftest test-part2
  (is (= 288957 (day10/part2 (core/read-input "day10-test")))))