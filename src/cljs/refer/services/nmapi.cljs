(ns refer.services.nmapi
  (:require
   [taoensso.timbre :as log]))

(def leaderboard-list
  [[:get "/leaderboards/"]
   ["leaderboard1", "scores", "wins"]])

(def user-list
  [[:get "/user/"]
   ["user1", "user2", "user3"]])

(def api-data [leaderboard-list
               user-list])

(def mock-data (into {} api-data))

(defn make-request [method path]
  (let [data-key [method path]]
    (log/info "MOCK DATA: " mock-data)
    (log/info (mock-data data-key))))

