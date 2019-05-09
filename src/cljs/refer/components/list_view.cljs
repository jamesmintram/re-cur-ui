(ns refer.components.list-view
  (:require
   [re-frame.core :as re-frame]
   [refer.util.context :as ctx]
   [taoensso.timbre :as log]
  ))


(re-frame/reg-event-db
 :listview-select
 (ctx/create-rc-handler
  (fn [data params]
    (log/info "Listview Select: " params)
    {:list-view-item params :foo-chee 1})))


(defn list-display [items parent-handler]
  [:div
   (map
    (fn [item]
      [:ul
       [:li
        [:a
         {:href "#"
          :on-click #(re-frame/dispatch
                      [:listview-select
                       {:params {:id 23 :name item}
                        :data {:parent parent-handler}}])}
         item]]])
    items)])
