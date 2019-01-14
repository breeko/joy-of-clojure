(ns joy-of-clojure.core
  (:gen-class)
  (:require [clojure.data.json :as json]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [json-str (json/read-str "{\"a\":1,\"b\":2}")]
    (println json-str))
  (println "Hello, World!")
  )
