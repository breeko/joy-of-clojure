(ns joy-of-clojure.json-test
  (:require [joy-of-clojure.json.json-path :refer :all]
            [clojure.test :refer :all]
            [clojure.data.json :as json]))

(def json-file (json/read-json (slurp "test/joy_of_clojure/example.json")))


(deftest test-all-authors
  (testing "Retrieving all authors"
    (is (= (get-json-path "$.store.book[*].author" json-file)
           ["Nigel Rees" "Evelyn Waugh" "Herman Melville" "J. R. R. Tolkien"]))))

(run-tests 'joy-of-clojure.json-test)

