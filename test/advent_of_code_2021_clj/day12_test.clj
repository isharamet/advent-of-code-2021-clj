(ns advent-of-code-2021-clj.day12_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day12 :as day12]))

(deftest test-part1
  (is (= 226 (day12/part1 (core/read-input "day12-test")))))

(deftest test-part2
  (is (= 3509 (day12/part2 (core/read-input "day12-test")))))