(ns refer.domain.users
  (:require
   [taoensso.timbre :as log]))

(defn create-user [db user]
  (assoc-in db [:users (:id user)] user))

(defn users [db]
  (let [vals (vals (get-in db [:users]))]
    vals))
