; functions are run with prefix notation
(+ 4 1)
(* 10 2)

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
  ([a op b op2 c op3 d]     (op3 d (l->r a op b op2 c)))
  )

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

