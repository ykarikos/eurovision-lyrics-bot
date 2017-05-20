(ns eurovision-lyrics-bot.parser)

(defmacro traverse [content & path]
  (let [nth-ops (map #(list 'nth %) path)]
    (cons '-> (cons content nth-ops))))

(defn- get-lyrics
  [lyrics-table]
  (->> (drop 3 lyrics-table)
       (map #(traverse % 2 2))
       (filter #(not= % " "))))

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
        lyrics-table (find-element html :table :id "lyrics-table")
        title (traverse lyrics-table 2 2 2 2)
        lyrics (get-lyrics lyrics-table)]
      {:year year
       :country country
       :title title
       :lyrics lyrics}))
