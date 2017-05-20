(ns eurovision-lyrics-bot.parser)

(defmacro traverse [content & path]
  (let [nth-ops (map #(list 'nth %) path)]
    (cons '-> (cons content nth-ops))))

(defn parse-song
  [html]
  (let [content (traverse html 3 2 6)
        header (traverse content 3 2 2 3 2 2 2 2 2)
        country (traverse header 4 2)
        year (traverse header 2 2)
        lyrics-table (traverse content 7 2)
        title (traverse lyrics-table 2 2 2 2)]
        {:year year
         :country country
         :title title}))
