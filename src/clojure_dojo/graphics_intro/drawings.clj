(ns clojure-dojo.graphics-intro.drawings
  (:require [clojure-dojo.graphics-intro.graphics-2d :as g2d]
            [lonocloud.synthread :as ->])
  (:import java.awt.Graphics2D))

(defn draw-simple-coordinate-system [^Graphics2D g2d x-max y-max]
  (let [x-offset 30
        y-offset 40
        step 20]
    (-> g2d
        (g2d/with-font-> (g2d/font "sansserif" PLAIN 9)
          (g2d/set-basic-stroke 1.0)
          (g2d/draw-line x-offset y-offset x-max y-offset)
          (->/for [i (range (+ x-offset step) (inc x-max) step)]
            (-> (g2d/draw-line i (- y-offset 2)
                           i (+ y-offset 2))
                (g2d/draw-string (str i)
                             (- i 7) (- y-offset 7))))
          (g2d/draw-line x-offset y-offset
                     x-offset y-max)
          (->/for [i (range (+ y-offset step) (inc y-max) step)]
            (-> (g2d/draw-line (- x-offset 2) i
                           (+ x-offset 2) i)
                (g2d/draw-string (str (if (> i 99) "" "   ") i)
                             (- x-offset 25) (+ i 5))))))))
