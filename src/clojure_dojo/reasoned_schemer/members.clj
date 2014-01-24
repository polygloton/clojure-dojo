(ns clojure-dojo.reasoned-schemer.members
  (:refer-clojure :exclude [==])
    (:use
      [clojure-dojo.reasoned-schemer.core :only [eq-first? eq-firsto emptyo resto conso]]
      [clojure.core.logic :exclude [firsto resto conso emptyo membero]]))

(defn mem [x l]
  (cond
    (empty? l) false
    (eq-first? l x) l
    :else (mem x (rest l))))

(defn memo [x l out]
  (conde
    ((eq-firsto l x) (== l out))
    ((fresh [d]
       (resto l d)
       (memo x d out)))))

(defn rember [x l]
  (cond
    (empty? l) '()
    (eq-first? l x) (rest l)
    :else (cons (first l)
            (rember x (rest l)))))

(defn rembero [x l out]
  (conde
    ((emptyo l) (== '() out))
    ((eq-firsto l x) (resto l out))
    ((fresh [a d res]
       (conso a d l)
       (rembero x d res)
       (conso a res out)))))

(defn surpriseo [s]
  (rembero s '(a b c) '(a b c)))
