(ns colojure-dojo.computation.simple
  (:refer-clojure :exclude [boolean]))

(defprotocol SimpleSyntax
  (->str [_])
  (reducible? [_])
  (reduce [_ _])
  (build [_] [_ _] [_ _ _] [_ _ _ _]))

(defprotocol SimpleMachine
  (step [_])
  (run [_]))

(defmacro def-syntax-print-method [syntax-type]
  `(defmethod clojure.core/print-method ~syntax-type [s# ^java.io.Writer w#]
     (.write w# (->str s#))))

(defmacro def-syntax [name args & methods]
  (let [syntax-type (gensym name)]
    `(do
       (defrecord ~syntax-type ~args
         SimpleSyntax
         ~@methods
         ~(case (count args)
            0 `(build [s#]
                      (new ~syntax-type))
            1 `(build [s# v1#]
                      (new ~syntax-type v1#))
            2 `(build [s# v1# v2#]
                      (new ~syntax-type v1# v2#))
            3 `(build [s# v1# v2# v3#]
                      (new ~syntax-type v1# v2# v3#))))
       (def-syntax-print-method ~syntax-type)
       (defn ~name ~args
         (new ~syntax-type ~@args)))))

(def-syntax number [value]
  (->str [s] (-> s :value str))
  (reducible? [_] false)
  (reduce [s env] [s env]))

(def-syntax boolean [value]
  (->str [s] (-> s :value str))
  (reducible? [_] false)
  (reduce [s env] [s env]))

(defn infix-syntax->str [syntax op-char]
  (format "%s %c %s"
          (-> syntax :left ->str)
          op-char
          (-> syntax :right ->str)))

(defn reduce-infix-syntax [syntax env build-fn op-fn]
  (let [left (:left syntax)
        right (:right syntax)]
    (cond
     (reducible? left) (build syntax (reduce left env) right)
     (reducible? right) (build syntax left (reduce right env))
     :else (build-fn (op-fn (:value left) (:value right))))))

(def-syntax add [left right]
  (->str [s] (infix-syntax->str s \+))
  (reducible? [_] true)
  (reduce [s env] [(reduce-infix-syntax s env number +)
                   env]))

(def-syntax multiply [left right]
  (->str [s] (infix-syntax->str s \*))
  (reducible? [_] true)
  (reduce [s env] [(reduce-infix-syntax s env number *)
                   env]))

(def-syntax less-than [left right]
  (->str [s] (infix-syntax->str s \<))
  (reducible? [_] true)
  (reduce [s env] [(reduce-infix-syntax s env boolean <)
                   env]))

(def-syntax variable [name]
  (->str [s] (-> s :name str))
  (reducible? [_] true)
  (reduce [s env] (get env (:name s))))

(def-syntax do-nothing []
  (->str [_] "do-nothing")
  (reducible? [_] false)
  (reduce [s env] [s env]))

(def-syntax assign [name expression]
  (->str [s] (format "%s %c %s" name \= (->str (:expression s))))
  (reducible? [_] true)
  (reduce [s environment]
          (let [expression (:expression s)
                name (:name s)]
            (if (reducible? expression)
              [(build s name (reduce expression environment)) environment]
              [(do-nothing) (merge environment {name expression})]))))

(def-syntax iff [condition consequence alternative]
  (->str [s]  (let [condition (:condition s)
                    consequence (:consequence s)
                    alternative (:alternative s)]
                (format "if (%s) { %s } else { %s }"
                        (->str condition)
                        (->str consequence)
                        (->str alternative))))
  (reducible? [_] true)
  (reduce [s environment]
          (let [condition (:condition s)
                consequence (:consequence s)
                alternative (:alternative s)]
            (cond
             (reducible? condition)
             [(build s (reduce condition environment) consequence alternative)
              environment]

             (= condition (boolean true))
             [consequence environment]

             (= condition (boolean false))
             [alternative environment]))))

(defn print-statement [machine]
  (println (format "%s, %s"
                   (-> machine :statement deref ->str)
                   (-> machine :environment deref))))

(defrecord SimpleMutatingMachine [statement environment]
  SimpleMachine
  (step [m] (let [environment (:environment m)
                  statement (:statement m)
                  [new-statement new-environment] (reduce @statement @environment)]
              (reset! statement new-statement)
              (reset! environment new-environment)))
  (run [m]
    (while (-> m :statement deref reducible?)
             (print-statement m)
             (step m))
    (print-statement m)))

(defn machine [statement environment]
  (SimpleMutatingMachine. (atom statement) (atom environment)))
