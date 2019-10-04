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
; [:a :d :c]

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
(first '())
; nil
(rest '())
; ()

; seq function accepts a wide variety of collection like objects and returns a sequence
(seq [1 2 3])
; (1 2 3)

(seq {:1 "one" :2 "two"})
; ([:1 "one" :2 "two"])

(seq (keys {:1 "one" :2 "two"}))
; (:1 :2)

(seq (vals {:1 "one" :2 "two"}))
; ("one" "two")


(sequential? {:1 "one"})
; false

(sequential? #{:1 :2})
; false

; clojure classifies each collection type into three logical categories:
; sequentials, maps and sets
; few are actually sequences but several, like vectors, are sequential
; if two sequentials are compared and each element is the same, = returns true
(= '(1 2 3) [1 2 3])
; true

(= '(1 2 3) #{1 2 3})
; false

; wide variety of objects satisfies this interface
(class (hash-map :a 1))
; clojure.lang.PersistentHashMap

(class (first (hash-map :a 1)))
; clojure.lang.MapEntry

(class (seq (hash-map :a 1)))
; clojure.lang.PersistentHashMap$NodeSeq

(class (seq (keys (hash-map :a 1))))
; clojure.lang.PersistentMap$KeySeq

(class (seq (vals (hash-map :a 1))))
; clojure.lang.APersistentMap$ValSeq


; vectors
; vectors have literal square brackets
[1 2 3]
(vec (range 10))

; adding to vectors
(let [my-vector [:a :b :c]]
  (into my-vector (range 10)))
; [:a :b :c 0 1 2 3 4 5 6 7 8 9]

; heterogeneous types okay, even primitive types
; can specify primitive type with vector-of
; (:int :long :float :double :byte :short :boolean :char)
(into (vector-of :char) (range 100 110))
; [\d \e \f \g \h \i \j \k \l \m]
(into (vector-of :int) [10.9])
; [10]

; vectors are efficient at
; 1. adding or removing things from right end of collection
; 2. accessing or changing items in interiour by numeric index
; 3. walking in reverse order

(def a-to-j (vec (map char (range 65 75))))

(nth a-to-j 4)
; \E
(get a-to-j 4)
; \E
(a-to-j 4)

; when vector is nil:
;  nth->nil
;  get->nil
;  vector as function->throws

; when index out of range:
;  nth->throws
;  get->nil
;  vector as function->throws

; supports not found:
;  nth->yes (nth [] 0 :whoops)
;  get->yes (get [] 0 :whoops)
;  vector as function->now

; convert to seq
(seq a-to-j)
(rseq a-to-j)

; update keys
(assoc a-to-j 4 "NO LONGER \\E")
; \A \B \C "NO LONGER \E"  \E \F \G \H \I \J

; didn't change underlying
a-to-j
; \A \B \C \D \E \F \G \H \I \J

(replace {:1 'a :2 'b} [:1 :2 :3])
; ['a 'b 3]

; use get-in for multi-dimenions
(def matrix [[1 2]
             [3 4]])

(get-in matrix [0 1])
; 2

(assoc-in matrix [1 1] 'a)
; [[1 2] [3 'a]]

(update-in matrix [1 1] * 2)
; [[1 2] [3 8]]


(defn neighbors
  "
  [size yx]
    Returns manhattan neighbors of 2d vector within the given size
  [deltas size yx]
    Adds deltas vector to 2d vector and returns neighbors based on delta that are in bounds"
  ([size yx] (neighbors [[-1 0] [1 0] [0 -1] [0 1]]
                        size
                        yx))
  ([deltas size yx]
   (filter (fn [new-yx]
             (every? #(< -1 % size) new-yx))
           (map #(vec (map + yx %)) deltas))))

(neighbors 10 [2 3])
; [[1 3] [3 3] [2 2] [2 4]]

; vectors can be though of as stacks with push and pop operations as conj and pop

(conj [1 2] 3)
; [1 2 3]

(pop [1 2 3])
;[1 2]

(peek [1 2 3])
; 3

(filter odd? [1 2 3])
(find [4 5 6] 0)

(defun pos
  "Returns positional index of a value in a collection"
  [arr x]
  
  )
