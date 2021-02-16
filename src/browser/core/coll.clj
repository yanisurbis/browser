(ns browser.core.coll
  (:require [camel-snake-kebab.core :as csk]
            [camel-snake-kebab.extras :as cske]
            [clojure.walk :as walk]))

(defn ->kebab-case [m]
  (cske/transform-keys csk/->kebab-case m))

(defn remove-where
  "Removes the map entry if the value satisfies predicate.
  Also walks into nested maps"
  [m pred?]
  (walk/postwalk
    (fn [x] (if (map? x)
              (into {} (remove (fn [[k v]]
                                 (pred? v))) x)
              x)) m))

(defn remove-nils
  "Removes the map entry if the value for the key is nil. Also walks into nested maps"
  [m]
  (remove-where m nil?))