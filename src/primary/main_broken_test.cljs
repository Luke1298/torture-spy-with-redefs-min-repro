(ns primary.main-broken-test
  (:require
   [clojure.test :refer [deftest testing is]]
   [spy.core :as spy]
   [spy.assert :as spy-assert]
   ;[primary.main :as sut]
   [primary.nested.core]))

(deftest routes-correctly
    (testing "Routes to primary.nested.core on 'AnswerVideoTranscript' type"
      (let [handler-spy (spy/spy (fn [] (do (println "I DID IT I CALLED THE SPY!") true)))]
        (with-redefs [primary.nested.core/handler handler-spy] ;;NOTE: Any other type of function as a redef works
          ;(println "Type: " (type primary.nested.core/handler))
          ;$~ Type:  cljs.core/MetaFn
          ;Not sure if comparing functions is really legal, but this holds:
          ;(println "Equality: " (= handler-spy primary.nested.core/handler))
          ;$~ Equality:  true

          ;What I want to do:
          ;(sut/router {:InputType "Type1"} nil nil)
          ;For sake of simplicity:
          (primary.nested.core/handler nil nil nil);; <-- TypeError: primary.nested.core.handler is not a function

          (spy-assert/called? handler-spy)))))
