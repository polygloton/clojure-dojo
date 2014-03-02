(ns ^{:doc "Clojure wrapper around Java's 2D Graphics libs"}
  clojure-dojo.graphics-intro.graphics
  (:import java.awt.BasicStroke
           java.awt.Color
           java.awt.Font
           java.awt.Frame
           java.awt.Graphics
           java.awt.Graphics2D
           java.awt.RenderingHints
           java.awt.Window
           java.awt.geom.Arc2D$Double
           java.awt.geom.CubicCurve2D$Double
           java.awt.geom.Ellipse2D$Double
           java.awt.geom.GeneralPath
           java.awt.geom.Line2D$Double
           java.awt.geom.QuadCurve2D$Double
           java.awt.geom.Rectangle2D
           java.awt.geom.Rectangle2D$Double
           java.awt.event.WindowAdapter
           java.awt.event.WindowEvent))

;; Custom function definers

(defn ^:private ->camel-case [^String method-name]
  (clojure.string/replace method-name #"-(\w)" 
                          #(clojure.string/upper-case (second %1))))

(defmacro def-frame-setter [name method-sym args]
  `(defn ~name [^Frame f# ~@args]
     (~(symbol (format ".set%s" method-sym)) f# ~@args)
     f#))

(defmacro def-g2d-method [name args]
  `(defn ~name [^Graphics2D g2d# ~@args]
     (~(symbol (format ".%s" (->camel-case name))) g2d# ~@args)
     g2d#))

(defmacro def-g2d-getter [name method-sym]
  `(defn ~name [^Graphics2D g2d#]
     (~(symbol (format ".get%s" method-sym)) g2d#)
     g2d#))

(defmacro def-g2d-setter [name method-sym args]
  `(defn ~name [^Graphics2D g2d# ~@args]
     (~(symbol (format ".set%s" method-sym)) g2d# ~@args)
     g2d#))

(defmacro def-genpath-method [name args]
  `(defn ~name [^GeneralPath gp# ~@args]
     (~(symbol (format ".%s" (->camel-case name)))
      gp#
      ~@(map #(cons double (list %)) args))
     gp#))

(defmacro def-window-setter [name method-sym args]
  `(defn ~name [^Frame f# ~@args]
     (~(symbol (format ".set%s" method-sym)) (cast Window f#) ~@args)
     f#))

;; 2D Graphics

(def-g2d-method draw [obj])

(def-g2d-method fill [shape])

(def-g2d-method draw-line [x1 y1 x2 y2])

(def-g2d-method draw-string [str x y])

(def-g2d-setter set-font Font [font])

(def-g2d-getter get-font Font)

(def-g2d-setter set-paint_ Paint [color])

(defmacro set-paint [g2d color-sym]
  `(set-paint_ ~g2d ~(symbol (format "java.awt.Color/%s" color-sym))))

(def-g2d-setter set-rendering-hint RenderingHint [name value])

(def-g2d-setter set-size Size [x y])

(def-g2d-setter set-stroke Stroke [stroke])

(defn set-antialias-on [g2d]
  (set-rendering-hint g2d
                      RenderingHints/KEY_ANTIALIASING
                      RenderingHints/VALUE_ANTIALIAS_ON))

(defn basic-stroke [float_]
  (BasicStroke. float_))

(defn set-basic-stroke [g2d stroke]
  (set-stroke g2d (basic-stroke stroke)))

;; Fonts

(defmacro with-font-> [^Graphics2D g2d ^Font font & sexprs]
  `(let [^Font orig-font# (.getFont ~g2d)]
     (.setFont ~g2d ~font)
     (let [result# (-> ~g2d ~@sexprs)]
       (.setFont ~g2d orig-font#)
       result#)))

(defmacro font [name style size]
  `(java.awt.Font. ~name ~(symbol (format "java.awt.Font/%s" style)) ~size))

;; Frames

(defn enable-close-button [^Frame frame]
  (.addWindowListener frame (proxy [WindowAdapter] []
                              (windowClosing [^WindowEvent e]
                                (.dispose frame))))
  frame)

(def-window-setter set-size Size [x y])

(def-frame-setter set-title Title [s])

(def-window-setter set-visible Visible [visible?])

;; General Paths

(defn general-path []
  (GeneralPath.))

(def-genpath-method move-to [x y])

(def-genpath-method line-to [x y])

(def-genpath-method quad-to [x1 y1 x2 y2])

(def-genpath-method curve-to [x1 y1 x2 y2 x3 y3])

;; Shapes

(defn arc_ [^Rectangle2D ellipse-bounds start extent type]
  (Arc2D$Double. ellipse-bounds start extent type))

(defmacro arc [ellipse-bounds start extent type-sym]
  `(arc_ ~ellipse-bounds ~start ~extent
         ~(symbol (format "java.awt.geom.Arc2D/%s" type-sym))))

(defn rectangle [x y width height]
  (Rectangle2D$Double. x y width height))

(defn ellipse [x y width height]
  (Ellipse2D$Double. x y width height))

(defn line [x-start y-start x-end y-end]
  (Line2D$Double. x-start y-start x-end y-end))

(defn quad-curve [x1 y1 ctrl-x ctrl-y x2 y2]
  (QuadCurve2D$Double. x1 y1 ctrl-x ctrl-y x2 y2))

(defn cubic-curve [x1 y1 ctrl-x1 ctrl-y1 ctrl-x2 ctrl-y2 x2 y2]
  (CubicCurve2D$Double. x1 y1 ctrl-x1 ctrl-y1 ctrl-x2 ctrl-y2 x2 y2))

;; Paint

(defn paint-fn [f & {:keys [ title size]
                     :or [title "UNTITLED" size [300 200]]}]
  (-> (proxy [Frame] []
        (paint [^Graphics g]
          (f (cast Graphics2D g))))
      (enable-close-button)
      (set-title title)
      (set-size (nth size 0)
                (nth size 1))
      (set-visible true)))

(defmacro paint [body & kwargs]
  `(paint-fn
    (fn [g2d#]
      (-> g2d#
          ~body))
    ~@kwargs))
