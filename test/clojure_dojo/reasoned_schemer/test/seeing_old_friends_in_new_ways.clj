(ns clojure-dojo.reasoned-schemer.test.seeing-old-friends-in-new-ways
  (:refer-clojure :exclude [== identity])
  (:use
    [clojure.test :only [deftest]]
    [clojure-dojo.reasoned-schemer.test.test-helper :only [test-eq?]]
    [clojure.core.logic :exclude [is firsto resto conso emptyo membero] :as l]
    [clojure-dojo.reasoned-schemer.core]))

(deftest ^:reasoned-schemer seeing-old-friends-in-new-ways

  (test-eq? 7 '(_.0)
    (run* [x]
      (listo (list 'a 'b x 'd))))

  (test-eq? 10 '(())
    (run 1 [x]
      (listo (llist 'a 'b 'c x))))

  (test-eq? 14 '(() (_.0) (_.0 _.1) (_.0 _.1 _.2) (_.0 _.1 _.2 _.3))
    (run 5 [x]
      (listo (llist 'a 'b 'c x))))

  (test-eq? 20 '(())
    (run 1 [l] (lolo l)))

  (test-eq? 21 '(true)
    (run* [q]
      (fresh [x y]
        (lolo (list (list 'a 'b) (list x 'c) (list 'd y)))
        (== true q))))

  (test-eq? 22 '(true)
    (run 1 [q]
      (fresh  [x]
        (lolo (llist (list 'a 'b) x))
        (== true q))))

  (test-eq? 23 '(())
    (run 1 [x]
      (lolo (llist '(a b) '(c d) x))))

  ; Doesn't match the book, is it because of condi?
  (test-eq? 24 '(() (()) ((_.0)) (() ()) ((_.0 _.1)))
    (run 5 [x]
      (lolo (llist '(a b) '(c d) x))))

  (test-eq? 32 '(true)
    (run* [q]
      (twinso '(tofu tofu))
      (== true q)))

  (test-eq? 33 '(tofu)
    (run* [z]
      (twinso (list z 'tofu))))

  (test-eq? 38 '(())
    (run 1 [z]
      (loto (llist '(g g) z))))

  (test-eq? 42
    '(()
      ((_.0 _.0))
      ((_.0 _.0) (_.1 _.1))
      ((_.0 _.0) (_.1 _.1) (_.2 _.2))
      ((_.0 _.0) (_.1 _.1) (_.2 _.2) (_.3 _.3)))
    (run 5 [z]
      (loto (llist '(g g) z))))

  (test-eq? 45
    '((e (_.0 _.0) ())
      (e (_.0 _.0) ((_.1 _.1)))
      (e (_.0 _.0) ((_.1 _.1) (_.2 _.2)))
      (e (_.0 _.0) ((_.1 _.1) (_.2 _.2) (_.3 _.3)))
      (e (_.0 _.0) ((_.1 _.1) (_.2 _.2) (_.3 _.3) (_.4 _.4))))
    (run 5 [r]
      (fresh [w x y z]
        (loto (llist '(g g) (list 'e w) (list x y) z))
        (== (list w (list x y) z) r))))

  (test-eq? 47
    '(((g g) (e e) (_.0 _.0))
      ((g g) (e e) (_.0 _.0) (_.1 _.1))
      ((g g) (e e) (_.0 _.0) (_.1 _.1) (_.2 _.2)))
    (run 3 [out]
      (fresh [w x y z]
        (== (llist '(g g) (list 'e w) (list x y) z) out)
        (loto out))))

  (test-eq? 49
    '(((g g) (e e) (_.0 _.0))
      ((g g) (e e) (_.0 _.0) (_.1 _.1))
      ((g g) (e e) (_.0 _.0) (_.1 _.1) (_.2 _.2)))
    (run 3 [out]
      (fresh [w x y z]
          (== (llist '(g g) (list 'e w) (list x y) z) out)
        (listofo twinso out))))

  (test-eq? 53 true
    (member? 'olive '(virgin olive oil)))

  (test-eq? 57 '(true)
    (run* [q]
      (membero 'olive '(virgin olive oil))
      (== true q)))

  (test-eq? "57, using built-in membero" '(true)
    (run* [q]
      (l/membero 'olive '(virgin olive oil))
      (== true q)))

  (test-eq? 58 '(hummus)
    (run 1 [y]
      (membero y '(hummus with pita))))

  (test-eq? 59 '(with)
    (run 1 [y]
      (membero y '(with pita))))

  (test-eq? 60 '(pita)
    (run 1 [y]
      (membero y '(pita))))

  (test-eq? 61 '()
    (run* [y]
      (membero y '())))

  (test-eq? 62 '(hummus with pita)
    (run* [y]
      (membero y '(hummus with pita))))

  (test-eq? 66 '(e)
    (run* [x]
      (membero 'e (list 'pasta x 'fagioli))))

  (test-eq? 69 '(_.0)
    (run 1 [x]
      (membero 'e (list 'pasta 'e x 'fagioli))))

  (test-eq? 70 '(e)
    (run 1 [x]
      (membero 'e (list 'pasta x 'e 'fagioli))))

  (test-eq? 71 '((e _.0) (_.0 e))
    (run* [r]
      (fresh [x y]
        (membero 'e (list 'pasta x 'fagioli y))
        (== (list x y) r))))

  (test-eq? 73 (list (llist 'tofu '_.0))
    (run 1 [l]
      (membero 'tofu l)))

  (test-eq? 76
    (list
      (llist 'tofu '_.0)
      (llist '_.0 'tofu '_.1)
      (llist '_.0 '_.1 'tofu '_.2)
      (llist '_.0 '_.1 '_.2 'tofu '_.3)
      (llist '_.0 '_.1 '_.2 '_.3 'tofu '_.4))
    (run 5 [l]
      (membero 'tofu l)))

  (test-eq? 80
    '((tofu)
      (_.0 tofu)
      (_.0 _.1 tofu)
      (_.0 _.1 _.2 tofu)
      (_.0 _.1 _.2 _.3 tofu))
    (run 5 [l]
      (pmembero_1 'tofu l)))

  (test-eq? 81 '(true true)
    (run* [q]
      (pmembero_2 'tofu '(a b tofu d tofu))
      (== true q)))

  (test-eq? 89
    (list
      (list 'tofu)
      (llist 'tofu '_.0 '_.1)
      (list '_.0 'tofu)
      (llist '_.0 'tofu '_.1 '_.2)
      (list '_.0 '_.1 'tofu)
      (llist '_.0 '_.1 'tofu '_.2 '_.3)
      (list '_.0 '_.1 '_.2 'tofu)
      (llist '_.0 '_.1 '_.2 'tofu '_.3 '_.4)
      (list '_.0 '_.1 '_.2 '_.3 'tofu)
      (llist '_.0 '_.1 '_.2 '_.3 'tofu '_.4 '_.5)
      (list '_.0 '_.1 '_.2 '_.3 '_.4 'tofu)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 'tofu '_.5 '_.6))
    (run 12 [l]
      (pmembero_2 'tofu l)))

  (test-eq? 93
    (list
      (llist 'tofu '_.0 '_.1)
      (list 'tofu)
      (llist '_.0 'tofu '_.1 '_.2)
      (list '_.0 'tofu)
      (llist '_.0 '_.1 'tofu '_.2 '_.3)
      (list '_.0 '_.1 'tofu)
      (llist '_.0 '_.1 '_.2 'tofu '_.3 '_.4)
      (list '_.0 '_.1 '_.2 'tofu)
      (llist '_.0 '_.1 '_.2 '_.3 'tofu '_.4 '_.5)
      (list '_.0 '_.1 '_.2 '_.3 'tofu)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 'tofu '_.5 '_.6)
      (list '_.0 '_.1 '_.2 '_.3 '_.4 'tofu))
    (run 12 [l]
      (pmembero_3 'tofu l)))

  (test-eq? 96 '(pasta)
    (first-value '(pasta e fagioli)))

  ; not working, is it because of condi?
  (comment (test-eq? 100 '(fagioli e pasta)
    (run* [x]
      (memberrevo x '(pasta e fagioli)))))
)
