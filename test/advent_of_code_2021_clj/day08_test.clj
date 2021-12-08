(ns advent-of-code-2021-clj.day08_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.core :as core]
            [advent-of-code-2021-clj.day08 :as day08]))

;; (day08/part1 (core/read-input "day08"))

(deftest test-part1
  (is (= 26 (day08/part1 (core/read-input "day08-test")))))

;; (deftest test-part2
;;   (is (= 168 (day08/part2 (core/read-input "day08-test")))))