(ns advent-of-code-2021-clj.day11
  (:require [clojure.string :as str]
            [advent-of-code-2021-clj.matrix :as mx]
            [advent-of-code-2021-clj.matrix-viz :as viz]))

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

(defn- update-energy
  [coord dim m]
  (let [x (mx/get-val coord m)]
    (if (> x 9)
      m
      (let [x (inc x)]
        (if (> x 9)
          (let [adj (mx/gen-adjacent coord dim)]
            (reduce #(update-energy %2 dim %1) (mx/set-val coord x m) adj))
          (mx/set-val coord x m))))))

(defn- count-flashes
  [m]
  (->> m
       (map (fn [r] (filter #(> % 9) r)))
       (map #(count %))
       (reduce +)))

(defn- reset-flasehes
  [m]
  (->> m
       (map #(map (fn [x] (if (> x 9) 0 x)) %))
       (map #(into [] %))
       (into [])))

(defn- count-and-reset
  [m]
  (let [c (count-flashes m)
        m (reset-flasehes m)]
    {:m m :c c}))

(defn- progress
  [coords dim m]
  (->> coords
       (reduce #(update-energy %2 dim %1) m)
       (count-and-reset)))

(defn- progress-n
  [m n]
  (let [dim (mx/dimensions m)
        coords (mx/gen-coords dim)
        cycles (range n)]
    (reduce (fn [{m :m c :c} _]
              (let [r (progress coords dim m)]
                (update r :c #(+ c %))))
            {:m m :c 0}
            cycles)))

(defn- synchronized?
  [m]
  (every? true? (map (fn [r] (every? zero? r)) m)))

(defn- progress-till-sync
  [m]
  (let [dim (mx/dimensions m)
        coords (mx/gen-coords dim)
        frame (viz/create-window m)]
    (loop [m m
           c 0]
      (let [m ((progress coords dim m) :m)
            c (inc c)
            _ (do (Thread/sleep 200)
                  (viz/draw-matrix frame m))]
        (if (synchronized? m) c (recur m c))))))

(defn- solve1
  [m]
  ((progress-n m 100) :c))

(defn- solve2
  [m]
  (progress-till-sync m))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (solve2 (parse-input input)))