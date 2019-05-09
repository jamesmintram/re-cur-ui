(ns refer.util.context
  (:require
   [re-frame.core :as re-frame]
   [taoensso.timbre :as log]))

(def empty-context nil)

(defn push [parent event-id props]
  {:event event-id
   :data (assoc props :parent parent)})

(defn create-handler [dispatch processor]
  (fn [_db args]
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
        (dispatch [parent-event parent-args])))))


(def create-rc-handler (partial create-handler re-frame/dispatch))

;; Can we make the API nicer?

(comment

  ;;We can push contexts
  (->
   empty-context
   (push ::event-id {:id 1})
   (push ::event-id {:id 2})
   (push ::event-id {:id 3}))

  ;; We can partially apply params, and create
  ;; multiple child contexts
  (let [ctx (partial push ::event-id)]
    [(ctx {:id :curry1})
     (ctx {:id :curry2})
     (ctx {:id :curry3})
     ])

  (create-dispatcher )
  )
