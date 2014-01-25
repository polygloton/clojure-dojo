(ns clojure-dojo.coding-the-matrix.complex
  (:import [org.apache.commons.math3.complex Complex]))

(defn complex
  ([real]
     (Complex. real 0.0))
  ([real imaginary]
     (Complex. real imaginary)))

(defn imaginary
  ([] Complex/I)
  ([imaginary] (complex 0.0 imaginary)))

(def i imaginary)

(defmacro def-oper [name fun]
  `(do
     (defmulti ~name (fn [operand# _#]
                       (class operand#)))
     (defmethod ~name Complex [operand# complex-num#]
       (~fun operand# complex-num#))
     (defmethod ~name Double [operand# complex-num#]
       (~fun (complex operand#)
             complex-num#))
     (defmethod ~name Long [operand# complex-num#]
       (~fun (complex (.doubleValue operand#))
             complex-num#))))

(def-oper multiply #(.multiply %1 %2))

(def-oper add #(.add %1 %2))

(def-oper subtract #(.subtract %1 %2))

(defn number->str [number]
  (if (== number (.intValue number))
    (format "%d" (.intValue number))
    (format "%s" number)))

(defmethod clojure.core/print-method Complex [c ^java.io.Writer w]
  (.write w (let [real-num (.getReal c)
                  imag-num (.getImaginary c)]
              (cond
               (and (zero? real-num) (zero? imag-num))
               "0"
               
               (zero? real-num)
               (format "%si"
                       (number->str imag-num))
               
               (zero? imag-num)
               (format "%s"
                       (number->str real-num))

               :else
               (format "%s+%si"
                       (number->str real-num)
                       (number->str imag-num))))))
