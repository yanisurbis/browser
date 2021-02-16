(ns browser.integration.xlsx
  (:require [dk.ative.docjure.spreadsheet :as ss]
            [clojure.string :as str]))

(defn tweet-url->tweet-id
  "takes tweet url"
  {:test (fn []
           (assert (tweet-url->tweet-id "http://twitter.com/smashingmag/status/1361301045482315777")
                   "1361301045482315777"))}
  [tweet-url]
  (-> tweet-url
      (clojure.string/split  #"/")
      last))

(def tweet-ids (->> (ss/load-workbook "./resources/tweets1.xlsx")
                    (ss/select-sheet "Sheet1")
                    (ss/select-columns {:E :tweet-url})
                    (map (comp tweet-url->tweet-id :tweet-url))))