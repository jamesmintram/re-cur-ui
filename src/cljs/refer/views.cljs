(ns refer.views
  (:require
   [re-frame.core :as re-frame]
   [refer.subs :as subs]
   [refer.util.route :as route]
   [refer.layout.main :as main]
   ))

(def route-data
  (route/make-route
  [
   :main
   :users
   10
   ]))




;; (ctx/push parent-ctx ::event-id props)
;; (re-frame/dispatch (ctx/push parent-ctx ::event-id props))

;; (let [ctx (partial ctx/push parent-ctx ::event-id)]
;;   (re-frame/dispatch (ctx {:user-id 23})))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])]
    [:div
     [main/main-layout route-data]
     ])
  )
