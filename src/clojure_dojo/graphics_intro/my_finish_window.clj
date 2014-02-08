(ns clojure-dojo.graphics-intro.my-finish-window
  (:import java.awt.event.WindowAdapter
           java.awt.event.WindowEvent))

(defn add-window-listener [g]
  (.addWindowListener g (proxy [WindowAdapter] []
                          (windowClosing [^WindowEvent e]
                            (System/exit 0)))))
