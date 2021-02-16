(ns browser.integration.twitter
  (:require [clj-http.client :as client]
            [clojure.pprint :refer [pprint]]
            [environ.core :refer [env]]
            [browser.core.coll :as coll]))

;; Libs
;; https://developer.twitter.com/en/docs/basics/authentication/oauth-2-0/bearer-tokens
;; https://github.com/dakrone/clj-http
;; https://github.com/saurabhnemade/react-twitter-embed

;; Api
;; INDEX https://developer.twitter.com/en/docs/api-reference-index

;; TWEETS https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-statuses-lookup
;; USERS https://developer.twitter.com/en/docs/accounts-and-users/follow-search-get-users/api-reference/get-users-lookup

;; Api
(def url-twitter-auth "https://api.twitter.com/oauth2/token")
(def url-favorite-tweets "https://api.twitter.com/1.1/favorites/list.json")
(def url-tweets "https://api.twitter.com/1.1/statuses/lookup.json")
(def url-tweetsv2 "https://api.twitter.com/1.1/statuses/lookup.json")
(def url-users "https://api.twitter.com/1.1/users/lookup.json")

(def twitter-access-token-secret (:twitter-access-token-secret env))
(def twitter-access-token (:twitter-access-token env))
(def bearer-token (:bearer-token env))
(def token bearer-token)

#_(def token (-> (client/post url-twitter-auth
                              {:basic-auth    [twitter-access-token twitter-access-token-secret]
                               :form-params   {"grant_type" "client_credentials"}
                               :cookie-policy :none
                               :as            :json-strict})
                 :body
                 :access_token))

;;

;; https://developer.twitter.com/en/docs/tweets/post-and-engage/api-reference/get-favorites-list
;; https://developer.twitter.com/en/docs/labs/filtered-stream/faq

(defn fetch-favorite-tweets [{:keys [user-id count max-id]
                              :or   {count 200}}]
  (->> (client/get url-favorite-tweets
                   {:headers       {"Authorization" (str "Bearer " token)}
                    :query-params  (coll/remove-nils
                                     {"user_id" 114640283
                                      ;;"count" count
                                      ;;"max_id" max-id
                                      "tweet_mode" "extended"})
                    :as            :json-strict
                    ;;:throw-entire-message? true
                    :cookie-policy :none})
       :body
       (map coll/->kebab-case)))

(->> (client/get url-favorite-tweets
                 {:headers       {"Authorization" (str "Bearer " token)}
                  :query-params  (coll/remove-nils
                                   {"user_id" 114640283
                                    ;;"count" count
                                    ;;"max_id" max-id
                                    "tweet_mode" "extended"})
                  :as            :json-strict
                  ;;:throw-entire-message? true
                  :cookie-policy :none})
     :headers)

(fetch-favorite-tweets {:user-id "114640283"})

;;

(defn fetch-detailed-tweets [{:keys [ids]}]
  (client/get url-tweets
              {:headers       {"Authorization" (str "Bearer " token)}
               :query-params  {"id" (clojure.string/join "," ids)
                               "tweet_mode" "extended"}
               :as            :json-strict
               :cookie-policy :none}))

#_(fetch-detailed-tweets (take 2 browser.integration.xlsx/tweet-ids))

;;

(defn fetch-user [{:keys [user-id]}]
  (->> {:headers       {"Authorization" (str "Bearer " token)}
        :query-params  {"user_id" user-id}
        :as            :json-strict
        ;;:response-interceptor (fn [resp _] (pprint resp))
        :cookie-policy :none}
       (client/get url-users)
       :body))

;;

(defn fetch-detailed-tweets-v2 [{:keys [ids]}]
  (client/get "https://api.twitter.com/2/tweets"
              {:headers       {"Authorization" (str "Bearer " token)}
               :query-params  {"ids" (clojure.string/join "," ids)
                               "tweet.fields" "created_at"
                               "expansions" "author_id"}
               :as            :json-strict
               :cookie-policy :none}))

;;(fetch-detailed-tweets-v2 {:ids (take 2 browser.integration.xlsx/tweet-ids)})

;;

;;(def favourite-tweets (fetch-favorite-tweets {:twitter-user-id my-twitter-id}))

;;(defonce me (fetch-user {:user-id my-twitter-id}))
;;(:favourites_count (first me))

;;(fetch-detailed-tweets {:ids (take 2 (map :id favourite-tweets))})