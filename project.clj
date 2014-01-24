(defproject clojure-dojo "0.1.0-SNAPSHOT"
  :description "Personal clojure dojo for Stephen Sloan"
  :url "https://github.com/polygloton/clojure-dojo"
  :license {:name "MIT"
            :url "https://raw2.github.com/polygloton/clojure-dojo/master/LICENSE.txt"
            :distribution "manual"}
  :min-lein-version "2.0.0"
  :pendantic? :warn
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/core.logic "0.7.5"]
                 [org.apache.commons/commons-math3 "3.0"]]
  :test-selectors {:default (constantly false)
                   :reasoned-schemer :reasoned-schemer})
