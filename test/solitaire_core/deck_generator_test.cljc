(ns solitaire-core.deck_generator_test
  (:require [clojure.test :refer :all]
            [solitaire-core.deck_generator :refer :all]))

(deftest new-deck-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (count (new_deck)) 52))))

(deftest random-deck-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (count (random_deck)) 52))))
