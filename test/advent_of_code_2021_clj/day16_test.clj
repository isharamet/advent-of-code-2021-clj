(ns advent-of-code-2021-clj.day16_test
  (:require [clojure.test :refer [deftest is]]
            [advent-of-code-2021-clj.day16 :as day16]))

(deftest test-part1-1
  (is (= 16 (day16/part1 "8A004A801A8002F478"))))

(deftest test-part1-2
  (is (= 12 (day16/part1 "620080001611562C8802118E34"))))

(deftest test-part1-3
  (is (= 23 (day16/part1 "C0015000016115A2E0802F182340"))))

(deftest test-part1-4
  (is (= 31 (day16/part1 "A0016C880162017C3686B18A3D4780"))))

(deftest test-part2-1
  (is (= 3 (day16/part2 "C200B40A82"))))

(deftest test-part2-2
  (is (= 54 (day16/part2 "04005AC33890"))))

(deftest test-part2-3
  (is (= 7 (day16/part2 "880086C3E88112"))))

(deftest test-part2-4
  (is (= 9 (day16/part2 "CE00C43D881120"))))

(deftest test-part2-5
  (is (= 1 (day16/part2 "D8005AC2A8F0"))))

(deftest test-part2-6
  (is (= 0 (day16/part2 "F600BC2D8F"))))

(deftest test-part2-7
  (is (= 0 (day16/part2 "9C005AC2F8F0"))))

(deftest test-part2-8
  (is (= 1 (day16/part2 "9C0141080250320F1802104A08"))))