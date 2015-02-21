(ns graphics.frames
  (:require [clojure.core.async :as a]
            [graphics.common :refer :all])
  (:import java.awt.Frame
           java.awt.Graphics
           java.awt.Graphics2D
           java.awt.event.WindowAdapter
           java.awt.event.WindowEvent))

(defn enable-close-button [^Frame frame]
  (.addWindowListener frame (proxy [WindowAdapter] []
                              (windowClosing [^WindowEvent e]
                                (.dispose frame))))
  frame)

(def-window-setter set-size Size [x y])

(def-frame-setter set-title Title [s])

(def-window-setter set-visible Visible [visible?])

(defn paint-ch [& {:keys [title size]
                   :or {title "UNTITLED"
                        size [300 200]}}]
  (let [c1 (a/chan)
        c2 (doto (a/chan 1)
             (a/>!! (constantly nil))) ;; do nothing on first paint
        frame (proxy [Frame] []
                (paint [^Graphics g]
                  (when-let [f (a/<!! c2)]
                    (f (cast Graphics2D g)))))]
    (a/go-loop []
      (if-let [f (a/<! c1)]
        (do
          (a/>! c2 f)
          (.repaint frame)
          (recur))
        (a/close! c2)))
    (-> frame
        (enable-close-button)
        (set-title title)
        (set-size (first size) (last size))
        (set-visible true))
    c1))

(defn paint-fn [f & {:keys [title size]}]
  (doto (paint-ch :title title :size size)
    (a/>!! f)))
