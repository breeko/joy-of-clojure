
; only nil and false evalute to false
(if true :truthy :falsey)
; truthy
(if [] :truthy :falsey)
; truthy
(if nil :truthy :falsey)
; falsey
(if false :truthy :falsey)
; falsey

; never create boolean object
(def evil-false (Boolean. 'false))
evil-false
; false
(= false evil-false)
; true
(if evil-false :truthy :falsey)
; truthy

; use Boolean/valueOf instead
(if (Boolean/valueOf "false") :truthy :falsey)
; falsey

; test just for nil
(when (nil? nil) :nil)
; :nil
(when-not (nil? []) :not-nil!)
; not-nil!
(if (not (nil? nil)) :not-nil :nil)
; :nil

; seq returns a sequence view of a collection or nil if collection is empty
(seq [])
; nil
(seq [1 2 3])

; seq or empty? can be used to check if empty
(defn print-seq [x]
  (when-not (empty? (seq x))
    (println (first x))
    (recur (rest x))))

(print-seq [1 2 3])

; doseq used for side effects, doesn't return anything
(doseq [x [1 2 3]] (+ x 5))
 ; nil
; for builds a lazy sequence and returns it
(for [x [1 2 3]] (+ x 5))
(dotimes [x 3] (println :hi))
; nil
; for also takes :let and :when
(for [x (range 10)
      :let [x-big (+ x 100)]
      :when (= (rem x-big 2) 0)]
  x-big)
; [100 102 104 106 108]

; destructing
; destructing allows you to positionally bind locals
; based on an expected form for a composite data structure
; loosely based on pattern matching in haskell and scala

; take a vector of length 3 that represent first middle and last name
(defn guys-whole-name [x]
  (str
   (nth x 2) ", "
   (nth x 0) " "
   (nth x 1)))

(def guys-long-name ['Guy 'Lewis 'Steele])
(def guys-short-name ['Guy 'Steele])
(def guys-long-name-associates `[~@guys-long-name ~@['Bob 'Rob]])
; [Guy Lewis Steele Bob]

; destructing a vector
(defn guys-whole-name2 [[first middle last]]
  (str last ", " middle " " first))
(guys-whole-name2 guys-long-name) ; Steele, Lewis Guy
(guys-whole-name2 guys-short-name) ; , Steele Guy
(guys-whole-name2 `[~@guys-long-name ~@['Bob]]) ; Steele, Lewis Guy

; you can also use & to store arguments 
(defn guys-associates [[first middle last & others]]
  (str last ", " middle " " first " and associates " others))
(guys-associates ['Guy 'Lewis 'Steele 'Bob 'Rob])

; as can be used to bind the input collection as is
(let [range-vec (vec (range 10)) ; defining range-vec
      [a b c & more :as all] range-vec] ; destructing range-vec
  (println "a b c: " a b c)
  (println "and more " more)
  (println "all " all))

; desturcting a map
(def guys-name-map
  {:f-name 'Guy :m-name 'Lewis :l-name 'Steele})
(def guys-short-name-map
  {:f-name 'Guy :l-name 'Steele})


(defn guys-whole-name-map [{f-name :f-name m-name :m-name l-name :l-name}]
  (str l-name ", " m-name " " f-name))

(guys-whole-name-map guys-name-map)

; :keys feature allows you to bind map keys
(defn guys-whole-name-map2 [{:keys [f-name m-name l-name] :as whole-name}]
  (str l-name ", " m-name " " f-name "\nwhole name: " whole-name))

(guys-whole-name-map2 guys-name-map)

; if destructing can't find a key, it'll map to nil
(guys-whole-name-map2 guys-short-name-map)

; you can set defaults with :or
(defn guys-whole-name-map3 [{:keys [f-name m-name l-name]
                             :or {m-name 'Middle}
                             :as whole-name}]
  (str l-name ", " m-name " " f-name))

(guys-whole-name-map3 guys-short-name-map)

; works on lists, used by functions to accept keyword arguments
; desctructing works on lists by defining a map declaring the local name
; as indices into them
(let [{first-thing 0 last-thing 3} [1 2 3 4]]
  [first-thing last-thing]) ; [1 4]

; each function parameter can destruct into a map or sequence
(defn get-last-name [{:keys [l-name]}]
   l-name)

(get-last-name guys-name-map)
; Steele


; Experimenting with repl
; ranges
(range 5)
; (0 1 2 3 4)

; for loop
(for [x (range 2) y (range 2)] [x y])
; ([0 0] [0 1] [1 0] [1 1])

; finding documents
(use 'clojure.repl)
(find-doc "xor")

(bit-xor 1 2)
; 3
(defn xors [max-x max-y]
  (for [x (range max-x) y (range max-y)]
        [x y (bit-xor x y)]))

(xors 2 2)
; ([0 0 0] [0 1 1] [1 0 1] [1 1 0])

; getting methods of an object
; generalize find-methods
(defn find-methods [c match-name]
  (let [regex-match-name (re-pattern (str "(?i)" match-name))]
    (for [meth (.getMethods c)
          :let [meth-name (.getName meth)]
          :when (re-find regex-match-name meth-name)]
      meth-name)
    ))

; experimenting with graphics

; new frame
(def frame (java.awt.Frame.))

(find-methods (class frame) "visible")

; set frame to visible and adjust size
(.setVisible frame true)
(.setSize frame 200 200)

; get graphics to draw on frame
(def gfx (.getGraphics frame))

(.setColor gfx (java.awt.Color. 255 128 0))
(.fillRect gfx 100 100 50 75)

(find-methods (class gfx) "clear")

; draw xor pattern
(doseq [[x y xor] (xors 200 200)]
  (.setColor gfx (java.awt.Color. xor xor xor))
  (.fillRect gfx x y 1 1))

(defn clear [g max]
  (.clearRect g max max))
(clear frame 200)
(.clearRect)

(defn draw-rect
  [f max-x max-y]
  (.clearRect gfx 0 0 max-x max-y)
  (.setSize frame max-x max-y)
  (doseq [x (range max-x) y (range max-y)]
    (let [color (rem (f x y) 256)]
      (.setColor gfx (java.awt.Color. color color color))
      (.fillRect gfx x y 1 1)))
  )

(draw-rect bit-xor 500 500)
(find-methods (class gfx) "rect")

