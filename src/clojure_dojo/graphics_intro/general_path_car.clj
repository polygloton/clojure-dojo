(ns ^{:doc "An example for the use of a GeneralPath to draw a car."}
  clojure-dojo.graphics-intro.general-path-car
  (:require [clojure-dojo.graphics-intro.frame :refer
             [enable-close-button set-title set-visible set-size]]
            [clojure-dojo.graphics-intro.graphics-2d :refer
             [set-antialias-on set-basic-stroke with-font-> draw draw-line
              draw-string font]]
            [clojure-dojo.graphics-intro.general-path :refer
             [general-path move-to line-to quad-to curve-to]]
            [lonocloud.synthread :as ->])
  (:import java.awt.Frame
           java.awt.Graphics
           java.awt.Graphics2D))

(defn draw-simple-coordinate-system [^Graphics2D g2d x-max y-max]
  (let [x-offset 30
        y-offset 50
        step 20]
    (-> g2d
        (with-font-> (font "sansserif" PLAIN 9)
          (set-basic-stroke 1.0)
          (draw-line x-offset y-offset x-max y-offset)
          (->/for [i (range (+ x-offset step) (inc x-max) step)]
            (-> (draw-line i (- y-offset 2)
                           i (+ y-offset 2))
                (draw-string (str i)
                             (- i 7) (- y-offset 7))))
          (draw-line x-offset y-offset
                     x-offset y-max)
          (->/for [i (range (+ y-offset step) (inc y-max) step)]
            (-> (draw-line (- x-offset 2) i
                           (+ x-offset 2) i)
                (draw-string (str (if (> i 99) "" "   ") i)
                             (- x-offset 25) (+ i 5))))))))

(defn general-path-car []
  (-> (proxy [Frame] []
        (paint [^Graphics g]
          (-> (cast Graphics2D g)
              (set-antialias-on)
              (set-basic-stroke 3.0)
              (draw
               (-> (general-path)
                   (move-to 60 120)  ;; start at lower front
                   (line-to 80 120)  ;; front underbody
                   (quad-to 90 140   ;; front wheel
                            100 120)
                   (line-to 160 120) ;; middle underbody
                   (quad-to 170 140  ;; rear wheel
                            180 120)
                   (line-to 200 120) ;; rear underbody
                   (curve-to 195 100 ;; rear
                             200 80
                             160 80)
                   (line-to 110 80)  ;; roof
                   (line-to 90 100)  ;; windscreen
                   (line-to 60 100)  ;; hood
                   (line-to 60 120)  ;; front
                   ))
              (draw-simple-coordinate-system 200 150)
              )))
      (enable-close-button)
      (set-title "GeneralPath example")
      (set-size 250 200)
      (set-visible true)))
