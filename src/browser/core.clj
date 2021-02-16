(ns browser.core
  (:require [somnium.congomongo :as m]
            [browser.integration.xlsx :as xlsx]
            [browser.integration.twitter :as twitter]
            [browser.core.coll :as coll]
            [clojure.core.async :as async]
            [qbits.spandex :as s]))

(def conn (m/make-connection "mydb" :instances [{:host "127.0.0.1" :port 27017}]))
(m/set-connection! conn)

;;(def tweet-ids (xlsx/xlsx-file->tweet-ids "./resources/tweets1.xlsx"))
;;
;;(def tweeter-api-responses (->> tweet-ids
;;                                (partition 100)
;;                                (map #(twitter/fetch-detailed-tweets-v2 {:ids %}))))
;;
;;(def tweets-data (->> tweeter-api-responses
;;                      (mapcat #(get-in % [:body :data]))))
;;
;;(def tweets-data-for-elastic (->> tweets-data
;;                                  (map coll/->kebab-case)))
;;
;;(def elastic-conn (s/client {:hosts ["http://127.0.0.1:9200"]}))
;;
;;(s/request elastic-conn {:url [:tweets-test]
;;                         :method :put})
;;
;;(s/request elastic-conn {:url [:tweets-test :_doc]
;;                         :method :post
;;                         :body (take 2 tweets-data-for-elastic)})

(defn add-tweet-to-index [tweet]
  (s/request elastic-conn {:url [:tweets-test :_doc]
                           :method :post
                           :body tweet}))

#_(->> tweets-data-for-elastic
       (map add-tweet-to-index)
       doall)

#_(->> (s/request elastic-conn {:url [:tweets-test :_search]
                                :method :get
                                :body {:query {:query_string {:query "job"}}}})
       :body
       :hits
       :hits
       (map (comp (juxt :text :id) :_source)))
