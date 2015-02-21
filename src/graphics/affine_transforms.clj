(ns graphics.affine-transforms
  (:import java.awt.Graphics2D
           java.awt.geom.AffineTransform))

;; Transforms

(defn affine-transform []
  (AffineTransform.))

(defn set-to-scale [^AffineTransform at x y]
  (.setToScale at x y)
  at)

(defn set-to-translation [^AffineTransform at x y]
  (.setToTranslation at x y)
  at)

(defn pre-concatenate [^AffineTransform at1 ^AffineTransform at2]
  (.preConcatenate at1 at2)
  at1)

(defn transform [^Graphics2D g2d ^AffineTransform at]
  (.transform g2d at)
  g2d)

(defn create-transformed-shape [^AffineTransform at shape]
  (.createTransformedShape at shape))
