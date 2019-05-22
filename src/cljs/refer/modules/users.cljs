(ns refer.modules.users
  (:require
   [refer.domain.users :as users1]

   [re-frame.core :as re-frame]
   [refer.components.list-view :as list-view]
   [refer.util.context :as ctx]

   [bidi.bidi :as bidi]
   [taoensso.timbre :as log]
   ))

(re-frame/reg-event-db
 ::user-delete
 (fn [db params]
   (log/info "Handle Delete")
   (users1/create-user db {:id (rand-int 50)
                           :name "James"
                           :job "Programmer"})))

(re-frame/reg-sub
 ::sub-users
 (fn [db _] (users1/users db)))


(defn user-list-item [item props]
  (let [user-map {:params {:id 23}}
        base-path (:path props  )]
    [:div
     [:a {:href (str "#/users/" (:id item))} "Welcome as: " (:name item)]
     [:span " - "]
     [:a {:href "#"
          :on-click #(re-frame/dispatch
                      [::user-delete (:id item)])}
     "Delete"]
     ]))

(defn root [path]
  (let [users (re-frame/subscribe [::sub-users])
        props {:path path}]
    [:div
     [:h1 "Users"]
     [list-view/list-display @users user-list-item props]]))

;;define routes here
(def routes {"" ::users
             [:id "/"] ::users-item})

;;(def root ["/" {"users/" routes}])



;;(bidi/match-route root "/users")
