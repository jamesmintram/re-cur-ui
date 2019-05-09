(ns refer.util.route)

(defn make-route [path]
  {:ancestors []
   :current (first path)
   :descendents (rest path)
   :all path})

(defn step [route]
  (if (:current route)
    {:ancestors (conj (:ancestors route) (:current route))
     :current (first (:descendents route))
     :descendents (rest (:descendents route))
     :all (:all route)}

    route))

(comment

  ;;We can create a route
  (make-route [:main :users 23 :profile])

  ;;We can step through once
  (->
   (make-route [:main :users 23 :profile])
   (step))

  ;;We can step through more than once
  (->
   (make-route [:main :users 23 :profile])
   (step)
   (step))

  ;; Check that we cannot overrun the route
  (->
   (make-route [:main :users 23 :profile])
   (step)
   (step)
   (step)
   (step)
   (step)
   (step)
   (step))
)
