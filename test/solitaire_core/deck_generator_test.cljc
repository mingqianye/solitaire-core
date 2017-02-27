(ns solitaire-core.deck-generator-test
  (:require [clojure.test :refer :all]
            [solitaire-core.deck-generator :refer :all]))

(deftest new-deck-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (count (new-deck)) 52))))

(deftest random-deck-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (count (random-deck)) 52))))
