(ns refer.modules.leaderboards
  (:require
   [re-frame.core :as re-frame]

   [refer.util.context :as ctx]
   [refer.components.list-view :as list-view]

   [taoensso.timbre :as log]
   ))


(re-frame/reg-event-db
 ::lboard-select
 (ctx/create-rc-handler
  (fn [data params]
    (log/info "Leaderboard View: " params)
    {:lboard-view params :make-lboard 33})))


(defn root [path parent-handler]
  (let [lboards ["lb1" "lb2"]]
    [list-view/list-display lboards (ctx/push parent-handler
                                              ::lboard-select
                                              {:id 33})]))
