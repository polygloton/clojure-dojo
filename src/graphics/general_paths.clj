(ns graphics.general-paths
  (:require [graphics.common :refer :all])
  (:import java.awt.geom.GeneralPath))

(defn general-path []
  (GeneralPath.))

(def-genpath-method move-to [x y])

(def-genpath-method line-to [x y])

(def-genpath-method quad-to [x1 y1 x2 y2])

(def-genpath-method curve-to [x1 y1 x2 y2 x3 y3])
