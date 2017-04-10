(ns solitaire-core.levels-test
  (:require [clojure.test :refer :all]
            [solitaire-core.levels :refer :all]))

; count number of cards in a game
(defn num-of-cards [game]
  (->> game
  (map (fn [[k v]] (count v)))
  (apply +)))

(deftest unshuffled-level-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (num-of-cards (get-level {:level-name :unshuffled})) 52))))

(deftest shuffled-level-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (num-of-cards (get-level {:level-name :shuffled})) 52))))

(deftest aces-in-tableau-piles-level-contains-52-cards
  (testing "Test if a new deck contains 52 cards"
    (is (= (num-of-cards (get-level {:level-name :aces-in-tableau-piles})) 4))))
