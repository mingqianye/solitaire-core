(ns solitaire-core.game-generator-test
  (:require [clojure.test :refer :all]
            [solitaire-core.game-generator :refer :all]
            ))

(deftest new-game-contains-52-cards
  (testing "Test if a new game contains 52 cards"
    (let [game (new-game)]
    (is (= (-> game :stock count) 24))
    (is (= (-> game :waste count) 0))
    (is (= (-> game :foundation-1 count) 0))
    (is (= (-> game :foundation-2 count) 0))
    (is (= (-> game :foundation-3 count) 0))
    (is (= (-> game :foundation-4 count) 0))
    (is (= (-> game :tableau-1-face-down count) 0))
    (is (= (-> game :tableau-2-face-down count) 1))
    (is (= (-> game :tableau-3-face-down count) 2))
    (is (= (-> game :tableau-4-face-down count) 3))
    (is (= (-> game :tableau-5-face-down count) 4))
    (is (= (-> game :tableau-6-face-down count) 5))
    (is (= (-> game :tableau-7-face-down count) 6))
    (is (= (-> game :tableau-1-face-up count) 1))
    (is (= (-> game :tableau-2-face-up count) 1))
    (is (= (-> game :tableau-3-face-up count) 1))
    (is (= (-> game :tableau-4-face-up count) 1))
    (is (= (-> game :tableau-5-face-up count) 1))
    (is (= (-> game :tableau-6-face-up count) 1))
    (is (= (-> game :tableau-7-face-up count) 1)))))
