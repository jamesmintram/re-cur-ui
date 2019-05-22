(ns refer.util.router
  (:require [bidi.bidi :as bidi]
            [goog.History :as h]
            [goog.events :as e]
            [clojure.string :as s]
            [clojure.core :as c])
  (:import [goog History]))

(defprotocol Router
  (set-location! [_ location])
  (replace-location! [_ location]))

;;-------------------------------------------------------------

(defn unify-string [schema-type url-value pair]
  (if (= schema-type url-value)
    [:ok [url-value (keyword url-value)]]
    [:error pair]))

(defn unify-integer [schema-type url-value pair]
  [:ok [:integer (js/parseInt url-value)]])

(defn unify-pair [pair]
  "
    Takes:
      [schema-type url-value]

      e.g [:integer 32]
          [:string 'stringval']
          ['schema-key' :schema-key]

    Returns
      [:ok [schema-key url-value]]
    or
      [:error [..some info]]
  "
  (let [[schema-type url-value] pair]
    (cond
      (c/string? schema-type)  (unify-string schema-type url-value pair)
      (= :integer schema-type) (unify-integer schema-type url-value pair)
      ;;Add new conds here
      )))

(defn unify-pairs [pairs]
  (map unify-pair pairs))

;;-------------------------------------------------------------

(defn group-results [unified-pairs]
  "
    Takes:
     [[:ok val1] [:ok val2] [:error val3]]
    Returns:
     {:ok [val1 val2] :error [val3]}
  "
  (reduce (fn [m kv-pair]
            (let [key (first kv-pair)
                  val (second kv-pair)]
              (update-in m [key] #(conj % val))))
          {}
          unified-pairs))

(comment
  (group-results [[:ok [:result1]] [:ok [:result2]] [:error [:err1]]])
  (group-results [[:ok [:result1]]])
  (group-results [[:error [:err1]]])
  (group-results [])
)


(defn find-valid-path-entry [url-schema url-head]
  (let [schema-keys   (keys url-schema)
        schema-pairs  (zipmap schema-keys (repeat url-head))
        unified-pairs (unify-pairs schema-pairs)

        results (group-results unified-pairs)

        valid-pairs (:ok results)
        invalid-pairs (:error results)

        ;; Check that there is only 1 that we can use
        ;;TODO(JAMES): Failure case, multiple matches
        ;;TODO(JAMES): Failure case, no matches
        unified-value (first valid-pairs)]

    unified-value))


(defn validate-url [url-schema url]
  (let [url-head      (first url)
        unified-value (find-valid-path-entry url-schema url-head)
        unified-type  (first unified-value)
        unified-value (second unified-value)]

    (println unified-type)
    (println unified-value)

    (when-let [sub-path (get url-schema unified-type nil)]
      (validate-url sub-path (rest url)))))


;;-------------------------------------------------------------


(comment
  "
   URL Schema
    /users/:int/name         ['users' :integer 'name']
    /users/:int/name/:name   ['users' :integer 'name' :string]
  "
  (let [easy-tree {"users" {:integer {"name" {:leaf   nil
                                              :string {}}}
                            "age"    {}}}
        valid-url ["users" 22 "name"]
        invalid-url ["users" "not int" "name"]]

    ;; Validate the urls
    (validate-url easy-tree valid-url)
    (validate-url easy-tree invalid-url))

  )

(defn start-router!
  "Starts up a Bidi router based on Google Closure's 'History'

  Types:

    Location :- {:handler ...
                 :route-params {...}}

  Parameters:

    routes :- a Bidi route structure
    on-navigate :- 0-arg function, accepting a Location
    default-location :- Location to default to if the current token doesn't match a route

  Returns :- Router

  "

  [routes {:keys [on-navigate default-location]
           :or {on-navigate (constantly nil)}}]


  (let [history (History.)]
    (.setEnabled history true)

    (letfn [(token->location [path]
              ;;TODO(James): Better way to parse URL into components
              (let [not-blank? (comp not clojure.string/blank?)
                    split (clojure.string/split path #"/")
                    path-components (filter not-blank? split)
                    path-components (map keyword path-components)]
                path-components))

            (location->token [path-components]
              ;;TODO(James): Better way to generate URL from components
              (clojure.string/join (interpose "/" path-components)))]

      (e/listen history h/EventType.NAVIGATE
                (fn [e]
                  (on-navigate (token->location (.-token e)))))

      (let [initial-token (let [token (.getToken history)]
                            (if-not (s/blank? token)
                              token
                              (or (location->token default-location) "/")))

            initial-location (token->location initial-token)]

        (.replaceToken history initial-token)
        (on-navigate initial-location))

      (reify Router
        (set-location! [_ location]
          (.setToken history (location->token location)))

        (replace-location! [_ location]
          (.replaceToken history (location->token location)))))))