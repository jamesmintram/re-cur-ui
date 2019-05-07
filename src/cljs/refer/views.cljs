(ns refer.views
  (:require
   [re-frame.core :as re-frame]
   [refer.subs :as subs]
   [refer.layout.main :as main]
   ))

(def route-data
  [
   :main
   :leaderboards
   10
   ])
(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [main/main-layout route-data]
     ])
  )
