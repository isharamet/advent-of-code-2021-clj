(ns advent-of-code-2021-clj.day05_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day05 :as day05]))

(deftest test-part1
  (is (= 5 (day05/part1 (core/read-input "day05-test")))))

(deftest test-part2
  (is (= 12 (day05/part2 (core/read-input "day05-test")))))