(ns solitaire-core.game-generator-test
  (:require [clojure.test :refer :all]
            [solitaire-core.game-generator :refer :all]))

(deftest new-game-contains-52-cards
  (testing "Test if a new game contains 52 cards"
    (let [game (new-game)]
    (is (= (-> game :stock count) 24))
    (is (= (-> game :waste count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :foundation (nth 0) count) 0))
    (is (= (-> game :tableau (nth 0) :face-down count) 0))
    (is (= (-> game :tableau (nth 1) :face-down count) 1))
    (is (= (-> game :tableau (nth 2) :face-down count) 2))
    (is (= (-> game :tableau (nth 3) :face-down count) 3))
    (is (= (-> game :tableau (nth 4) :face-down count) 4))
    (is (= (-> game :tableau (nth 5) :face-down count) 5))
    (is (= (-> game :tableau (nth 6) :face-down count) 6))
    (is (= (-> game :tableau (nth 0) :face-up count) 1))
    (is (= (-> game :tableau (nth 1) :face-up count) 1))
    (is (= (-> game :tableau (nth 2) :face-up count) 1))
    (is (= (-> game :tableau (nth 3) :face-up count) 1))
    (is (= (-> game :tableau (nth 4) :face-up count) 1))
    (is (= (-> game :tableau (nth 5) :face-up count) 1))
    (is (= (-> game :tableau (nth 6) :face-up count) 1)))))
