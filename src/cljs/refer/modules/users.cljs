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
    (let [{:keys [:id]} data
          list-item (:list-view-item params)
          {:keys [:name]} list-item]

      (log/info "We should update the user: " name)
      ;; How do we access

      ;;TODO: Can we return something here?
      ;;TODO: We can choose to allow this event to propagate?

      {:user-select params :make-user 55}))))


;;TODO: Some methods for building these dispatch chains
(defn root [path parent-handler]
  (let [users ["user1", "user2"]]
    [list-view/list-display
     users
     (ctx/push parent-handler
               ::user-select
               {:id :some})]))
