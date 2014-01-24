(ns clojure-dojo.reasoned-schemer.test.test-helper
  (:require
    [clojure.test :as t]))

(defmacro test-eq? [name expect & subject]
  `(t/testing ~name (t/is (= ~expect ~@subject))))
