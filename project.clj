(defproject eurovision-lyrics-bot "1.0.0"
  :description "A Twitter bot that tweets Eurovision song texts as tweets"
  :url "https://github.com/ykarikos/eurovision-lyrics-bot"
  :license {:name "MIT"
            :url "https://github.com/ykarikos/eurovision-lyrics-bot/blob/master/LICENSE"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-tagsoup "0.3.0" :exclusions [org.clojure/clojure]]
                 [clj-http "3.5.0"]
                 [environ "1.0.0"]
                 [twitter-api "1.8.0"]]
  :main ^:skip-aot eurovision-lyrics-bot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
