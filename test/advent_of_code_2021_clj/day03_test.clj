(ns advent-of-code-2021-clj.day03_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day03 :as day03]))

(deftest test-part1
  (is (= 198 (day03/part1 (core/read-input "day03-test")))))

(deftest test-part2
  (is (= 230 (day03/part2 (core/read-input "day03-test")))))