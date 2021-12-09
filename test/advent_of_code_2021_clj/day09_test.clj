(ns advent-of-code-2021-clj.day09_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day09 :as day09]))

;; (day09/part1 (core/read-input "day09"))

(deftest test-part1
  (is (= 15 (day09/part1 (core/read-input "day09-test")))))

;; (deftest test-part2
;;   (is (= 61229 (day09/part2 (core/read-input "day09-test")))))