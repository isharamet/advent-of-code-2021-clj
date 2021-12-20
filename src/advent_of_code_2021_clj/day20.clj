(ns advent-of-code-2021-clj.day20
  (:require [clojure.string :as str]
            [advent-of-code-2021-clj.matrix :as mx]))

(defn- parse-algorithm
  [alg]
  (->> alg
       (str/split-lines)
       (str/join)
       (map #(if (= % \.) 0 1))
       (into [])))

(defn- parse-image
  [img]
  (->> img
       (str/split-lines)
       (map (fn [xs] (map #(if (= % \.) 0 1) xs)))
       (map #(into [] %))
       (into [])))

(defn- parse-input
  [input]
  (->> input
       (#(str/split % #"\n\n"))
       ((fn [[alg img]]
          {:algorithm (parse-algorithm alg)
           :image (parse-image img)}))))

(defn alg-index
  [[row col] dim m]
  (let [xs [[(dec row) (dec col)]
            [(dec row) col]
            [(dec row) (inc col)]
            [row (dec col)]
            [row col]
            [row (inc col)]
            [(inc row) (dec col)]
            [(inc row) col]
            [(inc row) (inc col)]]
        color (first (first m))]
    (->> xs
         (map (fn [coord]
                (if (mx/valid-coord? coord dim)
                  (mx/get-val coord m)
                  color)))
         (apply str)
         ((fn [x] (Integer/parseInt x 2))))))

(defn- frame-image
  [img color]
  (let [w (count (first img))
        b (into [] (repeat (+ w 4) color))]
    (->> img
         (map #(concat [color color] % [color color]))
         (map #(into [] %))
         (#(concat [b b] % [b b]))
         (map #(into [] %))
         (into []))))

(defn- gen-indices
  [m]
  (let [[h w] (mx/dimensions m)
        hr (range h)
        wr (range w)]
    (map (fn [r] (map (fn [c] [r c]) wr)) hr)))

(defn- to-alg-indices
  [img]
  (let [dim (mx/dimensions img)
        indices (gen-indices img)]
    (map (fn [r]
           (map (fn [x]
                  (alg-index x dim img))
                r))
         indices)))

(defn- enchance-once
  [img alg]
  (->> img
       (#(frame-image % (first (first img))))
       (to-alg-indices)
       (map (fn [r] (map #(nth alg %) r)))
       (map #(into [] %))
       (into [])))

(defn- enchance
  ([img alg] (enchance img alg 1))
  ([img alg n]
   (loop [img img
          n n]
     (if (zero? n)
       img
       (recur
        (enchance-once img alg)
        (dec n))))))

(defn- count-lit
  [img]
  (->> img
       (map #(reduce + %))
       (reduce +)))

(defn- solve
  [{img :image alg :algorithm} n]
  (count-lit (enchance (frame-image img 0) alg n)))

(defn part1
  [input]
  (solve (parse-input input) 2))

(defn part2
  [input]
  (solve (parse-input input) 50))
