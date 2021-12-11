(ns advent-of-code-2021-clj.matrix-viz
  (:import (javax.swing JFrame)
           (java.awt Color))
  (:require [advent-of-code-2021-clj.matrix :as mx]))

(def header-height 27)

(def scale 20)

(defn draw-matrix
  [frame m]
  (let [g (.getGraphics frame)
        dim (mx/dimensions m)
        coords (mx/gen-coords dim)]
    (doseq [[y x] coords]
      (let [val (mx/get-val [y x] m)
            red (if (= 0 val) 0 (int (* 255 (/ val 10))))
            color (Color. red 0 0)
            y (+ (* y scale) header-height)
            x (* x scale)]
        (doto g
          (.setColor color)
          (.fillRect x y scale scale)
          (.setColor (Color/BLACK)))))))

(defn create-window
  [m]
  (let [[h w] (mx/dimensions m)
        frame (JFrame. "AoC-2021")
        h (+ (* h scale) header-height)
        w (* w scale)]
    (doto frame
      (.setSize w h)
      (.setResizable false)
      (.setVisible true)
      (draw-matrix m))))
