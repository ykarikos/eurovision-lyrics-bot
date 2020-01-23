(ns eurovision-lyrics-bot.core
  (:gen-class)
  (:require [environ.core :refer [env]]
            [pl.danieljanus.tagsoup :as tagsoup]
            [eurovision-lyrics-bot.parser :as parser]
            [eurovision-lyrics-bot.tweeter :as tweeter]
            [clj-http.client :as client]))

(defn- get-song []
  (-> (env :url)
    client/get
    :body
    tagsoup/parse-string
    parser/parse-song))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [song (get-song)
        tweet (tweeter/tweet song)]
    (println tweet)))
