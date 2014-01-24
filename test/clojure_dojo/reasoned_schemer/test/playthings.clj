(ns clojure-dojo.reasoned-schemer.test.playthings
  (:refer-clojure :exclude [==])
  (:use
    [clojure.test :only [deftest]]
    [clojure-dojo.reasoned-schemer.test.test-helper :only [test-eq?]]
    [clojure.core.logic :exclude [is firsto resto conso emptyo membero]]
    [clojure-dojo.reasoned-schemer.core :only [teacupo]]))

(deftest ^:reasoned-schemer playthings-test

  (test-eq? 10 '()
    (run* [q] fail))

  (test-eq? 11 '(true)
    (run* [q] (== true q)))

  (test-eq? 12 '()
    (run* [q]
      fail
      (== true q)))

  (test-eq? 14 '(true)
    (run* [q]
      succeed
      (== true q)))

  (test-eq? 16 '(corn)
    (run* [r]
      succeed
      (== 'corn r)))

  (test-eq? 17 '()
    (run* [r] fail (== 'corn r)))

  (test-eq? 18 '(false)
    (run* [q]
      succeed
      (== false q)))

  (test-eq? 22 '()
    (run* [x]
      (let [x false]
        (== true x))))

  (test-eq? 23 '(true)
    (run* [q]
      (fresh [x]
        (== true x)
        (== true q))))

  (test-eq? 26 '(true)
    (run* [q]
      (fresh [x]
        (== x true)
        (== true q))))

  (test-eq? 27 '(true)
    (run* [q]
      (fresh [x]
        (== x true)
        (== q true))))

  (test-eq? 28 '(_.0)
    (run* [x]
      succeed))

  (test-eq? 29 '(_.0)
    (run* [x]
      (let [x false]
        (fresh [x]
          (== true x)))))

  (test-eq? 30 '((_.0 _.1))
    (run* [r]
      (fresh [x y]
        (== (lcons x (lcons y '())) r))))

  (test-eq? 32 '((_.0 _.1 _.0))
    (run* [r]
      (fresh [x]
        (let [y x]
          (fresh [x]
            (== (cons y (cons x (cons y '()))) r))))))

  (test-eq? "32 with list" '((_.0 _.1 _.0))
    (run* [r]
      (fresh [x]
        (let [y x]
          (fresh [x]
            (== (list y x y) r))))))

  (test-eq? "32 with llist" (list (llist '_.0 '_.1 '_.0))
    (run* [r]
      (fresh [x]
        (let [y x]
          (fresh [x]
            (== (llist y x y) r))))))

  (test-eq? 33 '((_.0 _.1 _.0))
    (run* [r]
      (fresh [x]
        (let [y x]
          (fresh [x]
            (== (cons x (cons y (cons x '()))) r))))))

  (test-eq? 34 '()
    (run* [q]
      (== false q)
      (== true q)))

  (test-eq? 35 '(false)
    (run* [q]
      (== false q)
      (== false q)))

  (test-eq? 36 '(true)
    (run* [q]
      (let [x q]
        (== true x))))

  (test-eq? 37 '(_.0)
    (run* [r]
      (fresh [x]
        (== x r))))

  (test-eq? 38 '(true)
    (run* [q]
      (fresh [x]
        (== true x)
        (== x q))))

  (test-eq? 39 '(true)
    (run* [q]
      (fresh [x]
        (== x q)
        (== true x))))

  (test-eq? 40 '(false)
    (run* [q]
      (fresh [x]
        (== (= x q) q)))
    (run* [q]
      (let [x q]
        (fresh [q]
          (== (= x q) x)))))

  (test-eq? 41 false
    (cond
      false true
      :else false))

  (test-eq? 43 fail
    (cond
      false succeed
      :else fail))

  (test-eq? 47 '(olive oil)
    (run* [x]
      (conde
        ((== 'olive x) succeed)
        ((== 'oil x) succeed))))

  (test-eq? 49 '(olive)
    (run 1 [x]
      (conde
        ((== 'olive x) succeed)
        ((== 'oil x) succeed))))

  (test-eq? 50 '(olive _.0 oil)
    (run* [x]
      (conde
        ((== 'virgin x) fail)
        ((== 'olive x) succeed)
        (succeed succeed)
        ((== 'oil x) succeed))))

  (test-eq? 52 '(extra olive)
    (run 2 [x]
      (conde
        ((== 'extra x) succeed)
        ((== 'virgin x) fail)
        ((== 'olive x) succeed)
        ((== 'oil x) succeed))))

  (test-eq? 53 '((split pea))
    (run* [r]
      (fresh [x y]
        (== 'split x)
        (== 'pea y)
        (== (cons x (cons y '())) r))))

  (test-eq? 54 '((split pea) (navy bean))
    (run* [r]
      (fresh [x y]
        (conde
          ((== 'split x) (== 'pea y))
          ((== 'navy x) (== 'bean y)))
        (== (cons x (cons y '())) r))))

  (test-eq? 55 '((split pea soup) (navy bean soup))
    (run* [r]
      (fresh [x y]
        (conde
          ((== 'split x) (== 'pea y))
          ((== 'navy x) (== 'bean y)))
        (== (cons x (cons y (cons 'soup '()))) r))))

  (test-eq? 56 '(tea cup)
    (run* [x]
      (teacupo x)))

  (test-eq? 57 '((false true) (tea true) (cup true))
    (run* [r]
      (fresh [x y]
        (conde
          ((teacupo x) (== true y) succeed)
          ((== false x) (== true y)))
        (== (cons x (cons y '())) r))))

  (test-eq? 58 '((_.0 _.1) (_.0 _.1))
    (run* [r]
      (fresh [x y z]
        (conde
          ((== y x) (fresh [x] (== z x)))
          ((fresh [x] (== y x)) (== z x)))
        (== (cons y (cons z '())) r))))

  (test-eq? 59 '((false _.0) (_.0 false))
    (run* [r]
      (fresh [x y z]
        (conde
          ((== y x) (fresh [x] (== z x)))
          ((fresh [x] (== y x)) (== z x)))
        (== false x)
        (== (cons y (cons z '())) r))))

  (test-eq? 60 '(false)
    (run* [q]
      (let [a (== true q)
            b (== false q)]
        b)))

  (test-eq? 61 '(false)
    (run* [q]
      (let [a (== true q)
            b (fresh [x]
                (== x q)
                (== false x))
            c (conde
                ((== true q) succeed)
                ((== false q) succeed))]
        b)))
)
