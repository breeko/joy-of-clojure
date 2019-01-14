; precision
; default precision
; clojure truncates by default, must excliticly type
(let [trunc 1.2345678901234567890]
  [trunc (type trunc)])
; [1.2345678901234567, java.lang.Double]

(let [un-trunc 1.2345678901234567890M]
  [un-trunc (type un-trunc)])
; [1.2345678901234567890, java.math.BigDecimal

; promotion
; clojure detects overflow and promotes
(let [long 10
      big 1000000000000000000000
      doub 1.0]
[[(str "long + big = " (type (+ long big)))]
 [(str "long + doub = " (type (+ long doub)))]
 [(str "big + doub = " (type (+ big doub)))]]
)
; long + big = java.lang.BigInt
; long + double = java.lang.Double
; big + big = java.lang.Double

(try (+ 1 Long/MAX_VALUE)
     (catch ArithmeticException e "overflow :-(")
 )
;"overflow :-("

; underflow
; when a number is so small its value collapses to zero
(= (float 0.000000000000000000000000000000000000000000000001) 0.0)
; true

(apply + (repeat 10 0.1M))
; 1.0

(apply + (concat '(0.1) (repeat 9 0.1M)))
; 0.9999999999999999

; rationals
; rational is any number that can be expressed as a fraction
; it provides unlimited precision bound by memory
(/ 2 3)
; 2/3

; clojure still simplifies the fraction
(/ 10 20)
; 1/2

(let [a 1.0e50
      b -1.0e50
      c 17.0]
  [(str "(a + b) + c = " (+ (+ a b) c))
   (str "a + (b + c) = " (+ (+ b c) a))])
; (a + b) + c = 17.0
; a + (b + c) = 0.0

; clojure provides the following function
; ratio? rational? ratioalize

(ratio? 0.5)
; false
(ratio? (/ 1 2))
; true

; rational checks if number is rational and rationalize can rationalize it

(rational? 1.0e50)
; false
(rational? (float (/ 1 2)))

(let [a (rationalize 1.0e50)
      b (rationalize -1.0e50)
      c (rationalize 17.0)]
  [(str "(a + b) + c = ", (+ (+ a b) c))
   (str "a + (b + c) = ", (+ a (+ b c)))])
; (a + b) + c = 17.0N
; a + (b + c) = 17.0N

; keywords
; keywords always refer to themselves, wheras symbols do not
; often used as map keys

(def population {:zombies 2700 :humans 9})

(str (/ (get population :zombies)
          (get population :humans))
       " zombies per capita")

; sometimes keywords are treated as functions when in function position
(:humans population)

; or as enumeration values (e.g. :small :medium

; or as directives to functions
(defn pour [from to]
  (cond
    (= to :inf) (iterate inc from)
    :else (range from to)))

(pour 1 5)
; (1 2 3 4)

(pour 1 :inf)
; (1 2 3 4 5 6 7 ...)

; keywords do not belong to any specific namespace
:not-in-ns
; :not-in-ns

; double colon used to use qualified or prefixed keyword
::in-another
; :user/in-another

(defn do-blowfish [directive]
  (case directive
    :aquarium/blowfish "feed the fish"
    :crypto/blowfish   "encode the message"
    :blowfish          "not sure what to do..."))

(do-blowfish :blowfish)
; not sure what to do...

(do-blowfish :crypto/blowfish)
; encode the message

; symbols
; symbols are roughly analogous to identifiers in other languages
; although they aren't unique solely on name alone
(identical? 'goat 'goat)
; false

(= 'goat 'goat)
; true

(name 'goat)
; "goat"

; only identical when they're the same object
(let [x 'goat y x]
  (identical? x y))
; true

; you can attach metadata to various objects
(let [x (with-meta 'goat {:ornery true}) 
      y (with-meta 'goat {:ornery false})]
  (str "equal? " (= x y)
       " identical? " (identical? x y)
       " x meta: " (meta x) " y meta: " (meta y)))
; equal? true identical? false x meta: {:ornery true} y-meta: {:ornery false}

; like keywords, symbols don't belong to any specific namespace
(def a-symbol 'where-am-i)
; user/a-symbol

a-symbol
; where-am-i

(resolve 'a-symbol)
; user/a-symbol

; clojure as a lisp-1
; lisp-1 programming language uses the same name resolution for function and value binding
; lisp-2 name resolutions are performed differently
; shadowing existing functions with local vars is possible in lisp-1
(defn best [f xs]
  (reduce #(if (f % %2) % %2) xs))

(best > [1 2 3 4 3 2 1])
; 4

; lisp-2 language would require another function to invoke function call explicitly

; regex
; clojure regular expression is created with #
(type #"an example")
; java.util.regex.Pattern

; regex literals starting with (?<flag>) set the mode for the pattern
(re-find #"yo" "Yo")
; nil

(re-find #"(?i)yo" "Yo")
; "Yo"

