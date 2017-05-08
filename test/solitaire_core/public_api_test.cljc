(ns solitaire-core.public-api-test
  (:require [clojure.test :refer :all]
            [solitaire-core.public-api :refer :all]))

(deftest test-new-game
  (testing "test if can create a new game as list of cards"
    (is (= 52 (count (new-game {:level-name :shuffled}))))))

(deftest test-unshuffled-game-is-not-won
  (testing "test if can create a unshuffled game as list of cards"
    (is (= false (won? (new-game {:level-name :unshuffled}))))))

(deftest test-new-game-is-not-won
  (testing "test if a new game is not won, is stable, and can be selected from tableau"
    (is (= false (won? (new-game {:level-name :shuffled}))))
    (is (= true (can-be-selected? {:m (new-game {:level-name :shuffled}) 
                                   :i 0 
                                   :from :tableau-1-face-up})))))
