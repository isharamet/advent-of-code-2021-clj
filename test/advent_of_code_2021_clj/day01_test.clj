(ns advent-of-code-2021-clj.day01_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day01 :as day01]))

(deftest test-part1
  (is (= 7 (day01/part1 (core/read-input "day01-test")))))

(deftest test-part2
  (is (= 5 (day01/part2 (core/read-input "day01-test")))))