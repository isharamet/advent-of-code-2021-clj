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

(defn- paths1
  [cave visited mapping]
  (if (= cave end)
    1
    (if (and (small? cave) (contains? visited cave))
      0
      (let [caves (mapping cave)]
        (if (empty? caves)
          0
          (->> caves
               (map #(paths1 % (conj visited cave) mapping))
               (reduce +)))))))

(defn- allowed-to-visit?
  [cave visited]
  (let [cnt (get visited cave 0)]
    (if (= cave start)
      (zero? cnt)
      (if (and (small? cave) (> cnt 0))
        (->> visited
             (filter (fn [[k _]] (small? k)))
             (map (fn [[_ v]] v))
             (every? #(< % 2)))
        true))))

(defn- update-visit-count
  [cave visited]
  (update visited cave #(if (nil? %) 1 (inc %))))

(defn- paths2
  [cave visited mapping]
  (if (= cave end)
    1
    (if (allowed-to-visit? cave visited)
      (let [caves (mapping cave)]
        (if (empty? caves)
          0
          (->> caves
               (map #(paths2 % (update-visit-count cave visited) mapping))
               (reduce +))))
      0)))

(defn- solve1
  [input]
  (paths1 start #{} input))

(defn- solve2
  [input]
  (paths2 start {} input))

(defn part1
  [input]
  (solve1 (parse-input input)))

(defn part2
  [input]
  (solve2 (parse-input input)))