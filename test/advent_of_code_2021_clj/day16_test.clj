(ns advent-of-code-2021-clj.day16_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.day16 :as day16]))

;; (day16/part1 (core/read-input "day16"))

(deftest test-part1-1
  (is (= 16 (day16/part1 "8A004A801A8002F478"))))

(deftest test-part1-2
  (is (= 12 (day16/part1 "620080001611562C8802118E34"))))

(deftest test-part1-3
  (is (= 23 (day16/part1 "C0015000016115A2E0802F182340"))))

(deftest test-part1-4
  (is (= 31 (day16/part1 "A0016C880162017C3686B18A3D4780"))))

;; (deftest test-part2
;;   (is (= 315 (day16/part2 (core/read-input "day16-test")))))