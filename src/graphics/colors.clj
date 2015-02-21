(ns graphics.colors
  (:import java.awt.Color))

(def color-map
  {:black      Color/black
   :blue       Color/blue
   :cyan       Color/cyan
   :dark-gray  Color/darkGray
   :gray       Color/gray
   :green      Color/green
   :light-gray Color/lightGray
   :lime       (Color. 0 255 0)
   :magenta    Color/magenta
   :maroon     (Color. 128 0 0)
   :navy       (Color. 0 0 128)
   :olive      (Color. 128 128 0)
   :orange     Color/orange
   :pink       Color/pink
   :purple     (Color. 128 0 128)
   :red        Color/red
   :silver     (Color. 192 192 192)
   :teal       (Color. 0 128 128)
   :white      Color/white
   :yellow     Color/yellow})
