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
        prev (into {} (map (fn [c] [c nil]) coords))
        q (reduce (fn [acc x] (assoc acc x Integer/MAX_VALUE))
                  (priority-map-by < [0 0] 0)
                  (rest coords))]
    (loop [dist dist
           prev prev
           q q]
      (if (empty? q)
        [dist prev]
        (let [[cu du] (peek q)
              q (pop q)
              vs (gen-not-visited-neighbours cu [h w] q)
              [dist prev q] (reduce
                             (fn [[dist prev q] cv]
                               (let [dt (+ du (mx/get-val cv m))
                                     dv (dist cv)]
                                 (if (< dt dv)
                                   [(assoc dist cv dt)
                                    (assoc prev cv cu)
                                    (assoc q cv dt)]
                                   [dist prev q])))
                             [dist prev q]
                             vs)]
          (recur dist prev q))))))

(defn- solve
  [m]
  (let [[h w] (mx/dimensions m)
        [dist prev] (find-distances m)]
    (dist [(dec h) (dec w)])))

(defn part1
  [input]
  (solve (parse-input input)))

(defn part2
  [input]
  (parse-input input))
