(ns advent-of-code-2021-clj.day02_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day02 :as day02]))

(deftest test-part1
  (is (= 1488669 (day02/part1 (core/read-input "day02")))))

(deftest test-part2
  (is (= 1176514794 (day02/part2 (core/read-input "day02")))))