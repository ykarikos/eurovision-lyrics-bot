(ns eurovision-lyrics-bot.parser)

(defmacro traverse [content & path]
  (let [nth-ops (map #(list 'nth %) path)]
    (cons '-> (cons content nth-ops))))

(defn- filter-elements
  [lyrics-row]
  (if (vector? lyrics-row)
    (get lyrics-row 2)
    lyrics-row))

(defn- get-lyrics-rows
  [lyrics-table]
  (let [rows (drop 3 lyrics-table)
        row-elem (-> rows first first)]
    (if (= :tr row-elem)
      rows
      (->> rows first (drop 2)))))

(defn- get-lyrics
  [lyrics-table column]
  (->> (get-lyrics-rows lyrics-table)
       (map #(traverse % column 2))
       (filter #(not= % " "))
       (map filter-elements)))

(declare find-element)

(defn- find-element-from-children
  [children elem-keyword attr-name attr-value]
  (if (empty? children)
      []
      (let [first-child-search (find-element (first children) elem-keyword attr-name attr-value)]
        (if (empty? first-child-search)
          (find-element-from-children (rest children) elem-keyword attr-name attr-value)
          first-child-search))))

(defn- find-element
  "Find the first element with the elem-keyword and attr-name=attr-value from tree"
  [tree elem-keyword attr-name attr-value]
  (if-not (vector? tree)
    []
    (let [root-keyword (first tree)
          root-attr-value (attr-name (second tree))
          children (drop 2 tree)]
      (if (and (= root-keyword elem-keyword)
               (= root-attr-value attr-value))
          tree
          (find-element-from-children children elem-keyword attr-name attr-value)))))

(defn parse-song
  [html]
  (let [header (find-element html :div :class "infohead2")
        country (traverse header 2 4 2)
        year (traverse header 2 2 2)
        lyrics-table (find-element html :table :class "text")
        title (traverse lyrics-table 2 2 2 2)
        languages (->> (traverse lyrics-table 2) (drop 2) count)
        lyrics (get-lyrics lyrics-table (inc languages))]
      {:year year
       :country country
       :title title
       :lyrics lyrics}))
