(ns advent-of-code-2021-clj.day13_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day13 :as day13]))

(deftest test-part1
  (is (= 17 (day13/part1 (core/read-input "day13-test")))))

(deftest test-part2
  (is (= 16 (day13/part2 (core/read-input "day13-test")))))