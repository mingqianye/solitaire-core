(ns solitaire-core.game-policy-test
  (:require [clojure.test :refer :all]
            [solitaire-core.game-generator :refer :all]
            [solitaire-core.game-policy :refer :all]))

(deftest new-game-is-not-won
  (testing "Test if a new game is not won"
    (is (false? (won? (new-game))))))

(deftest test-can-move-last?
  (testing "number elements in from-path"
    (is (true? (can-move-last?  :m {:a [1 2] :b []} :n 1 :from [:a] :to [:b])))
           
           
           ))
