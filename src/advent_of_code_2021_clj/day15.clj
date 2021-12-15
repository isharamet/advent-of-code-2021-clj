(ns advent-of-code-2021-clj.day15
  (:require [clojure.string :as str]
            [clojure.data.priority-map :refer [priority-map-by]]
            [advent-of-code-2021-clj.matrix :as mx]))

(def zero (int \0))

(defn- parse-line
  [line]
  (->> line
       (to-array)
       (map int)
       (map #(- % zero))
       (into [])))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)
       (into [])))

(defn- gen-neighbours
  [[row col] dim]
  (let [adj [[(inc row) col]
             [row (dec col)]
             [row (inc col)]]]
    (filter #(mx/valid-coord? % dim) adj)))

(defn- gen-not-visited-neighbours
  [coord dim not-visited]
  (filter #(contains? not-visited %) (gen-neighbours coord dim)))

(defn- find-distances
  [m]
  (let [[h w] (mx/dimensions m)
        coords (mx/gen-coords [h w])
        dist (into {[0 0] 0}
                   (map (fn [c] [c Integer/MAX_VALUE]) (rest coords)))
        q (reduce (fn [acc x] (assoc acc x Integer/MAX_VALUE))
                  (priority-map-by < [0 0] 0)
                  (rest coords))]
    (loop [dist dist
           q q]
      (if (empty? q)
        dist
        (let [[cu du] (peek q)
              q (pop q)
              vs (gen-not-visited-neighbours cu [h w] q)
              [dist q] (reduce
                        (fn [[dist q] cv]
                          (let [dt (+ du (mx/get-val cv m))
                                dv (dist cv)]
                            (if (< dt dv)
                              [(assoc dist cv dt)
                               (assoc q cv dt)]
                              [dist q])))
                        [dist q]
                        vs)]
          (recur dist q))))))

(defn- solve
  [m]
  (let [[h w] (mx/dimensions m)
        dist (find-distances m)]
    (dist [(dec h) (dec w)])))

(defn- scale-fn
  [factor]
  (fn [x]
    (let [x (+ x factor)]
      (if (> x 9) (mod (inc x) 10) x))))

(defn- scale-matrix
  [m factor]
  (let [r (range factor)
        m (map #(reduce (fn [acc i] (into acc (map (scale-fn i) %))) [] r) m)
        m (reduce (fn [acc i] (into acc (mx/transform m (scale-fn i)))) [] r)]
    m))

(defn part1
  [input]
  (solve (parse-input input)))

(defn part2
  [input]
  (->> input
       (parse-input)
       (#(scale-matrix % 5))
       (solve)))
