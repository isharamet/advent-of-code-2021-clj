(ns advent-of-code-2021-clj.core
  (:gen-class)
  (:require [clojure.java.io :as io]))


(defn read-input
  [path]
  (slurp (io/resource path)))


(defn -main
  [& args]
  (read-input "day01.txt"))
