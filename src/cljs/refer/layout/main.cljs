(ns refer.layout.main
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as log]))

(defn create-rc-handler [processor]
  (fn [_db args]

    ;;(log/info "-----------------------")
    ;;(log/info "Args: " args)
    ;;(log/info "-----------------------")

    (let [[_ param-map] args
          data (:data param-map)
          params (:params param-map)

          parent (:parent data)
          parent-event (:event parent)
          parent-data (:data parent)

          result (processor data params)

          parent-args {:data parent-data
                       :params result}]
      (if parent-event
        (re-frame/dispatch [parent-event parent-args])))))

(re-frame/reg-event-db
 :listview-select
 (create-rc-handler
  (fn [data params]
    (log/info "Listview Select: " params)
    {:list-view-item params :foo-chee 1})))

(re-frame/reg-event-db
 ::lboard-select
 (create-rc-handler
  (fn [data params]
    (log/info "Leaderboard View: " params)
    {:lboard-view params :make-lboard 33})))

(re-frame/reg-event-db
 ::user-select
 (create-rc-handler
  (fn [data params]
    (log/info "User View: " params)
    {:user-select params :make-user 55})))


(re-frame/reg-event-db
 ::content-panel-sel
 (create-rc-handler
  (fn [data params]
    ;; Expect data {:ha 1}
    (log/info "Content View; " params)
    {:conent-panel params :make-user 55})))


(defn login [path]
  (let [path-head (first path)
        path-tail (rest path)]
    [:div [:h1 "Login"]]))

;; Main layout
(defn side-panel [path]
  (let [path-head (first path)
        path-tail (rest path)]
    [:div [:h1 "Side Panel"]]))

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


(defn make-handler [event-id data parent]
  {:event event-id
   :data (assoc data :parent parent)})

(defn leaderboard-root [path parent-handler]
  (let [lboards ["lb1" "lb2"]]
    [list-display lboards (make-handler ::lboard-select
                                        {:id 33}
                                        parent-handler)]))

;;TODO: Some methods for building these dispatch chains
(defn users-root [path parent-handler]
  (let [users ["user1", "user2"]]
    [list-display users (make-handler ::user-select
                                      {:id 69}
                                      parent-handler)]))

;; (defn leaderboard-root [path handler]
;;   (let [lboards ["lb1" "lb2"]]
;;     ;; I want t handle events from list-display
;;     [list-display lboards {:event ::lboard-select
;;                            :data {:id 99
;;                                   :parent handler}}]))

;; ;;TODO: Some methods for building these dispatch chains
;; (defn users-root [path handler]
;;   (let [users ["user1", "user2"]]
;;     ;; I also want to handle events from list-display
;;     [list-display users {:event ::user-select
;;                          :data {:id 69
;;                                 :parent handler}}]))


(defn content-panel [path]
  (let [path-head (first path)
        path-tail (rest path)
        handler {:event ::content-panel-sel :data {:ha 1}}]
    (log/info path-head)
    (case path-head
          :leaderboards (leaderboard-root path-tail handler)
          :users (users-root path-tail handler))))

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
      :login (login path-tail)
      ))
  ;;(let [
  ;;      ;;name (re-frame/subscribe [::subs/name])
  ;;      ]
  ;;  [:div
  ;;   [:h1 "Cheese" (str ctr)]
  ;;   ])
  )
