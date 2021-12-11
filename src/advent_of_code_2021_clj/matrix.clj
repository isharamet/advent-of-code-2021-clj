(ns advent-of-code-2021-clj.matrix)

(defn dimensions
  [m]
  (let [h (count m)
        w (count (first m))]
    [h w]))

(defn get-val
  [[row col] m]
  (nth (nth m row) col))

(defn set-val
  [[row col] val m]
  (assoc m row (assoc (nth m row) col val)))

(defn gen-coords
  [[height width]]
  (let [indices (range (* height width))]
    (map (fn [x]
           [(int (/ x width)) (mod x width)])
         indices)))

(defn valid-coord?
  [[row col] [height width]]
  (and (>= row 0) (< row height)
       (>= col 0) (< col width)))

(defn gen-adjacent
  [[row col] dim]
  (let [adj [[(dec row) col]
             [(inc row) col]
             [row (dec col)]
             [row (inc col)]
             [(dec row) (inc col)]
             [(dec row) (dec col)]
             [(inc row) (inc col)]
             [(inc row) (dec col)]]]
    (filter #(valid-coord? % dim) adj)))

