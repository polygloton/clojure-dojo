(ns clojure-dojo.reasoned-schemer.test.members-only
  (:refer-clojure :exclude [==])
  (:use
    [clojure.test :only [deftest]]
    [clojure-dojo.reasoned-schemer.test.test-helper :only [test-eq?]]
    [clojure.core.logic :exclude [is firsto resto conso emptyo membero]]
    [clojure-dojo.reasoned-schemer.members]))

(deftest ^:reasoned-schemer members-only

  (test-eq? 1 '(tofu d peas e)
    (mem 'tofu '(a b tofu d peas e)))

  (test-eq? 2 false
    (mem 'tofu '(a b peas d peas e)))

  (test-eq? 3 '((tofu d peas e))
    (run* [out]
      (== (mem 'tofu '(a b tofu d peas e)) out)))

  (test-eq? 4 '(peas e)
    (mem 'peas
      (mem 'tofu '(a b tofu d peas e))))

  (test-eq? 5 '(tofu d tofu e)
    (mem 'tofu
      (mem 'tofu '(a b tofu d tofu e))))

  (test-eq? 6 '(tofu e)
    (mem 'tofu
      (rest (mem 'tofu '(a b tofu d tofu e)))))

  (test-eq? 10 '((tofu d tofu e))
    (run 1 [out]
      (fresh [x]
        (memo 'tofu (list 'a 'b x 'd 'tofu 'e) out))))

  (test-eq? 11 '((tofu d tofu e))
    (run 1 [out]
      (fresh [x]
        (memo 'tofu (list 'a 'b x 'd 'tofu 'e) out))))

  (test-eq? 12 '(tofu)
    (run* [r]
      (memo r
        '(a b tofu d tofu e)
        '(tofu d tofu e))))

  (test-eq? 13 '(true)
    (run* [q]
      (memo 'tofu '(tofu e) '(tofu e))
      (== true q)))

  (test-eq? 14 '()
    (run* [q]
      (memo 'tofu '(tofu e) '(tofu))
      (== true q)))

  (test-eq? 15 '(tofu)
    (run* [x]
      (memo 'tofu '(tofu e) (list x 'e))))

  (test-eq? 16 '()
    (run* [x]
      (memo 'tofu '(tofu e) (list 'peas x))))

  (test-eq? 17 '((tofu d tofu e) (tofu e))
    (run* [out]
      (fresh [x]
        (memo 'tofu (list 'a 'b x 'd 'tofu 'e) out))))

  (test-eq? 18
    (list
      '_.0
      '_.0
      (llist 'tofu '_.0)
      (llist '_.0 'tofu '_.1)
      (llist '_.0 '_.1 'tofu '_.2)
      (llist '_.0 '_.1 '_.2 'tofu '_.3)
      (llist '_.0 '_.1 '_.2 '_.3 'tofu '_.4)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 'tofu '_.5)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 '_.5 'tofu '_.6)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 '_.5 '_.6 'tofu '_.7)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 '_.5 '_.6 '_.7 'tofu '_.8)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4 '_.5 '_.6 '_.7 '_.8 'tofu '_.9))
    (run 12 [z]
      (fresh [u]
        (memo 'tofu (llist 'a 'b 'tofu 'd 'tofu 'e z) u))))

  (test-eq? 23 '(a b d peas e)
    (rember 'peas '(a b peas d peas e)))

  (test-eq? 30 '((a b d peas e))
    (run 1 [out]
      (fresh [y]
        (rembero 'peas (list 'a 'b y 'd 'peas 'e) out))))

  (test-eq? 31
    '((b a d _.0 e)
      (a b d _.0 e)
      (a b d _.0 e)
      (a b d _.0 e)
      (a b _.0 d e)
      (a b e d _.0)
      (a b _.0 d _.1 e))
    (run* [out]
      (fresh [y z]
        (rembero y (list 'a 'b y 'd z 'e) out))))

  (test-eq? 49
    '((d d)
      (d d)
      (_.0 _.0)
      (e e))
    (run* [r]
      (fresh [y z]
        (rembero y (list y 'd z 'e) (list y 'd 'e))
        (== (list y z) r))))

  (test-eq? 57
    (list
      '_.0
      '_.0
      '_.0
      '_.0
      '_.0
      '()
      (llist '_.0 '_.1)
      (list '_.0)
      (llist '_.0 '_.1 '_.2)
      (list '_.0 '_.1)
      (llist '_.0 '_.1 '_.2 '_.3)
      (list '_.0 '_.1 '_.2)
      (llist '_.0 '_.1 '_.2 '_.3 '_.4))
    (run 13 [w]
      (fresh [y z out]
        (rembero y (llist 'a 'b y 'd z w) out))))

  (test-eq? 69 '(d)
    (run* [r]
      (== 'd r)
      (surpriseo r)))

  (test-eq? 70 '(_.0)
    (run* [r]
      (surpriseo r)))

  (test-eq? 71 '(b)
    (run* [r]
      (surpriseo r)
      (== 'b r)))
)
