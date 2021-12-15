(ns advent-of-code-2021-clj.day15_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day15 :as day15]))

(deftest test-part1
  (is (= 40 (day15/part1 (core/read-input "day15-test")))))

(deftest test-part2
  (is (= 315 (day15/part2 (core/read-input "day15-test")))))