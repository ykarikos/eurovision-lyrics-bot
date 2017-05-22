(defproject eurovision-lyrics-bot "0.1.0-SNAPSHOT"
  :description "Twitter bot that sends eurovision song texts as tweets"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-tagsoup "0.3.0" :exclusions [org.clojure/clojure]]
                 [clj-http "3.5.0"]
                 [environ "1.0.0"]]
  :main ^:skip-aot eurovision-lyrics-bot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
