(ns refer.pages.login)


(defn login [path]
  (let [path-head (first path)
        path-tail (rest path)]
    [:div [:h1 "Login"]]))
