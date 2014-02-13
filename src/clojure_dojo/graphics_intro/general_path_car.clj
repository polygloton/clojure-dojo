(ns ^{:doc "An example for the use of a GeneralPath to draw a car."}
  clojure-dojo.graphics-intro.general-path-car
  (:require [clojure-dojo.graphics-intro.frame :refer
             [enable-close-button set-title set-visible set-size]]
            [clojure-dojo.graphics-intro.graphics-2d :refer
             [paint set-antialias-on set-basic-stroke draw]]
            [clojure-dojo.graphics-intro.general-path :refer
             [general-path move-to line-to quad-to curve-to]]
            [clojure-dojo.graphics-intro.drawings :as drawings]))

(defn general-path-car []
  (-> (paint [g2d]
             (-> g2d
                 (set-antialias-on)
                 (set-basic-stroke 3.0)
                 (draw
                  (-> (general-path)
                      (move-to 60 120) ;; start at lower front
                      (line-to 80 120) ;; front underbody
                      (quad-to 90 140  ;; front wheel
                               100 120)
                      (line-to 160 120) ;; middle underbody
                      (quad-to 170 140  ;; rear wheel
                               180 120)
                      (line-to 200 120) ;; rear underbody
                      (curve-to 195 100 ;; rear
                                200 80
                                160 80)
                      (line-to 110 80) ;; roof
                      (line-to 90 100) ;; windscreen
                      (line-to 60 100) ;; hood
                      (line-to 60 120) ;; front
                      ))
                 (drawings/draw-simple-coordinate-system 200 150)))
      (enable-close-button)
      (set-title "GeneralPath example")
      (set-size 250 200)
      (set-visible true)))
