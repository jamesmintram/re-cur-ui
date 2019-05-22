# TODO

- Do something useful
-- Generate a URL for selecting a user to view
-- Subscription for users
-- Add a new user

- Create some links for nav

- Try to bubble all in 1 epoch 
- First entry for dispatch manually uses list (list-view)
   - Refactor everything to work like this



# NOTES
main.cljs -> fix up the path parsing

- Domain state
-- Stored independant of any component
-- Available users

- Component State
-- Stored relative to the component's position in the tree
-- Selected item in a list

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
