; collection types
; persistence, sequences and complexity
; persistence collection in clojure allows you to preseve historical versions

; create an array
(def ds (into-array [:a :b :c]))
(seq ds)
; (:a :b :c)

; set value of array
(aset ds 1 :d)
(seq ds)
; (:a :d :c)

; create persistent vector
(def ds [:a :b :c])
(def ds1 (replace {:b :d} ds))

ds1
; [:a :d :b]

ds
; [:a :b :c]

; seqs, sequentials and sequences
; a sequential collection is one that holds a series of values without reordering
; a sequential is one of three collections types along with sets and maps
'(1 2 3)

; a sequence is a sequential collection that represents a series of values that may or may not exist yet
; sequence may be empty
'()
(empty? '())


; seq is a simply API
; consists of two functions: first and rest
; (first coll) returns the first item or nil
; (rest coll) returns seq after first item
(rest '())


