(ns ^{:doc "Examples from Introduction to Computer Graphics Using Java 2D
            3D, book and original example code by Frank Klawonn.
            The example code here is translated to Clojure by Stephen Sloan."}
  clojure-dojo.graphics-intro.drawings
  (:require [graphics.affine-transforms :as at]
            [graphics.common :refer [def-drawing]]
            [graphics.fonts :as fnt]
            [graphics.frames :as f]
            [graphics.general-paths :as gp]
            [graphics.g2d :as g]
            [graphics.shapes :as s]
            [lonocloud.synthread :as ->]))

(def-drawing simple-2d-example [g2d]
  (g/draw-string g2d "Hello, World!" 30 50))

(comment
  (f/paint-fn simple-2d-example
           :title "The first Clojure 2D program"
           :size [350 80]))

(def-drawing draw-small-square [g2d color x y]
  (-> g2d
      (g/set-paint color)
      (g/fill (s/rectangle (- x 2) (- y 2) 4 4))))

(def-drawing curve-demo [g2d]
  (-> g2d
      (g/set-antialias-on)
      (g/set-basic-stroke 3.0)
      (g/draw (s/line 200 200
                      300 300))
      (draw-small-square :blue 200 200)
      (draw-small-square :red 300 300)
      (g/set-paint :black)
      (g/draw (s/quad-curve 300 50
                            500 500
                            550 100))
      (draw-small-square :blue 300 50)
      (g/draw-line 300 50
                   500 500)
      (draw-small-square :green 500 500)
      (draw-small-square :red 550 100)
      (g/draw-line 550 100
                   500 500)
      (g/set-paint :black)
      (g/draw (s/cubic-curve 100 50
                             150 100
                             50 500
                             100 400))
      (draw-small-square :blue 100 50)
      (g/draw-line 100 50
                   150 100)
      (draw-small-square :green 150 100)
      (draw-small-square :magenta 50 500)
      (draw-small-square :red 100 400)
      (g/draw-line 50 500
                   100 400)))

(comment
  (f/paint-fn curve-demo
           :title "A cubic curve, a line segment, and a quadratic curve"
           :size [600 600]))

(def-drawing coordinate-system [g2d x-max y-max]
  (let [x-offset 30
        y-offset 40
        step 20]
    (-> g2d
        (fnt/with-font-> (fnt/font "sansserif" PLAIN 9)
          (g/set-basic-stroke 1.0)
          (g/draw-line x-offset y-offset x-max y-offset)
          (->/for [i (range (+ x-offset step) (inc x-max) step)]
            (-> (g/draw-line i (- y-offset 2)
                             i (+ y-offset 2))
                (g/draw-string (str i)
                               (- i 7) (- y-offset 7))))
          (g/draw-line x-offset y-offset
                       x-offset y-max)
          (->/for [i (range (+ y-offset step) (inc y-max) step)]
            (-> (g/draw-line (- x-offset 2) i
                             (+ x-offset 2) i)
                (g/draw-string (str (if (> i 99) "" "   ") i)
                               (- x-offset 25) (+ i 5))))))))

(def-drawing general-path-car [g2d]
  (-> g2d
      (g/set-antialias-on)
      (g/set-basic-stroke 3.0)
      (g/draw
       (-> (gp/general-path)
           (gp/move-to 60 120) ;; start at lower front
           (gp/line-to 80 120) ;; front underbody
           (gp/quad-to 90 140  ;; front wheel
                       100 120)
           (gp/line-to 160 120) ;; middle underbody
           (gp/quad-to 170 140  ;; rear wheel
                       180 120)
           (gp/line-to 200 120) ;; rear underbody
           (gp/curve-to 195 100 ;; rear
                        200 80
                        160 80)
           (gp/line-to 110 80) ;; roof
           (gp/line-to 90 100) ;; windscreen
           (gp/line-to 60 100) ;; hood
           (gp/line-to 60 120) ;; front
           ))))

(comment
  (f/paint-fn (comp #(coordinate-system % 200 150) general-path-car)
              :title "GeneralPath example"
              :size [250 200]))

(def-drawing rectangle-ellipse-example [g2d]
  (-> g2d
      (g/set-antialias-on)
      (g/set-basic-stroke 3.0)
      (g/draw (s/rectangle 50 60 150 100))
      (g/draw (s/ellipse 250 60 150 100))
      (g/set-basic-stroke 1.0)
      (g/draw (s/rectangle 250 60 150 100))))

(comment
  (f/paint-fn (comp #(coordinate-system % 430 180) rectangle-ellipse-example)
              :title "Rectangle and ellipse"
              :size [450 200]))

(def-drawing arc-example [g2d]
  (let [thin-stroke (g/basic-stroke 1.0)
        thick-stroke (g/basic-stroke 3.0)
        rect1 (s/rectangle 50 50
                           200 100)
        rect2 (s/rectangle 300 50
                           200 100)
        rect3 (s/rectangle 550 50
                            200 100)]
    (-> g2d
        (g/set-antialias-on)
        (g/set-stroke thin-stroke)
        (g/draw rect1)
        (g/set-stroke thick-stroke)
        (g/draw (s/arc rect1 45 90 OPEN))
        (g/set-stroke thin-stroke)
        (g/draw rect2)
        (g/set-stroke thick-stroke)
        (g/draw (s/arc rect2 45 90 PIE))
        (g/set-stroke thin-stroke)
        (g/draw rect3)
        (g/set-stroke thick-stroke)
        (g/draw (s/arc rect3 45 90 CHORD)))))

(comment (f/paint arc-example
                  :title "Ellipse arc, section and segment"
                  :size [800 200]))

(def-drawing area-example [g2d]
  (let [radius 50
        start-x 100
        start-y 100
        width 70
        height 100
        rect-shift-x -10
        rect-shift-y 10
        x-shift 140]
    (-> g2d
        (g/set-antialias-on)
        (g/fill (s/add
                 (s/area
                  (s/circle start-x
                            start-y
                            radius))
                 (s/area
                  (s/rectangle (+ start-x rect-shift-x)
                               (+ start-y rect-shift-y)
                               width
                               height))))
        (g/fill (s/intersect
                 (s/area
                  (s/circle (+ start-x x-shift)
                            start-y
                            radius))
                 (s/area
                  (s/rectangle (+ start-x rect-shift-x x-shift)
                               (+ start-y rect-shift-y)
                               width
                               height))))
        (g/fill (s/subtract
                 (s/area
                  (s/circle (+ start-x (* 2 x-shift))
                            start-y
                            radius))
                 (s/area
                  (s/rectangle (+ start-x rect-shift-x (* 2 x-shift))
                               (+ start-y rect-shift-y)
                               width
                               height))))
        (g/fill (s/exclusive-or
                (s/area
                 (s/circle (+ start-x (* 3 x-shift))
                           start-y
                           radius))
                (s/area
                 (s/rectangle (+ start-x rect-shift-x (* 3 x-shift))
                              (+ start-y rect-shift-y)
                              width
                              height)))))))

(comment
  (f/paint-fn area-example
              :title "Operations for combining areas"
              :size [600 250]))

(def-drawing scaling-example [g2d height]
  (g/set-antialias-on g2d)
  (let [y-up (at/affine-transform)
        translate (at/affine-transform)
        scaling (at/affine-transform)
        rect (s/rectangle 80 120 100 60)]
    (at/set-to-scale y-up 1 -1)
    (at/set-to-translation translate 40 (- height 50))
    (at/pre-concatenate y-up translate)
    (at/transform g2d y-up)
    (g/set-basic-stroke g2d 3.0)
    (g/draw g2d rect)
    (at/set-to-scale scaling 2 0.5)
    (g/set-stroke g2d (g/basic-stroke 3.0
                                      :butt
                                      :bevel
                                      8.0
                                      [50.0 10.0]
                                      4.0))
    (g/draw g2d (at/create-transformed-shape scaling rect))
    (g/set-basic-stroke 1.0)))

(comment
  (let [height 300]
    (f/paint-fn #(scaling-example % height)
                :title "Scaling examples"
                :size [500 height])))
