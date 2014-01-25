(ns clojure-dojo.coding-the-matrix.test.complex
  (:require [clojure.test :refer :all]
            [clojure-dojo.coding-the-matrix.complex :refer :all]))

(deftest ^:coding-the-matrix test-complex

  (testing "1.2 Complex numbers in *Clojure*"

    (is (= (org.apache.commons.math3.complex.Complex. 0.0 3.0)
           (complex 0 3)
           (complex 0.0 3.0)
           (imaginary 3)
           (imaginary 3.0)
           (i 3)))

    (is (= "3i"
           (complex->str (i 3))))

    (is (= org.apache.commons.math3.complex.Complex/I
           (complex 0 1)
           (i)))

    (is (= "i"
           (complex->str (i))))

    (is (= (complex 1 3)
           (add 1 (i 3))))

    (is (= "1 + 3i"
           (complex->str (complex 1 3))))

    (is (= (complex 11 23)
           (add (complex 1 3) (complex 10 20))
           (add (add 1 (i 3)) (add 10 (i 20)))))

    (is (= "11 + 23i"
           (complex->str (complex 11 23))))

    (let [x (add 1 (i 3))]
      (is (= (complex -9 0)
             (pow (subtract 1 x) 2)
             (square (subtract 1 x))))
      (is (= 1.0
             (.getReal x)))
      (is (= 3.0
             (.getImaginary x))))
    
    (is (= "-9"
           (complex->str (complex -9 0))))

    (is (= (complex 1 6)
           (add 1 (multiply (i 2) 3))))

    (is (= (complex -36 0)
           (multiply 4 (pow (i 3) 2))
           (multiply 4 (square (i 3)))))))
