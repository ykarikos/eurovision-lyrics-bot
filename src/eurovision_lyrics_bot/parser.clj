(ns eurovision-lyrics-bot.parser)

(defn parse-song
  [html]
  (let [content (-> html (nth 3) (nth 2) (nth 6))
        header (-> content (nth 3) (nth 2) (nth 2) (nth 3) (nth 2) (nth 2) (nth 2) (nth 2) (nth 2))
        country (-> header (nth 4) (nth 2))
        year (-> header (nth 2) (nth 2))
        lyrics-table (-> content (nth 7) (nth 2))
        title (-> lyrics-table (nth 2) (nth 2) (nth 2) (nth 2))]
        {:year year
         :country country
         :title title}))
