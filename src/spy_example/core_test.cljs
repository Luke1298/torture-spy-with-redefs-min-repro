(ns spy-example.core-test
  (:require [clojure.test :refer [deftest testing is]]
            [spy.core :as spy]))

(def beatle->email
  {:john   "john.lennon@beatles.com"
   :paul   "paul.mccartney@beatles.com"
   :george "george.harrison@beatles.com"
   :ringo  "ringo.starr@beatles.com"})

(defn lookup-email [beatle-id]
  (get beatle->email beatle-id))

(defn send-message [email message]
  (println (str "Sending " message " to " email))
  nil)

(defn email-beatle [beatle-id message]
  (when-let [email (lookup-email beatle-id)]
    (send-message email message)))

(deftest email-beatle-test
  (testing "A message is sent to a Beatle"
    ;; example 1 - wrap the original fn (so it is still called)
    (with-redefs [send-message (spy/spy send-message)]
      (email-beatle :ringo "Hello Ringo!")
      (is (spy/called-once? send-message))
      (is (spy/called-with? send-message "ringo.starr@beatles.com" "Hello Ringo!"))))

  (testing "A message is not sent to a Rolling Stone"
    ;; example 2 - call spy without passing a fn (to avoid sending the email)
    (with-redefs [send-message (spy/spy)]
      (email-beatle :mick "Hello Mr Jagger!")
      (is (spy/not-called? send-message)))))
