(ns eurovision-lyrics-bot.tweeter
  (:require [twitter.api.restful :as restful]
            [environ.core :refer [env]]
            [twitter.oauth :as oauth]
            [clojure.java.io :as io]))

(defn- split-flag [f]
  (rest (re-find #"(.+) ([^ *]+)$" f)))

(def flags
  (->> "flags.txt"
    io/resource
    slurp
    clojure.string/split-lines
    (map split-flag)
    flatten
    (apply array-map)))


(def credentials
  (oauth/make-oauth-creds
    (env :consumer-key)
    (env :consumer-secret)
    (env :access-token)
    (env :access-token-secret)))

(defn- get-lyrics
  ([lyrics]
    (get-lyrics lyrics ""))
  ([lyrics tweet]
    (let [new-tweet (str tweet (first lyrics) "\n")
          new-tweet-len (count new-tweet)]
      (if (> new-tweet-len 130)
        tweet
        (get-lyrics (rest lyrics) new-tweet)))))

(defn tweet [song]
  (let [lyrics (-> song :lyrics)
        random-start (-> lyrics count (- 4) rand-int)
        random-lyrics (drop random-start lyrics)
        tweet-lyrics (get-lyrics random-lyrics)
        flag (flags (:country song))
        tweet (str tweet-lyrics
              (:year song) " "
              flag)]
    (restful/statuses-update
      :oauth-creds credentials
      :params {:status tweet})
    tweet))
