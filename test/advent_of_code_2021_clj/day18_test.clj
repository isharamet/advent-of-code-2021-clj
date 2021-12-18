(ns advent-of-code-2021-clj.day18_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day18 :as day18]))

(def test-input
  (core/read-input "day18-test"))

(deftest test-part1
  (is (= 4140 (day18/part1 test-input))))

(deftest test-part2
  (is (= 3993 (day18/part2 test-input))))
