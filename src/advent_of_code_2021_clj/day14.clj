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
       (map parse-insertion)))

(defn- parse-input
  [input]
  (let [[template insertions] (str/split input #"\n\n")
        template (parse-template template)
        insertions (parse-insertions insertions)]
    {:template template
     :insertions insertions}))

(defn- freq-map
  [s]
  (->> s
       (partition 2 1)
       (group-by identity)
       (map (fn [[k v]] [k (count v)]))
       (into {})))

(defn- update-cnt-fn
  [cnt]
  (fn [x] (if (nil? x) cnt (+ x cnt))))

(defn- result
  [m]
  (let [m (reduce
           (fn [acc [[a b] cnt]]
             (->> acc
                  (#(update % a (update-cnt-fn cnt)))
                  (#(update % b (update-cnt-fn cnt)))))
           {}
           m)
        xs (vals m)
        counts (map (fn [x] (Math/ceil (/ x 2))) xs)
        a (apply max counts)
        b (apply min counts)]
    (long (- a b))))

(defn- make-insertion
  [[m mn] [[a b] c]]
  (let [cnt (m [a b])]
    (if (nil? cnt)
      [m mn]
      [(dissoc m [a b])
       (->> mn
            (#(update % [a c] (update-cnt-fn cnt)))
            (#(update % [c b] (update-cnt-fn cnt))))])))

(defn- make-insertions
  [m inss]
  (let [[m mn] (reduce make-insertion [m {}] inss)]
    (merge-with + m mn)))

(defn- solve
  [{template :template insertions :insertions} n]
  (->> template
       (freq-map)
       (#(reduce
          (fn [m _] (make-insertions m insertions))
          %
          (range n)))
       (result)))

(defn part1
  [input]
  (solve (parse-input input) 10))

(defn part2
  [input]
  (solve (parse-input input) 40))
