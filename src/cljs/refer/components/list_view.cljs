(ns refer.components.list-view
  (:require
   [re-frame.core :as re-frame]

   [day8.re-frame.tracing :refer-macros [fn-traced]]
   [refer.util.context :as ctx]
   [taoensso.timbre :as log]
  ))


(re-frame/reg-event-db
 :listview-select
 (ctx/create-rc-handler
  (fn-traced [data params]
    {:list-view-item params :foo-chee 1})))


(defn list-display [items item-fn props]
  [:div
   [:ul
    (map
     (fn [item]
       [:li {:key (:id item)} (item-fn item props)])
     items)]])
