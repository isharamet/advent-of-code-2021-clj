(ns advent-of-code-2021-clj.day07_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day07 :as day07]))

(deftest test-part1
  (is (= 37 (day07/part1 (core/read-input "day07-test")))))

(deftest test-part2
  (is (= 168 (day07/part2 (core/read-input "day07-test")))))