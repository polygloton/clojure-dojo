(ns clojure-dojo.graphics-intro.curve-demo
  (:require [clojure-dojo.graphics-intro.my-finish-window :as mfw])
  (:import java.awt.Frame
           java.awt.RenderingHints
           java.awt.BasicStroke
           java.awt.Color
           java.awt.Graphics
           java.awt.Graphics2D
           java.awt.geom.Rectangle2D$Double
           java.awt.geom.Line2D$Double
           java.awt.geom.QuadCurve2D$Double
           java.awt.geom.CubicCurve2D$Double))

(defmacro draw-small-square [g2d color-sym x y]
  `(doto ~g2d
     (.setPaint ~(symbol (format "Color/%s" color-sym)))
     (.fill (Rectangle2D$Double. (- ~x 2) (- ~y 2) 4 4))))

(def f (proxy [Frame] []
         (paint [^Graphics g]
           (doto (cast Graphics2D g)
             ;; Use of antialiasing to have nicer lines.
             (.setRenderingHint RenderingHints/KEY_ANTIALIASING
                                RenderingHints/VALUE_ANTIALIAS_ON)
             ;; The lines should have a thickness of 3.0 instead of 1.0.
             (.setStroke (BasicStroke. 3.0))
             
             ;; Generate and draw the line segment
             (.draw (Line2D$Double. 200 200
                                    300 300))
             ;; Mark the initial point in blue.
             (draw-small-square blue 200 200)
             ;; Mark the endpoint in red
             (draw-small-square red 300 300)
             
             ;; Generate and draw the quadratic curve.
             (.setPaint Color/black)
             (.draw (QuadCurve2D$Double. 300 50
                                         500 500
                                         550 100))
             ;; Mark the initial point in blue.
             (draw-small-square blue 300 50)
             ;; Draw the line connecting the initial point and the control
             ;; point.
             (.drawLine 300 50
                        500 500)
             ;; Mark the control point in green.
             (draw-small-square green 500 500)
             ;; Mark the endpoint in red.
             (draw-small-square red 550 100)
             ;; Draw the line connecting the endpoint and the control point
             (.drawLine 550 100
                        500 500)
             
             ;; Generate and draw the cubic curve.
             (.setPaint Color/black)
             (.draw (CubicCurve2D$Double. 100 50
                                          150 100
                                          50 500
                                          100 400))
             ;; Mark the initial point in blue.
             (draw-small-square blue 100 50)
             ;; Draw the line connecting the initial poing and the first control
             ;; point.
             (.drawLine 100 50
                        150 100)
             ;; Mark the first control point in green
             (draw-small-square green 150 100)
             ;; Mark the second control point in magenta
             (draw-small-square magenta 50 500)
             ;; Mark the endpoint in red.
             (draw-small-square red 100 400)
             ;; Draw the line connecting the end point and the control point.
             (.drawLine 50 500
                        100 400)))))

(doto f
  (mfw/add-window-listener)
  (.setTitle "A cubic curve, a line segment, and a quadratic curve")
  (.setSize 600 600)
  (.setVisible true))

