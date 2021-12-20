(ns advent-of-code-2021-clj.day20_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day20 :as day20]))

(def test-input
  (core/read-input "day20-test"))

(deftest test-part1
  (is (= 35 (day20/part1 test-input))))

(deftest test-part2
  (is (= 3351 (day20/part2 test-input))))
