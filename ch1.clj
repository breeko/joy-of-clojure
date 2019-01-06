; functions are run with prefix notation
(+ 4 1)
(* 10 2)

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

(catenate [1 2 3] [4 5 6])

; anonymous function
(fn [a b] (+ a b))

; using anonymous function
((fn [a b] (+ a b)) 3 4)

; binding an anonymous function to a variable
(def add (fn [a b] (+ a b)))

; using defn macro instead
(defn add [a b] (+ a b))


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
