(ns eurovision-lyrics-bot.parser)

(defmacro traverse [content & path]
  (let [nth-ops (map #(list 'nth %) path)]
    (cons '-> (cons content nth-ops))))

(defn- get-lyrics
  [lyrics-table]
  (->> (drop 3 lyrics-table)
       (map #(traverse % 2 2))
       (filter #(not= % " "))))

(defn parse-song
  [html]
  (let [content (traverse html 3 2 6)
        header (traverse content 3 2 2 3 2 2 2 2 2)
        country (traverse header 4 2)
        year (traverse header 2 2)
        lyrics-table (traverse content 7 2)
        title (traverse lyrics-table 2 2 2 2)
        lyrics (get-lyrics lyrics-table)]
        {:year year
         :country country
         :title title
         :lyrics lyrics}))
