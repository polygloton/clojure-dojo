(ns clojure-dojo.graphics-intro.rectangle-ellipse-example
  (:require [clojure-dojo.graphics-intro.frame :refer
             [enable-close-button set-title set-visible set-size]]
            [clojure-dojo.graphics-intro.graphics-2d :refer
             [paint-> set-antialias-on set-basic-stroke draw]]
            [clojure-dojo.graphics-intro.drawings :as drawings])
  (:import java.awt.geom.Rectangle2D$Double
           java.awt.geom.Ellipse2D$Double))

(defn rectangle-ellipse-example []
  (-> (paint-> g2d
               (set-antialias-on)
               (set-basic-stroke 3.0)
               (draw (Rectangle2D$Double. 50 60 150 100))
               (draw (Ellipse2D$Double. 250 60 150 100))
               (set-basic-stroke 1.0)
               (draw (Rectangle2D$Double. 250 60 150 100))
               (drawings/draw-simple-coordinate-system 430 180))
      (enable-close-button)
      (set-title "Rectangle and ellipse")
      (set-size 450 200)
      (set-visible true)))

