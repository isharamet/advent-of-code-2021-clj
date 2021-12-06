(ns advent-of-code-2021-clj.day06_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day06 :as day06]))

;; (day06/part1 (core/read-input "day06"))

(deftest test-part1
  (is (= 5934 (day06/part1 (core/read-input "day06-test")))))

;; (deftest test-part2
;;   (is (= 12 (day06/part2 (core/read-input "day06-test")))))