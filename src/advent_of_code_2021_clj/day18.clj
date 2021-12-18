(ns advent-of-code-2021-clj.day18
  (:require [clojure.string :as str]))

(def zero (int \0))

(defn- parse-num
  [c]
  (- (int c) zero))

(defn- parse-pair
  [cs]
  (loop [acc '()
         cs cs]
    (if (empty? cs)
      (first acc)
      (let [c (first cs)
            cs (rest cs)]
        (case c
          \[ (recur (conj acc []) cs)
          \] (let [inner (first acc)
                   acc (rest acc)
                   outer (first acc)
                   acc (rest acc)]
               (if (nil? outer)
                 inner
                 (recur (conj acc (conj outer inner)) cs)))
          \, (recur acc cs)
          (let [inner (first acc)
                acc (rest acc)
                n (parse-num c)]
            (recur (conj acc (conj inner n)) cs)))))))

(defn- parse-line
  [line]
  (->> line
       (char-array)
       (seq)
       (parse-pair)))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)
       (into [])))

(defn- add-left
  [x a]
  (if (vector? x)
    (let [[l r] x]
      [(add-left l a) r])
    (+ x a)))

(defn- add-right
  [x a]
  (if (vector? x)
    (let [[l r] x]
      [l (add-right r a)])
    (+ x a)))

(defn- explode
  ([x] (explode x 0))
  ([x depth]
   (if (vector? x)
     (let [[l r] x]
       (if (= depth 3)
         (if (vector? l)
           (let [[lv rv] l]
             [true [0 (add-left r rv)] [lv nil]])
           (if (vector? r)
             (let [[lv rv] r]
               [true [(add-right l lv) 0] [nil rv]])
             [false [l r] [nil nil]]))
         (let [[res p [lv rv]] (explode l (inc depth))]
           (if res
             (if (not (nil? lv))
               [true [p r] [lv nil]]
               (if (not (nil? rv))
                 [true [p (add-left r rv)] [nil nil]]
                 [true [p r] [nil nil]]))
             (let [[res p [lv rv]] (explode r (inc depth))]
               (if res
                 (if (not (nil? lv))
                   [true [(add-right l lv) p] [nil nil]]
                   (if (not (nil? rv))
                     [true [l p] [nil rv]]
                     [true [l p] [nil nil]]))
                 [false [l r] [nil nil]]))))))
     [false x [nil nil]])))

(defn- split
  [x]
  (if (vector? x)
    (let [[l r] x]
      (let [[res p] (split l)]
        (if res
          [true [p r]]
          (let [[res p] (split r)]
            (if res
              [true [l p]]
              [false x])))))
    (if (> x 9)
      (let [h (/ x 2)
            l (int (Math/floor h))
            r (int (Math/ceil h))]
        [true [l r]]))))

(defn- reduce-pair
  [p]
  (let [[r p] (explode p)
        [r p] (if r [r p] (split p))]
    (if r (reduce-pair p) p)))

(defn- add-numbers
  [a b]
  (reduce-pair [a b]))

(defn- add-all
  [xs]
  (reduce add-numbers xs))

(defn- magnitude
  [x]
  (if (vector? x)
    (let [[l r] x
          l (* 3 (magnitude l))
          r (* 2 (magnitude r))]
      (+ l r))
    x))

(defn- cross-magnitudes
  [xs]
  (let [n (count xs)
        r (range n)]
    (reduce
     (fn [acc i]
       (reduce
        (fn [acc j]
          (if (= i j)
            acc
            (->> [(nth xs i) (nth xs j)]
                 ((fn [[l r]] (add-numbers l r)))
                 (reduce-pair)
                 (magnitude)
                 (conj acc))))
        acc
        r))
     '()
     r)))

(defn part1
  [input]
  (->> input
       (parse-input)
       (add-all)
       (magnitude)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (cross-magnitudes)
       (apply max)))
