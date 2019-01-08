; functions are run with prefix notation
(+ 4 1)
(* 10 2)

; anonymous function
(fn [a b] (+ a b))

; using anonymous function
((fn [a b] (+ a b)) 3 4)

; binding an anonymous function to a variable
(def add (fn [a b] (+ a b)))

; using defn macro instead
(defn add [a b] (+ a b))
