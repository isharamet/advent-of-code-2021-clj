(ns advent-of-code-2021-clj.day17_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.day17 :as day17]))

(def input "target area: x=20..30, y=-10..-5")

(deftest test-part1
  (is (= 45 (day17/part1 input))))

(deftest test-part2
  (is (= 112 (day17/part2 input))))
