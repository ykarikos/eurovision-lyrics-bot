(ns eurovision-lyrics-bot.tweeter
  (:require [twitter.api.restful :as restful]
            [environ.core :refer [env]]
            [twitter.oauth :as oauth]))

(def credentials
  (oauth/make-oauth-creds
    (env :consumer-key)
    (env :consumer-secret)
    (env :access-token)
    (env :access-token-secret)))

(defn- create-tweet
  ([lyrics]
    (create-tweet lyrics ""))
  ([lyrics tweet]
    (let [new-tweet (str tweet (first lyrics) "\n")
          new-tweet-len (count new-tweet)]
      (if (> new-tweet-len 140)
        tweet
        (create-tweet (rest lyrics) new-tweet)))))

(defn tweet [song]
  (let [tweet (-> song :lyrics create-tweet)]
    (restful/statuses-update
      :oauth-creds credentials
      :params {:status tweet})
    tweet))
