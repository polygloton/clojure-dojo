# clojure-dojo

This is my personal space for practicing and learning how to program,
specifically using Clojure.  This is my personal coding dojo.

## Introduction to Computer Graphics

In support of Coding The Matrix, I am reading [Introduction to Computer Graphics](http://www.springer.com/computer/image+processing/book/978-1-84628-848-7).

## Coding The Matrix

I am working on [Coding The Matrix](http://codingthematrix.com/).  Of course I am translating the Python code into Clojure as I go.

## Understanding Computation

I am working through [Understanding Computation](http://computationbook.com/).
As I read the book, I am translating the examples from Ruby to Clojure.

The following code...
```clojure
;; in clojure-dojo.computation.small-step

(run (machine (while (less-than (variable :x) (number 5))
                     (assign :x (multiply (variable :x) (number 3))))
              {:x (number 1)}))

```

Prints:
```
while (:x < 5) { :x = :x * 3 }, {:x 1}
if (:x < 5) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 1}
if (1 < 5) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 1}
if (true) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 1}
:x = :x * 3; while (:x < 5) { :x = :x * 3 }, {:x 1}
:x = 1 * 3; while (:x < 5) { :x = :x * 3 }, {:x 1}
:x = 3; while (:x < 5) { :x = :x * 3 }, {:x 1}
do-nothing; while (:x < 5) { :x = :x * 3 }, {:x 3}
while (:x < 5) { :x = :x * 3 }, {:x 3}
if (:x < 5) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 3}
if (3 < 5) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 3}
if (true) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 3}
:x = :x * 3; while (:x < 5) { :x = :x * 3 }, {:x 3}
:x = 3 * 3; while (:x < 5) { :x = :x * 3 }, {:x 3}
:x = 9; while (:x < 5) { :x = :x * 3 }, {:x 3}
do-nothing; while (:x < 5) { :x = :x * 3 }, {:x 9}
while (:x < 5) { :x = :x * 3 }, {:x 9}
if (:x < 5) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 9}
if (9 < 5) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 9}
if (false) { :x = :x * 3; while (:x < 5) { :x = :x * 3 } } else { do-nothing }, {:x 9}
do-nothing, {:x 9}
```

### The Reasoned Schemer

I implemented many of the examples from
 [The Reasoned Schemer](http://mitpress.mit.edu/books/reasoned-schemer)
using core.logic.  I gave it up when differences between the mini-kanren and
core.logic became problematic.  Some day I'd like to start again using Jim Duey's
implementation of [mini-kanren](https://github.com/jduey/mini-kanren).

Exercise the code with these tests.

```bash
lein test :reasoned-schemer
```


## License

Copyright © 2013-2014 Stephen Sloan

Licensed under the MIT License, see LICENSE.txt
