(ns clojure-dojo.graphics-intro.drawings
  (:require [clojure-dojo.graphics-intro.graphics :as g]
            [lonocloud.synthread :as ->]))

(defmacro def-drawing [name [g2d-sym & args] & sexprs]
  `(defn ~name [~g2d-sym ~@args]
     ~@sexprs
     ~g2d-sym))

(def-drawing simple-2d-example [g2d]
  (g/draw-string g2d "Hello, World!" 30 50))

(comment
  (g/paint (simple-2d-example)
           :title "The first Clojure 2D program"
           :size [350 80]))

(defmacro draw-small-square [g2d color x y]
  `(-> ~g2d
       (g/set-paint ~color)
       (g/fill (g/rectangle ~x ~y 4 4))))

(def-drawing curve-demo [g2d]
  (-> g2d
      (g/set-antialias-on)
      (g/set-basic-stroke 3.0)
      (g/draw (g/line 200 200
                      300 300))
      (draw-small-square blue 200 200)
      (draw-small-square red 300 300)
      (g/set-paint black)
      (g/draw (g/quad-curve 300 50
                            500 500
                            550 100))
      (draw-small-square blue 300 50)
      (g/draw-line 300 50
                   500 500)
      (draw-small-square green 500 500)
      (draw-small-square red 550 100)
      (g/draw-line 550 100
                   500 500)
      (g/set-paint black)
      (g/draw (g/cubic-curve 100 50
                             150 100
                             50 500
                             100 400))
      (draw-small-square blue 100 50)
      (g/draw-line 100 50
                   150 100)
      (draw-small-square green 150 100)
      (draw-small-square magenta 50 500)
      (draw-small-square red 100 400)
      (g/draw-line 50 500
                   100 400)))

(comment
  (g/paint (curve-demo)
           :title "A cubic curve, a line segment, and a quadratic curve"
           :size [600 600]))

(def-drawing coordinate-system [g2d x-max y-max]
  (let [x-offset 30
        y-offset 40
        step 20]
    (-> g2d
        (g/with-font-> (g/font "sansserif" PLAIN 9)
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
       (-> (g/general-path)
           (g/move-to 60 120) ;; start at lower front
           (g/line-to 80 120) ;; front underbody
           (g/quad-to 90 140  ;; front wheel
                    100 120)
           (g/line-to 160 120) ;; middle underbody
           (g/quad-to 170 140  ;; rear wheel
                    180 120)
           (g/line-to 200 120) ;; rear underbody
           (g/curve-to 195 100 ;; rear
                     200 80
                     160 80)
           (g/line-to 110 80) ;; roof
           (g/line-to 90 100) ;; windscreen
           (g/line-to 60 100) ;; hood
           (g/line-to 60 120) ;; front
           ))))

(comment
  (g/paint-fn (comp #(coordinate-system % 200 150) general-path-car)
                     :title "GeneralPath example"
                     :size [250 200]))

(def-drawing rectangle-ellipse-example [g2d]
  (-> g2d
      (g/set-antialias-on)
      (g/set-basic-stroke 3.0)
      (g/draw (g/rectangle 50 60 150 100))
      (g/draw (g/ellipse 250 60 150 100))
      (g/set-basic-stroke 1.0)
      (g/draw (g/rectangle 250 60 150 100))))

(comment
  (g/paint-fn (comp #(coordinate-system % 430 180) rectangle-ellipse-example)
                     :title "Rectangle and ellipse"
                     :size [450 200]))