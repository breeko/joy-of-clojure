; integers

42
-9

; floating point numbers
'1.1
'2.0

; rationals
(/ 22 7)
; rationals simplify
(/ 4 2)

; keywords
:hello
:?
:ThisIsTheNameOfaKeyword

; strings
"This is a string"

; characters
; written with a literal syntax
\a
\A
\u0042
\\

; collections

; lists
; lists are written with parenthesis
; when a list is evaluated, the first item of a list is resolved to a function, macro or special operator 
(+ 2 3 4)
; a form is any clojure object meant to be evaluated, including but not limited to list, vector, map, number, keyword and symbol

; vector
; like a list, a vector stores a series of values
; vectors have a literal syntax using square brackets and they can be heterogeneous
[1 2 :a :b :c]

; maps
; maps store unique keys and one value per key (also known as dictionaries and hashes)
; maps can be written using literal syntax with alternative keys and values inside curly braces
; empty map {} is not same as nil 
{:a 1 :b 2}
{}

; sets
; sets store zero or more unique items
; written using curly braces with a leading hash
; empty set #{} is not same as nil 
#{1 2 "three" :four 0x5}

; functions
; functions can be stored in vars, held in lists and other collection types, passed as arguments or returned
#{+ - * /}
(vector 1 2 3)

; var
; var is named by a symbole and holds a single value
; its value can be changed while the program is running
; var can be shadowed althogh shadowing does not change its original value
; most common way to define a var is def
(def x 42)
x
; vars don't require a value
(def y)
y

; anonymous functions
; anonymous functions can be defined using a special form
(fn [x y]
  (println "Making a set")
  #{x y})
; they can be called immediately after creation
((fn [x y]
   #{x y})
 1 2) ; #{1 2}

; you can bind anonymous functions to vars with def
(def make-set (fn [x y]
               #{x y}))
(make-set 1 2) ; #{1 2}
; or use defn macro
(defn make-set
  "Takes two values and makes a set from them"
  [x y]
  #{x y})
(make-set 1 2) ; #{1 2}

; functions with multiple arities
; functions allow for arity overloading
(defn make-set
  ([x] #{x})
  ([x y] #{x y}))
(make-set 1) ; #{1}
(make-set 1 2) ; #{1 2}
; functions that take a variable number of arguments uses the & symbol
; every symbol before the & is still bound
(defn arity2+ [first second & more]
  (vector first second more))
(arity2+ 1 2 3 4 5) ; [1 2 (3 4 5)]

; protocols
; protocols allow you to define a function signature without its implementation
(defprotocol Concatenatable
  (catenate [this other]))

; types can be extended to implement a protocol
(extend-type String
  Concatenatable
  (catenate [this other]
    (.concat this other)))

(catenate "a" "b")

(extend-type java.util.List
  Concatenatable
  (catenate [this other]
    (concat this other)))

(catenate [1 2 3] [4 5 6]) ;(1 2 3 4 5 6)



; defining a function and overloading operators
(defn r->l
  "performs operations from right to left"
  ([a op b]                 (op a b))
  ([a op b op2 c]           (op a (op2 b c)))
  ([a op b op2 c op3 d]     (op a (op2 b (op3 c d)))))

(defn l->r
  "performs operations from left to right"
  ([a op b]                 (op a b))
  ([a op b op2 c]           (op2 c (l->r a op b)))
  ([a op b op2 c op3 d]     (op3 d (l->r a op b op2 c))))

; variables can be defined with def
; dictionaries are written in curly braces with alternative keys and vals {key1 val1 key2 val2}
(def order {+ 0  - 0
            * 1  / 1})

(defn infix [a op1 b op2 c]
  "performs two operations on three numbers in the proper order"
  (if (< (get order op1) (get order op2))
    (r->l a op1 b op2 c)
    (l->r a op1 b op2 c)))

(infix 1 * 2 + 3) ; 5
(infix 1 + 2 * 3) ; 7

; apply can be to apply the first argument onto the second
(apply + [1,2,3,4,5,6,7,8,9,10])

; recursion, function overload
(defn sum-top-down
  "sums up positive numbers from x to 0"
  ([x] (sum-top-down 0 x))
  ([sum x]
   (if (pos? x)
     (recur (+ sum x) (dec x)) ; recur returns to the 
     sum)))

; anonymous functions
(defn sum-top-down
  "sums up positive numbers from x to 0"
  [x]
  ((fn [sum x]
     (if (pos? x)
       (recur (+ sum x) (dec x))
       sum)) 0 x))

(sum-top-down 10)

; blocks
; do
; do form series or block of expressions are treated as one
(do
  (def x 5)
  (def y 4)
  (+ x y)
  [x y]) ; [5 4]

; locals
; no local variables, but it does have locals which can't vary
(let [r         5
      pi        3.1415 
      r-squared (* r r)] 
  (println "radius is " r)
  (* pi r-squared))
   ; 78.5375
; the body is expressed as an implicit do

; loops
; recur
; special form called recur used in tail recursion
(defn print-down-from [x]
  (when (pos? x)
    (println x)
    (recur (dec x))))
; must be tail tail

(defn sum-down-from 
  ([x] (sum-down-from 0 x))
  ([sum x])
  (if (pos? x)
    (recur (+ sum x) (dec x))
    sum))
(sum-down-from 0 10)
(sum-down-from 10)

; loop
; recur going back to top level function or nearest loop
(defn sum-down-from [initial-x]
  (loop [sum 0 x initial-x] ; set initial state of the loop
    (if (pos? x)
      (recur (+ sum x) (dec x))
      sum))) 

; quoting
; quoting special operator prevents its arguments from being evaluated
(def x 5)
(quote x) ; x
(quote not-defined) ; not defined
; works on nested lists too
(quote (cons 1 [2 3])) ; (cons 1 [2 3])
; shortcut for quote is '
'(+ 1 (+ 2 3)) ; (+ 1 (+ 2 3))

; syntax-quote
; ` is similar to ' but it provides a few extra features
; it evaluates functions to their namespace
`(+ 1 2) ; (clojure.core/+ 1 2)
`even? ; clojure.core/even?
`Integer ; java.lang/Integer

; unquoting
; quoting prevents its arguments and all sub-arguments from being evaluated
; sometimes you want to evaluate only some constituents
; ~ is used for unquoting, or forcing an evaluation
`(+ 1 2 ~(+ 3 4)) ; (clojure.core/+ 1 2 7)
(let [x 2]
  `(+ 1 ~x 3)) ; (clojure.core/+ 1 2 3)

; unquote-splicing
; unquote-splicing works like unquote but unpack the arguments
; ~@ splices the list into the containing list instead of inserting in nested list
(let [x '(2 3)]
  `(+ ~@x)) ; (clojure.core/+ 2 3)

; unqualified symbol
; ???
`potion#

; interoperability with Java
; using java host libraries
; accessing static class members
java.util.Locale/JAPAN ; # object[java.util.Locale ... "ja_JP"]
(Math/sqrt 9) ; 3.0

; creating instances
(new java.awt.Point 0 1)
(new java.util.HashMap {"foo" 42 "bar" 9 "baz" "quux"})
; alternative way using .
(java.util.HashMap. {"foo" 42 "bar" 9})

; accessing public instance variables
; precede the field name with a dot and a dash
(.-x (java.awt.Point. 10 20))

; chaining together sequence of method calls
; e.g. new java.util.Date().toString().endsWith("2014")
(.endsWith (.toString (java.util.Date.)) "2019")
; .. macro helps chain the method calls
(.. (java.util.Date.) toString (endsWith "2019"))

; doto macro
; doto lets you run a chain of commands on a given object
(doto (java.util.HashMap.)
  (.put "HOME" "/home/me")
  (.put "SRC", "src")
  (.put "BIN" "classes"))

; error handling
; throw
; throw java.lang.Exception using the throw keyword
(throw (Exception. "I done throwed"))

(defn throw-catch [f]
   (try
     (f)
     (catch ArithmeticException e "No dividing by zero!")
     (catch Exception e (str "You are so bad " (.getMessage e)))
     (finally (println "returning..."))))

(throw-catch #(/ 5 0))
(throw-catch #(/ 4 2))

; namespaces
; namespaces allow you to bundle functions macros and values
; new namespace
(ns joy.ch2)
*ns*

(defn report-ns []
  (str "The current namespace is " *ns*))
(report-ns)

(ns joy.req
  (:require [clojure.set :as s]
            [clojure.string :refer (capitalize)])
  (:refer clojure.set :rename {union onion}))

(s/intersection #{1 2 3} #{3 4 5}) ; #{3}
(capitalize 'hello) ; Hello
(onion '(1 2) '(3 4))
