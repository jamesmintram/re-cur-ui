(ns refer.modules.users
  (:require
   [re-frame.core :as re-frame]
   [refer.components.list-view :as list-view]
   [refer.util.context :as ctx]
   [taoensso.timbre :as log]
   ))


(re-frame/reg-event-db
 ::user-select
 (ctx/create-rc-handler
  (fn [data params]
    (log/info "User View: " params)
    {:user-select params :make-user 55})))


;;TODO: Some methods for building these dispatch chains
(defn root [path parent-handler]
  (let [users ["user1", "user2"]]
    [list-view/list-display
     users
     (ctx/push parent-handler
               ::user-select
               {:id 69})]))
