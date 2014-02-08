(ns clojure-dojo.graphics-intro.simple-clojure-2d-example
  (:require [clojure-dojo.graphics-intro.my-finish-window :as mfw])
  (:import java.awt.Frame
           java.awt.Graphics
           java.awt.Graphics2D))

(def f (proxy [Frame] []
         (paint [^Graphics g]
           (.drawString (cast Graphics2D g)
                        "Hello, World!"
                        30
                        50))))

(doto f
  (mfw/add-window-listener)
  (.setTitle "The first Clojure 2D program")
  (.setSize 350 80)
  (.setVisible true))

