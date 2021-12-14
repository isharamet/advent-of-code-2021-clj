(ns advent-of-code-2021-clj.day14_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day14 :as day14]))

;; (day14/part1 (core/read-input "day14"))

(deftest test-part1
  (is (= 17 (day14/part1 (core/read-input "day14-test")))))

;; (deftest test-part2
;;   (is (= 16 (day14/part2 (core/read-input "day14-test")))))