(ns advent-of-code-2021-clj.day16
  (:require [clojure.string :as str]))

(def hex-to-bin
  {\0 [\0 \0 \0 \0]
   \1 [\0 \0 \0 \1]
   \2 [\0 \0 \1 \0]
   \3 [\0 \0 \1 \1]
   \4 [\0 \1 \0 \0]
   \5 [\0 \1 \0 \1]
   \6 [\0 \1 \1 \0]
   \7 [\0 \1 \1 \1]
   \8 [\1 \0 \0 \0]
   \9 [\1 \0 \0 \1]
   \A [\1 \0 \1 \0]
   \B [\1 \0 \1 \1]
   \C [\1 \1 \0 \0]
   \D [\1 \1 \0 \1]
   \E [\1 \1 \1 \0]
   \F [\1 \1 \1 \1]})

(defn- parse-input
  [input]
  (->> input
       (char-array)
       (seq)
       (map hex-to-bin)
       (flatten)))

(defn- parse-binary
  [b]
  (Long/parseLong (apply str b) 2))

(defn- packet-version
  [[input p]]
  (let [v (parse-binary (take 3 input))
        input (drop 3 input)]
    [input (assoc p :version v)]))

(defn- packet-id
  [[input p]]
  (let [v (parse-binary (take 3 input))
        input (drop 3 input)]
    [input (assoc p :id v)]))

(defn- literal-payload
  [[input p]]
  (loop [input input
         acc []]
    (let [g (take 5 input)
          c (first g)
          x (rest g)
          input (drop 5 input)
          acc (concat acc x)]
      (if (= c \0)
        [input (assoc p :val (parse-binary acc))]
        (recur input acc)))))

(def min-length 11)

(declare parse-packet)

(defn- parse-all
  [[input p]]
  (loop [input input
         acc []]
    (if (< (count input) min-length)
      [input (assoc p :val acc)]
      (let [[input p] (parse-packet input)]
        (recur input (conj acc p))))))

(defn- parse-n
  [[input p] n]
  (loop [n n
         input input
         acc []]
    (if (zero? n)
      [input (assoc p :val acc)]
      (let [[input p] (parse-packet input)
            n (dec n)
            acc (conj acc p)]
        (recur n input acc)))))

(defn- operator-payload
  [[input p]]
  (let [lt (first input)
        input (rest input)]
    (if (= lt \0)
      (let [l (parse-binary (take 15 input))
            input (drop 15 input)
            [_ p] (parse-all [(take l input) p])]
        [(drop l input) p])
      (let [n (parse-binary (take 11 input))
            input (drop 11 input)]
        (parse-n [input p] n)))))

(defn- packet-payload
  [[input p]]
  (if (= (p :id) 4)
    (literal-payload [input p])
    (operator-payload [input p])))

(defn- parse-packet
  [input]
  (->> [input {}]
       (packet-version)
       (packet-id)
       (packet-payload)))

(defn- sum-versions
  [p]
  (let [id (p :id)
        v (p :version)]
    (if (= id 4)
      v
      (+ v (reduce + (map sum-versions (p :val)))))))

(defn part1
  [input]
  (->> input
       (parse-input)
       (parse-packet)
       (#(nth % 1))
       (sum-versions)))

;; (defn part2
;;   [input]
;;   (parse-input input))
