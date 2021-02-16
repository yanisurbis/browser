(ns browser.core
  (:require [somnium.congomongo :as m]
            [browser.integration.xlsx :as xlsx]
            [browser.integration.twitter :as twitter]
            [clojure.core.async :as async]))

(def conn (m/make-connection "mydb" :instances [{:host "127.0.0.1" :port 27017}]))
(m/set-connection! conn)

(def tweet-ids (xlsx/xlsx-file->tweet-ids "./resources/tweets1.xlsx"))

(def tweeter-api-responses (->> tweet-ids
                                (partition 100)
                                (map #(twitter/fetch-detailed-tweets-v2 {:ids %}))))

(def tweets-data (->> tweeter-api-responses
                      (mapcat #(get-in % [:body :data]))))

(count tweets-data)

(take 10 tweets-data)
