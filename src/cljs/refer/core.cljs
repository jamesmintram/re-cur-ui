(ns ^:figwheel-hooks refer.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [refer.events :as events]
   [refer.views :as views]
   [refer.config :as config]

   [refer.modules.leaderboards.data :as data]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:after-load init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (data/refresh-leaderboard-list)
  (mount-root))
