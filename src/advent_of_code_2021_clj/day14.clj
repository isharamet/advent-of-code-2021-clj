(ns advent-of-code-2021-clj.day14
  (:require [clojure.string :as str]))

(defn- parse-template
  [input]
  input)

(defn- parse-insertion
  [input]
  (->> input
       (#(str/split % #" -> "))
       ((fn [[[a b] [c]]] [[a b] c]))))

(defn- parse-insertions
  [input]
  (->> input
       (str/split-lines)
       (map parse-insertion)
       (into {})))

(defn- parse-input
  [input]
  (let [[template insertions] (str/split input #"\n\n")
        template (parse-template template)
        insertions (parse-insertions insertions)]
    {:template template
     :insertions insertions}))


(defn- result
  [m]
  (let [xs (vals m)
        counts (map count xs)
        a (apply max counts)
        b (apply min counts)]
    (- a b)))

(defn- solve1
  [{template :template insertions :insertions} n]
  (->> template
       (#(reduce (fn [s _]
                   (loop [acc '()
                          tail (reverse s)]
                     (if (empty? tail)
                       acc
                       (let [b (first tail)
                             tail (rest tail)
                             a (first tail)
                             acc (cons b acc)]
                         (if (nil? a)
                           acc
                           (let [c (insertions [a b])]
                             (if (nil? c)
                               (recur acc tail)
                               (recur (cons c acc) tail))))))))
                 %
                 (range n)))
       (group-by identity)
       (result)))

(defn- solve2
  [{template :template insertions :insertions}]
  [template insertions])

(defn part1
  [input]
  (solve1 (parse-input input) 10))

(defn part2
  [input]
  (solve2 (parse-input input)))
