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

;;TODO: Define this somewhere

(re-frame/reg-event-db
 ::content-panel-sel
 (ctx/create-rc-handler
  (fn [data params]
    ;; Expect data {:ha 1}
    (log/info "Content View; " params)
    {:conent-panel params :make-user 55})))


;; Main layout
(defn side-panel [path]
  (let [path-head (first path)
        path-tail (rest path)]
    [:div [:h1 "Side Panel"]]))


;;----------------------------------------------------------

(defn content-panel [path]
  (let [path-head (first path)
        path-tail (rest path)
        handler {:event ::content-panel-sel :data {:ha 1}}]
    (log/info path-head)
    (case path-head
          :leaderboards (leaderboards/root path-tail handler)
          :users (users/root path-tail handler))))  

(defn main-panel [path]
  [:div
   (side-panel path)
   (content-panel path)])

;; Root layout
(defn main-layout [path]
  (let [path-head (first path)
        path-tail (rest path)]
    (case path-head
      :main  (main-panel path-tail)
      :login (login/login path-tail)
      )))



;; Things to consider
;;------------------------------------------------
;; Components can generate valid links wherever they are in the hierarchy
;; Components may exist multiple times in different sub trees
;; Components must be able to dispatch events without needing a global view of the world
;;
;; Reading data
;; Sending and processing events
;; Generating links/references

;;
;; Main component use cases for bundled path state
;;------------------------------------------------
;; Components that do not care about the path
;;   - pass it on
;; Components that want to read from the path
;;   - creating links to child elements
;; Components that want to dispatch based on path head
;;   - layouts
;;   - single object views
;; Components that want to link to something outside of itself or descendants
;;   - Login page
;;   - Current user profile
;;   
