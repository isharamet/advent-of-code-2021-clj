(ns advent-of-code-2021-clj.day19_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day19 :as day19]))

(def test-input
  (core/read-input "day19-test"))

(deftest test-part1
  (is (= 79 (day19/part1 test-input))))

(deftest test-part2
  (is (= 3621 (day19/part2 test-input))))
