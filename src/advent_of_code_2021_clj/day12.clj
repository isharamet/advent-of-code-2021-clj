(ns advent-of-code-2021-clj.day12
  (:require [clojure.string :as str]))

(def start "start")

(def end "end")

(defn- parse-line
  [line]
  (str/split line #"-"))

(defn- update-mapping
  [mapping [src dst]]
  (update mapping
          src
          (fn [x]
            (if (nil? x)
              #{dst}
              (conj x dst)))))

(defn- update-mapping-uni
  [mapping [src dst]]
  (->> mapping
       (#(update-mapping % [src dst]))
       (#(update-mapping % [dst src]))))

(defn- parse-input
  [input]
  (->> input
       (str/split-lines)
       (map parse-line)
       (reduce update-mapping-uni {})))

(defn- small?
  [node]
  (every? #(Character/isLowerCase %) node))

(defn- paths
  [node visited mapping]
  (if (= node end)
    1
    (if (and (small? node) (contains? visited node))
      0
      (let [nodes (mapping node)]
        (if (empty? nodes)
          0
          (->> nodes
               (map #(paths % (conj visited node) mapping))
               (reduce +)))))))

(defn- solve1
  [input]
  (paths start #{} input))

;; (defn- solve2
;;   [input]
;;   input)

(defn part1
  [input]
  (solve1 (parse-input input)))

;; (defn part2
;;   [input]
;;   (solve2 (parse-input input)))