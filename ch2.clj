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
   1 2)                            ; #{1 2}
; you can bind anonymous functions to vars with def
(def make-set (fn [x y]
       #{x y}))
(make-set 1 2)                     ; #{1 2}
; or use defn macro
(defn make-set
  "Takes two values and makes a set from them"
  [x y]
  #{x y})
(make-set 1 2)                     ; #{1 2}

; functions with multiple arities
; functions allow for arity overloading
(defn make-set
  ([x] #{x})
  ([x y] #{x y}))
(make-set 1)                        ; #{1}
(make-set 1 2)                      ; #{1 2}
; functions that take a variable number of arguments uses the & symbol
; every symbol before the & is still bound
(defn arity2+ [first second & more]
  (vector first second more))
(arity2+ 1 2 3 4 5)                 ; [1 2 (3 4 5)]

