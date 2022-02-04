(ns primary.main
  (:require
   [primary.nested.core :as nested-core]))


(defn router [event context callback]
  ;;Works as a router for all of our types
  (case (:InputType event)
    "Type1" (nested-core/handler event context callback)
    (callback "TypeNotSuppored")))
