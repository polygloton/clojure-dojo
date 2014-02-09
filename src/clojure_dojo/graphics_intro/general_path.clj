(ns ^{:doc "Functions for working with GeneralPath objects"}
  clojure-dojo.graphics-intro.general-path
  (:require [clojure-dojo.graphics-intro.definitions :refer :all])
  (:import java.awt.geom.GeneralPath))

(defn general-path []
  (GeneralPath.))

(def-genpath-method move-to [x y])

(def-genpath-method line-to [x y])

(def-genpath-method quad-to [x1 y1 x2 y2])

(def-genpath-method curve-to [x1 y1 x2 y2 x3 y3])
