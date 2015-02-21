(ns graphics.shapes
  (:import java.awt.geom.Arc2D$Double
           java.awt.geom.Area
           java.awt.geom.CubicCurve2D$Double
           java.awt.geom.Ellipse2D$Double
           java.awt.geom.Line2D$Double
           java.awt.geom.Rectangle2D
           java.awt.geom.Rectangle2D$Double
           java.awt.geom.QuadCurve2D$Double))

(defn arc_ [^Rectangle2D ellipse-bounds start extent type]
  (Arc2D$Double. ellipse-bounds start extent type))

(defmacro arc [ellipse-bounds start extent type-sym]
  `(arc_ ~ellipse-bounds ~start ~extent
         ~(symbol (format "java.awt.geom.Arc2D/%s" type-sym))))

(defn rectangle [x y width height]
  (Rectangle2D$Double. x y width height))

(defn ellipse [x y width height]
  (Ellipse2D$Double. x y width height))

(defn line [x-start y-start x-end y-end]
  (Line2D$Double. x-start y-start x-end y-end))

(defn quad-curve [x1 y1 ctrl-x ctrl-y x2 y2]
  (QuadCurve2D$Double. x1 y1 ctrl-x ctrl-y x2 y2))

(defn cubic-curve [x1 y1 ctrl-x1 ctrl-y1 ctrl-x2 ctrl-y2 x2 y2]
  (CubicCurve2D$Double. x1 y1 ctrl-x1 ctrl-y1 ctrl-x2 ctrl-y2 x2 y2))

(defn circle [x y radius]
  (ellipse (- x radius) (- y radius) (* 2 radius) (* 2 radius)))

(defn area [shape]
  (Area. shape))

(defn add [^Area area1 ^Area area2]
  (.add area1 area2)
  area1)

(defn intersect [^Area area1 ^Area area2]
  (.intersect area1 area2)
  area1)

(defn subtract [^Area area1 ^Area area2]
  (.subtract area1 area2)
  area1)

(defn exclusive-or [^Area area1 ^Area area2]
  (.exclusiveOr area1 area2)
  area1)
