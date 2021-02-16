(ns browser.core
  (:require [somnium.congomongo :as m]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(def conn
  (m/make-connection "mydb" :instances [{:host "127.0.0.1" :port 27017}]))

(m/set-connection! conn)

(m/insert! :robots {:name "robby"})
(m/fetch-one :robots)
