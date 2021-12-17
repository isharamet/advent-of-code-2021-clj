(ns advent-of-code-2021-clj.day17_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.day17 :as day17]))

(deftest test-part1
  (is (= 190 (day17/part1 "target area: x=10..20, y=-20..-10"))))

