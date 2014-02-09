(ns ^{:doc "Custom defns for DRY'ing up graphics related function definitions"}
  clojure-dojo.graphics-intro.definitions
  (:import java.awt.Frame
           java.awt.Graphics2D
           java.awt.geom.GeneralPath
           java.awt.Window))

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
