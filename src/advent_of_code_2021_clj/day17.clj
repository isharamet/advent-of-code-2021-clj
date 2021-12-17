(ns advent-of-code-2021-clj.day17)

(def input-re
  #"target area: x=(\d+)\.\.(\d+), y=(-*\d+)\.\.(-*\d+)")

(defn- parse-input
  [input]
  (let [matcher (re-matcher input-re input)
        [_ x1 x2 y1 y2] (re-find matcher)]
    (map #(Integer/parseInt %) [x1 x2 y1 y2])))

(defn- solve1
  [[_ _ y1 _]]
  (let [y (Math/abs y1)]
    (/ (* y (dec y)) 2)))

(defn- overshoot?
  [[x y] [_ x2 y1 _]]
  (or (> x x2) (< y y1)))

(defn- within-area?
  [[x y] [x1 x2 y1 y2]]
  (and (<= x1 x x2) (<= y1 y y2)))

(defn- steps
  [vx vy]
  (iterate
   (fn [[[vx vy] [x y]]]
     [[(if (zero? vx) 0 (dec vx)) (dec vy)]
      [(+ x vx) (+ y vy)]])
   [[vx vy] [0 0]]))

(defn- hit?
  [[vx vy] target]
  (loop [ss (steps vx vy)]
    (let [[_ c] (first ss)]
      (if (within-area? c target)
        true
        (if (overshoot? c target)
          false
          (recur (rest ss)))))))

(defn- find-velocities
  [[x1 x2 y1 _ :as target]]
  (let [[vymin vymax] [y1 (- y1)]
        [vxmin vxmax] [(int (Math/floor (Math/sqrt (dec (* 2 x1))))) x2]
        vyr (range vymin (inc vymax))
        vxr (range vxmin (inc vxmax))]
    (reduce
     (fn [acc vy]
       (reduce
        (fn [acc vx]
          (if (hit? [vx vy] target)
            (conj acc [vx vy])
            acc))
        acc
        vxr))
     #{}
     vyr)))

(defn- solve2
  [target]
  (count (find-velocities target)))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (solve2 (parse-input input)))
