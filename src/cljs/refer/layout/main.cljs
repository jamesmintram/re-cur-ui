(ns refer.layout.main
  (:require
   [refer.util.context :as ctx]
   [refer.util.route :as route]

   [refer.modules.leaderboards :as leaderboards]
   [refer.modules.users :as users]
   [refer.pages.login :as login]

   [re-frame.core :as re-frame]
   [taoensso.timbre :as log]
   ))


;;----------------------------------------------------------

(defn side-panel [path]
  (let [path-head (first path)
        path-tail (rest path)]
    [:div [:h1 "Side Panel"]]))

(defn content-panel [path]
  (let [path (route/step path)]
    (case (:current path)
      :leaderboards (leaderboards/root path)
      :users (users/root path))))

;;----------------------------------------------------------

(defn main-panel [path]
  [:div
   (side-panel path)
   (content-panel path)])

;; Root layout
(defn main-layout [path]
    (case (:current path)
      :main  (main-panel path)
      :login (login/login path)))

