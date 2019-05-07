(ns refer.modules.leaderboards.data
  (:require
   [refer.services.nmapi :as nmapi]))

(defn refresh-leaderboard-list []
  ;;Updates the db entry containing all of the databases
  (nmapi/make-request :get "/user/"))
