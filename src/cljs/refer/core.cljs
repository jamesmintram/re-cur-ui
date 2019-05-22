(ns ^:figwheel-hooks refer.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [refer.events :as events]
   [refer.views :as views]
   [refer.config :as config]
   [refer.util.router :as br]

   ;;[refer.modules.leaderboards.data :as data]
   ))

(def routes {"" ::users
             [:id "/"] ::users-item})
(def root-url ["/" {"users/" routes}])

(def !location (atom nil))
(defn start-router []
  (br/start-router! root-url
                    {:on-navigate (fn [location]
                                    ;;Dispatch a new route
                                    (reset! !location location)
                                    (println "New Router Loc " location))
                     :default-location {:handler ::home-page}}))

(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (start-router)
  ;;(data/refresh-leaderboard-list) 
  (mount-root))

(defn ^:after-load reload []
  (mount-root))
