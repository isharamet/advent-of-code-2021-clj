(ns advent-of-code-2021-clj.day21_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day21 :as day21]))

(def test-input
  (core/read-input "day21-test"))

(deftest test-part1
  (is (= 739785 (day21/part1 test-input))))

(deftest test-part2
  (is (= 444356092776315N (day21/part2 test-input))))
