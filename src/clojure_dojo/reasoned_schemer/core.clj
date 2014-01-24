(ns clojure-dojo.reasoned-schemer.core
  (:refer-clojure :exclude [== identity])
  (:use
    [clojure.core.logic :exclude [firsto resto conso emptyo membero]]))

; https://github.com/clojure/core.logic/wiki/Differences-from-The-Reasoned-Schemer

(defn teacupo [x]
  (conde
    ((== 'tea x) s#)
    ((== 'cup x) s#)))

(defn firsto [p a]
  (fresh [d]
    (== (lcons a d) p)))

(defn resto [p d]
  (fresh [a]
    (== (lcons a d) p)))

(defn conso [a d p]
  (== (lcons a d) p))

(defn emptyo [x]
  (== '() x))

(defn eqo [x y]
  (== x y))

(defn pair? [x]
  (boolean
    (or (lcons? x) (and (coll? x) (seq x)))))

(defn pairo [p]
  (fresh [a d]
    (conso a d p)))

(defn listo [l]
  (conde
    ((emptyo l) succeed)
    ((pairo l)
      (fresh [d]
        (resto l d)
        (listo d)))))

(defn lolo [l]
  (conde
    ((emptyo l) s#)
    ((fresh [a]
      (firsto l a)
      (listo a))
    (fresh [d]
      (resto l d)
      (lolo d)))))

(defn twinso [s]
  (fresh [x y]
    (conso x y s)
    (conso x '() y)))

(defn loto [l]
  (conde
    ((emptyo l) s#)
    ((fresh [a]
      (firsto l a)
      (twinso a))
    (fresh [d]
      (resto l d)
      (loto d)))))

(defn listofo [predo l]
  (conde
    ((emptyo l) succeed)
    ((fresh [a]
       (firsto l a)
       (predo a))
      (fresh [d]
        (resto l d)
        (listofo predo d)))))

(defn eq-first? [l x]
  (= (first l) x))

(defn member? [x l]
  (cond
    (empty? l) false
    (eq-first? l x) true
    :else (member? x (rest l))))

(defn eq-firsto [l x]
  (firsto l x))

(defn membero [x l]
  (conde
    ((eq-firsto l x) succeed)
    ((fresh [d]
      (resto l d)
      (membero x d)))))

(defn identity [l]
  (run* [y]
    (membero y l)))

(defn pmembero_1 [x l]
  (conde
    ((eq-firsto l x) (resto l '()))
    ((fresh [d]
       (resto l d)
       (pmembero_1 x d)))))

(defn pmembero_2 [x l]
  (conde
    ((eq-firsto l x) (resto l '()))
    ((eq-firsto l x)
     (fresh [a d]
       (resto l (llist a d))))
    ((fresh [d]
      (resto l d)
      (pmembero_2 x d)))))

(defn pmembero_3 [x l]
  (conde
    ((eq-firsto l x)
      (fresh [a d]
        (resto l (llist a d))))
    ((eq-firsto l x) (resto l '()))
    ((fresh [d]
      (resto l d)
      (pmembero_3 x d)))))

(defn first-value [l]
  (run 1 [y]
    (membero y l)))

(defn memberrevo [x l]
  (conde
    ((fresh [d]
        (resto l d)
        (memberrevo x d)))
    ((eq-firsto l x))))
