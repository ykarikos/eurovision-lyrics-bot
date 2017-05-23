(ns eurovision-lyrics-bot.parser-test
  (:require [clojure.test :refer :all]
            [pl.danieljanus.tagsoup :as tagsoup]
            [eurovision-lyrics-bot.parser :as parser]))

(defn- get-html
  [year]
  (let [filename (str "index.html?" year "fi")]
    (-> filename
      slurp
      tagsoup/parse-string)))

(deftest parser-test
  (testing "test eurovision parser"
    (doseq [year (concat (range 1961 1970)
                         (range 1971 1995)
                         (range 1996 1997)
                         '(1998)
                         '(2000) '(2002)
                         (range 2004 2014))]
      (let [html (get-html year)]
        (try
          (let [song (parser/parse-song html)]
            (is (map? song))
            (is (= (:country song) "Finland"))
            (is (= (:year song) (str year))))
          (catch IndexOutOfBoundsException e
            (do
              (println year)
              (.printStackTrace e))))))))
