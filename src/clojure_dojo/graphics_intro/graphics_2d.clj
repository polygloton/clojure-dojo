(ns ^{:doc "Functions for working with Graphics2D objects"}
  clojure-dojo.graphics-intro.graphics-2d
  (:require [clojure-dojo.graphics-intro.definitions :refer :all])
  (:import java.awt.RenderingHints
           java.awt.BasicStroke
           java.awt.Frame
           java.awt.Graphics
           java.awt.Graphics2D
           java.awt.Font))

(defmacro paint [[graphics-2d-sym] & sexprs]
  `(proxy [Frame] []
     (paint [^Graphics g#]
       (let [~graphics-2d-sym (cast Graphics2D g#)]
         ~@sexprs))))

(defmacro paint-> [graphics-2d-sym & sexprs]
  `(paint [~graphics-2d-sym]
          (-> ~graphics-2d-sym
              ~@sexprs)))

(def-g2d-method draw [obj])

(def-g2d-method draw-line [x1 y1 x2 y2])

(def-g2d-method draw-string [str x y])

(def-g2d-setter set-font Font [font])

(def-g2d-getter get-font Font)

(def-g2d-setter set-rendering-hint RenderingHint [name value])

(def-g2d-setter set-size Size [x y])

(def-g2d-setter set-stroke Stroke [stroke])

(defn set-antialias-on [g2d]
  (set-rendering-hint g2d
                      RenderingHints/KEY_ANTIALIASING
                      RenderingHints/VALUE_ANTIALIAS_ON))

(defn set-basic-stroke [g2d stroke]
  (set-stroke g2d (BasicStroke. stroke)))

(defmacro with-font-> [^Graphics2D g2d ^Font font & sexprs]
  `(let [^Font orig-font# (.getFont ~g2d)]
     (.setFont ~g2d ~font)
     (let [result# (-> ~g2d ~@sexprs)]
       (.setFont ~g2d orig-font#)
       result#)))

(defmacro font [name style size]
  `(java.awt.Font. ~name ~(symbol (format "java.awt.Font/%s" style)) ~size))
