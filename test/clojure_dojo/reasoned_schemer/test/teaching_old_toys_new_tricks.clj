(ns clojure-dojo.reasoned-schemer.test.teaching-old-toys-new-tricks
  (:refer-clojure :exclude [== identity])
  (:use
    [clojure.test :only [deftest]]
    [clojure-dojo.reasoned-schemer.test.test-helper :only [test-eq?]]
    [clojure.core.logic :exclude [is firsto resto conso emptyo membero] :as l]
    [clojure-dojo.reasoned-schemer.core]))

(deftest ^:reasoned-schemer teaching-old-toys-new-tricks

  (test-eq? 1 'c
    (let [x (fn [a] a)
          y 'c]
      (x y)))

  (test-eq? 2 '((_.0 _.1))
    (run* [r]
      (fresh [y x]
        (== (list x y) r))))

  (test-eq? 3 '((_.0 _.1))
    (run* [r]
      (fresh [v w]
        (== (let [x v
                  y w]
              (list x y)) r))))

  (test-eq? 4 'grape
    (first '(grape raisin pear)))

  (test-eq? 5 'a
    (first '(a c o r n)))

  (test-eq? 6 '(a)
    (run* [r]
      (firsto '(a c o r n) r)))

  (test-eq? 7 '(true)
    (run* [q]
      (firsto '(a c o r n) 'a)
      (== true q)))

  (test-eq? 8 '(pear)
    (run* [r]
      (fresh [x y]
        (firsto (list r y) x)
        (== 'pear x))))

  (test-eq? "8, with built-in firsto" '(pear)
    (run* [r]
      (fresh [x y]
        (l/firsto (list r y) x)
        (== 'pear x))))

  (test-eq? 10 '(grape a)
    (cons
      (first '(grape raisin pear))
      (first '((a) (b) (c)))))

  (test-eq? 11 '((grape a))
    (run* [r]
      (fresh [x y]
        (firsto '(grape raisin pear) x)
        (firsto '((a) (b) (c)) y)
        (== (lcons x y) r))))

  (test-eq? 13 '(raisin pear)
    (rest '(grape raisin pear)))

  (test-eq? 14 'c
    (first (rest '(a c o r n))))

  (test-eq? 15 '(c)
    (run* [r]
      (fresh [v]
        (resto '(a c o r n) v)
        (firsto v r))))

  (test-eq? "15, with built-in resto" '(c)
    (run* [r]
      (fresh [v]
        (l/resto '(a c o r n) v)
        (firsto v r))))

  (test-eq? 17 '((raisin pear) a)
    (cons
      (rest '(grape raisin pear))
      (first '((a) (b) (c)))))

  (test-eq? 18 '(((raisin pear) a))
    (run* [r]
      (fresh [x y]
        (resto '(grape raisin pear) x)
        (firsto '((a) (b) (c)) y)
        (== (lcons x y) r))))

  (test-eq? 19 '(true)
    (run* [q]
      (resto '(a c o r n) '(c o r n))
      (== true q)))

  (test-eq? 20 '(o)
    (run* [x]
      (resto '(c o r n) (list x 'r 'n))))

  (test-eq? 21 '((a c o r n))
    (run* [l]
      (fresh [x]
        (resto l '(c o r n))
        (firsto l x)
        (== 'a x))))

  (test-eq? 22 '(((a b c) d e))
    (run* [l]
      (conso '(a b c) '(d e) l)))

  (test-eq? "22, with built-in conso" '(((a b c) d e))
    (run* [l]
      (l/conso '(a b c) '(d e) l)))

  (test-eq? 23 '(d)
    (run* [x]
      (conso x '(a b c) '(d a b c))))

  (test-eq? 24 '((e a d c))
    (run* [r]
      (fresh [x y z]
        (== (list 'e 'a 'd x) r)
        (conso y (list 'a z 'c) r))))

  (test-eq? 25 '(d)
    (run* [x]
      (conso x (list 'a x 'c) (list 'd 'a x 'c))))

  (test-eq? 26 '((d a d c))
    (run* [l]
      (fresh [x]
        (== (list 'd 'a x 'c) l)
        (conso x (list 'a x 'c) l))))

  (test-eq? 27 '((d a d c))
    (run* [l]
      (fresh [x]
        (conso x (list 'a x 'c) l)
        (== (list 'd 'a x 'c) l))))

  (test-eq? 29 '((b e a n s))
    (run* [l]
      (fresh [d x y w s]
        (conso w '(a n s) s)
        (resto l s)
        (firsto l x)
        (== 'b x)
        (resto l d)
        (firsto d y)
        (== 'e y))))

  (test-eq? 30 false
    (empty? '(grape raisin pear)))

  (test-eq? 31 true
    (empty? '()))

  (test-eq? 32 '()
    (run* [q]
      (emptyo '(grape raisin pear))
      (== true q)))

  (test-eq? "32, with built-in emptyo" '()
    (run* [q]
      (l/emptyo '(grape raisin pear))
      (== true q)))

  (test-eq? 33 '(true)
    (run* [q]
      (emptyo '())
      (== true q)))

  (test-eq? "33, with built-in emptyo" '(true)
    (run* [q]
      (l/emptyo '())
      (== true q)))

  (test-eq? 34 '(())
    (run* [x]
      (emptyo x)))

  (test-eq? 36 false
    (= 'pear 'plum))

  (test-eq? 37 true
    (= 'plum 'plum))

  (test-eq? 38 '()
    (run* [q]
      (eqo 'pear 'plum)
      (== true q)))

  (test-eq? 39 '(true)
    (run* [q]
      (eqo 'plum 'plum)
      (== true q)))

  (test-eq? 43 true
    (pair? (lcons '(split) 'pea)))

  (test-eq? 44 false
    (pair? '()))

  (test-eq? 45 false
    (pair? 'pair))

  (test-eq? 46 false
    (pair? 'pear))

  (test-eq? 47 true
    (pair? '(pear)))

  (test-eq? 48 'pear
    (first '(pear)))

  (test-eq? 49 '()
    (rest '(pear)))

  (test-eq? 51 (llist '(split) 'pea)
    (lcons '(split) 'pea))

  (test-eq? 52 (list (llist '_.0 '_.1 'salad))
    (run* [r]
      (fresh [x y]
        (== (lcons x (lcons y 'salad)) r))))

  (test-eq? 54 '(true)
    (run* [q]
      (pairo (lcons q q))
      (== true q)))

  (test-eq? 55 '()
    (run* [q]
      (pairo '())
      (== true q)))

  (test-eq? 56 '()
    (run* [q]
      (pairo 'pair)
      (== true q)))

  (test-eq? 57 (list (llist '_.0 '_.1))
    (run* [x]
      (pairo x)))

  (test-eq? 58 '(_.0)
    (run* [r]
      (pairo (lcons r 'pear))))
)
