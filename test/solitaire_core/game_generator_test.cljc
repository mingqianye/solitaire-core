(ns solitaire-core.game_generator_test
  (:require [clojure.test :refer :all]
            [solitaire-core.game_generator :refer :all]))

(deftest new-game-contains-52-cards
  (testing "Test if a new game contains 52 cards"
    (let [game (new-game)]
    (is (= (-> game :stock count) 24))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :tableau (nth 0) count) 1))
    (is (= (-> game :tableau (nth 1) count) 2))
    (is (= (-> game :tableau (nth 2) count) 3))
    (is (= (-> game :tableau (nth 4) count) 5))
    (is (= (-> game :tableau (nth 5) count) 6))
    (is (= (-> game :tableau (nth 6) count) 7)))))
