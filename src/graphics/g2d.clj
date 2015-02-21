(ns graphics.g2d
  (:require [graphics.common :refer :all]
            [graphics.colors :as colors])
  (:import java.awt.BasicStroke
           java.awt.Color
           java.awt.RenderingHints))

(def-g2d-method draw [obj])

(def-g2d-method fill [shape])

(def-g2d-method draw-line [x1 y1 x2 y2])

(def-g2d-method draw-string [str x y])

(def-g2d-setter set-font Font [font])

(def-g2d-getter get-font Font)

(def-g2d-setter set-paint_ Paint [color])

(defn set-paint [g2d color]
  (set-paint_ g2d (if (instance? Color color)
                    color
                    (get colors/color-map color Color/pink))))

(def-g2d-setter set-rendering-hint RenderingHint [name value])

(def-g2d-setter set-size Size [x y])

(def-g2d-setter set-stroke Stroke [stroke])

(defn set-antialias-on [g2d]
  (set-rendering-hint g2d
                      RenderingHints/KEY_ANTIALIASING
                      RenderingHints/VALUE_ANTIALIAS_ON))

(defn basic-stroke [float_]
  (BasicStroke. float_))

(defn set-basic-stroke [g2d stroke]
  (set-stroke g2d (basic-stroke stroke)))
