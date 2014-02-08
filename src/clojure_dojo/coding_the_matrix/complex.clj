(ns clojure-dojo.coding-the-matrix.complex
  (:import [org.apache.commons.math3.complex Complex]))

(defn complex
  ([real-num]
     (condp = (class real-num)
       Long (complex (double real-num) 0.0)
       Double (complex real-num 0.0)
       Complex real-num))
  ([real-num imag-num]
     (Complex. real-num imag-num)))

(defn imaginary
  ([] Complex/I)
  ([imag-num] (complex 0.0 imag-num)))

(def i imaginary)

(defmacro defoper [oper-sym]
  `(defn ~oper-sym
     ([n1# n2#]
        (~(symbol (str "." oper-sym))
         (complex n1#)
         (complex n2#)))
     ([n1# n2# & more#]
        (reduce ~oper-sym (~oper-sym n1# n2#) more#))))

(defoper multiply)

(defoper divide)

(defoper add)

(defoper subtract)

(defn square [n1]
  (let [complex-n1 (complex n1)]
    (.multiply complex-n1 complex-n1)))

(defn pow [base exp]
  (if (neg? exp)
    (.reciprocal (pow base (- exp)))
    (loop [n base
           exp exp]
      (condp = exp
        0 1
        1 n
        (recur (multiply n base) (dec exp))))))

(defn pow+ [n exp]
  (.pow (complex n)
        (complex exp)))

(defn pow-i [exp]
  (case (mod exp 4)
    0 (complex 1.0 0.0)
    1 (complex 0.0 1.0)
    2 (complex -1.0 0.0)
    3 (complex 0.0 -1.0)))

(defn sqrt [n]
  (.sqrt (complex n)))

(defn number->str [number]
  (if (== number (.intValue number))
    (format "%d" (.intValue number))
    (format "%s" number)))

(defn imaginary->str [number]
  (case number
    0.0 "0"
    1.0 "i"
    -1.0 "-i"
    (str (number->str number) "i")))

(defn complex->str [c]
  (let [real-num (.getReal c)
        imag-num (.getImaginary c)]
    (cond
     (and (zero? real-num) (zero? imag-num))
     "0"

     (zero? real-num)
     (imaginary->str imag-num)
               
     (zero? imag-num)
     (number->str real-num)

     :else
     (str (number->str real-num) " + " (imaginary->str imag-num)))))

(defmethod clojure.core/print-method Complex [c ^java.io.Writer w]
  (.write w (complex->str c)))
