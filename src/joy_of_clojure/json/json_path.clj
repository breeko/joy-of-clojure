(ns joy-of-clojure.json.json-path
  (:require [clojure.data.json :as json]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))

(def json-file (json/read-json (slurp "test/joy_of_clojure/example.json")))

(defn get-json-path [json-path json-str]
  "returns json contents based on json-path"
  (cond
    (= (class json-path) String)
    (get-json-path (str/split
                    (str/replace json-path #"^\$\." ""), #"\.") json-str)
    :else
    (cond
      (nil? json-str) json-str
      (empty? json-path) json-str
      :else (let [head (first json-path)
                  tail (rest json-path)
                  par-num (re-find #"(?!\[)\d+(?=\]$)" head)]
                (some? head) (get-json-path tail (json-str (keyword head))))
                ;(some? par) (get-json-path tail (nth json-str par)))
      )
    )
  )
(get-json-path "$.store.book" json-example)

(re-find #"(?!\[).(?=\])" "bob[1][2][3]"
; range
(re-find #"(?<=\[)[\d]+:[\d]?+(?=\])" "bob[1:3]")

; range

(defn parse-paren
 (let [[whole from comma to remain]
      (re-find #"(?<=\[)([\d]+)?(:)?([\d]+)?(?=\])(?:\])(.+)?" "[10:100][12]")]
   (match [from comma to remain]
          ["*" nil nil r] (str "do it all! " r)
          [s nil nil r] (str "single index " r)
          [nil c nil r] (str "inf " r)
          [s c nil r] (str "to inf " r)
          [nil c e r] (str "from inf " r)
          [s c e r] (str "from and to " r)
          :else (str "no match! " )
    )
  )
)

(re-find #"(?<=\[)([\d]+)?:?([\d]+)?(?=\])" "bob[1:3]")

(nil? :h)
; direct index
(re-find #"(?<=\[)([\d]+)?:?([\d]+)?(?=\])" "bob[1:2]")

(re-find #"(?<=\[)[\d]+(?=\])" "[1][2][3]")

(re-find #"" "bob[1][2]")
(str/split "branko"  #"\[.+\]")

(get 0 (:book (:store json-example)))
(nth (:book (:store json-example)) "0")
()


(str/split ".hi", #"\.")
(re-find #"^(?!\[).(?=\]$)" "bob[1]")

(def a {:1 'one :2 'two})
(map #(a %) (keys a))

(keys a)

(re-find #"\[.\]$" "store[*]")

(rest (str/split json-path #"\."))

(rest json-path)

(json-example :store)
  (def json-path "store")


json-example
(get 1 #{2: 'two})

(first)
(rest [1 2])
; tokenize the string
(str/split "$.store.book[*].author", #"\.")
(str/split "$.store.book[*].author", #"fdasf")

(= (class "f") String)
(get-json-path "hello" json-example)

(type (get-json-path "hello" json-example))


(cond
  (> 1 2) 1
  (> 2 3) 2

 )
