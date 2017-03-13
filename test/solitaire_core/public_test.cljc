(ns solitaire-core.public-test
  (:require [clojure.test :refer :all]
            [solitaire-core.public :refer :all]))

(deftest test-new-game
  (testing "test if can create a new game as list of cards"
    (is (= 52 (count (new-game))))))

(deftest test-new-game-is-not-won
  (testing "test if a new game is not won, is stable, and can be selected from tableau"
    (is (= false (won? (new-game))))
    (is (= true (is-stable? (new-game))))
    (is (= true (can-be-selected? {:m (new-game) :i 0 :from :tableau-1-face-up})))))
