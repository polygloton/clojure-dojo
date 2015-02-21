(ns graphics.fonts
  (:import java.awt.Font
           java.awt.Graphics2D
           java.awt.geom.AffineTransform))

(defmacro with-font [^Graphics2D g2d ^Font font & sexprs]
  `(let [^Font orig-font# (.getFont ~g2d)]
     (.setFont ~g2d ~font)
     (let [result# (do ~@sexprs)]
       (.setFont ~g2d orig-font#)
       result#)))

(defmacro with-font-> [^Graphics2D g2d ^Font font & sexprs]
  `(let [^Font orig-font# (.getFont ~g2d)]
     (.setFont ~g2d ~font)
     (let [result# (-> ~g2d ~@sexprs)]
       (.setFont ~g2d orig-font#)
       result#)))

(defmacro font [name style size]
  `(java.awt.Font. ~name ~(symbol (format "java.awt.Font/%s" style)) ~size))

(defn derive-font [^Font f ^AffineTransform at]
  (.deriveFont f at))
