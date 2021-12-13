(ns advent-of-code-2021-clj.day13
  (:require [clojure.string :as str]))

(defn- parse-dot
  [input]
  (->> input
       (#(str/split % #","))
       (map #(Integer/parseInt %))
       (into [])))

(defn- parse-dots
  [input]
  (->> input
       (str/split-lines)
       (map parse-dot)
       (into [])))

(defn- parse-fold
  [input]
  (let [matcher (re-matcher #"fold along (\S)=(\d+)" input)
        [_ axis val] (re-find matcher)]
    {:axis axis
     :val (Integer/parseInt val)}))

(defn- parse-folds
  [input]
  (map parse-fold (str/split-lines input)))

(defn- parse-input
  [input]
  (let [[dots folds] (str/split input #"\n\n")
        dots (parse-dots dots)
        folds (parse-folds folds)]
    {:dots dots
     :folds folds}))

(defn map-vals
  [m f]
  (reduce (fn [m [k v]] (assoc m k (f v))) {} m))

(defn- visualize
  [dots]
  (let [[w _] (last (sort-by (fn [[x _]] x) dots))
        [_ h] (last (sort-by (fn [[_ y]] y) dots))
        w (inc w)
        h (inc h)
        rows (group-by (fn [[_ y]] y) dots)
        rows (map-vals rows #(map (fn [[x _]] x) %))
        rows (map-vals rows set)
        rows (into {} rows)]
    (doseq [y (range h)]
      (let [row (rows y #{})]
        (do (doseq [x (range w)]
              (if (contains? row x)
                (print "x ")
                (print ". ")))
            (println))))))

(defn- make-a-fold
  [dots {axis :axis val :val}]
  (map (fn [dot]
         (if (nil? dot)
           dot
           (let [[x y] dot]
             (case axis
               "x" (if (= x val)
                     nil
                     (if (> x val)
                       [(- (* 2 val) x) y]
                       dot))
               "y" (if (= y val)
                     nil
                     (if (> y val)
                       [x (- (* 2 val) y)]
                       dot))
               dot))))
       dots))

(defn- visible-dots
  [dots folds]
  (->> folds
       (reduce make-a-fold dots)
       (filter (complement nil?))
       (set)
      ;;  (#(do (visualize %) %))
       (count)))

(defn- solve1
  [{dots :dots folds :folds}]
  (visible-dots dots (take 1 folds)))

(defn- solve2
  [{dots :dots folds :folds}]
  (visible-dots dots folds))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (solve2 (parse-input input)))