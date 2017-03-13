(ns solitaire-core.ui-mapper-test
  (:require [clojure.test :refer :all]
            [solitaire-core.ui-mapper :refer :all]))

(deftest test-card-to-list-item
  (testing "test if a card can be converted to a list item"
    (is (= {:suit "s" :rank "r" :pile-name "pn" :index "i"}
           (card-to-list-item {:card {:suit "s" :rank "r"} :pile-name "pn" :index "i"})))))

(deftest test-pile-to-list
  (testing "test if a pile can be converted to a list"
    (is (= [{:suit "s1" :rank "r1" :pile-name "pn" :index 0}
            {:suit "s2" :rank "r2" :pile-name "pn" :index 1}]
           (pile-to-list {:cards [{:suit "s1" :rank "r1"}
                                  {:suit "s2" :rank "r2"}]
                          :pile-name "pn"})))))

(deftest test-game-to-list
  (testing "test if a game can be converted to a list"
    (let [game {:stock [{:suit :heart :rank 4}]
                :tableau-1-face-up [{:suit :club :rank 9}]}]
      (is (= [{:suit :heart :rank 4 :pile-name :stock :index 0}
              {:suit :club  :rank 9 :pile-name :tableau-1-face-up :index 0}]
             (game-to-list game))))))



(deftest test-game-to-ui
  (testing "Test if a new deck contains 52 cards"
    (is (= (game-to-ui "blah") true))))

