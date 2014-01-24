(ns clojure-dojo.coding-the-matrix.complex
  (:import [org.apache.commons.math3.complex Complex]))

(defn complex
  ([real]
     (Complex. real 0.0))
  ([real imaginary]
     (Complex. real imaginary)))

(defn i
  ([] Complex/I)
  ([imaginary] (complex 0.0 imaginary)))

(defmulti multiply (fn [product _]
                     (class product)))

(defmethod multiply Complex [product complex-number]
  (.multiply complex-number
             product))

(defmethod multiply Double [product complex-number]
  (.multiply complex-number
            (complex product)))

(defmethod multiply Long [product complex-number]
  (.multiply (complex (.doubleValue product))
             complex-number))

(defmulti add (fn [addend _]
                 (class addend)))

(defmethod add Complex [addend complex-number]
  (.add complex-number
        addend))

(defmethod add Double [addend complex-number]
  (.add complex-number
        (complex addend)))

(defmethod add Long [addend complex-number]
  (.add complex-number
        (complex (.doubleValue addend))))

(defn number->str [number]
  (if (== number (.intValue number))
    (format "%d" (.intValue number))
    (format "%s" number)))

(defmethod clojure.core/print-method Complex [c ^java.io.Writer w]
  (.write w (condp == 0
                (.getReal c) (format "%si"
                                     (number->str (.getImaginary c)))
                (.getImaginary c) (format "%s"
                                          (number->str (.getReal c)))
                (format "%s + %si"
                        (number->str (.getReal c))
                        (number->str (.getImaginary c))))))
