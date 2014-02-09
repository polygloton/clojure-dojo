(ns ^{:doc "Functions for working with Frame objects"}
  clojure-dojo.graphics-intro.frame
  (:require [clojure-dojo.graphics-intro.definitions :refer :all])
  (:import java.awt.event.WindowAdapter
           java.awt.event.WindowEvent
           java.awt.Frame))

(defn enable-close-button [^Frame frame]
  (.addWindowListener frame (proxy [WindowAdapter] []
                              (windowClosing [^WindowEvent e]
                                (.dispose frame))))
  frame)

(def-window-setter set-size Size [x y])

(def-frame-setter set-title Title [s])

(def-window-setter set-visible Visible [visible?])
