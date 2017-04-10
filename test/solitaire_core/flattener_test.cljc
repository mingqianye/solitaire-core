(ns solitaire-core.flattener-test
  (:require [clojure.test :refer :all]
            [solitaire-core.flattener :refer :all]))

(deftest test-pile-to-list
  (testing "test if a pile can be converted to a list"
    (is (= [{:card-id 2 :suit "s1" :rank "r1" :pile-name "pn" :index-in-pile 0}
            {:card-id 4 :suit "s2" :rank "r2" :pile-name "pn" :index-in-pile 1}]
           (pile-to-list {:cards [{:card-id 2 :suit "s1" :rank "r1"}
                                  {:card-id 4 :suit "s2" :rank "r2"}]
                          :pile-name "pn"})))))

(deftest test-game-to-list
  (testing "test if a game can be converted to a list"
    (let [game {:stock [{:card-id 99 :suit :heart :rank 4 :pile-name :stock :index-in-pile 0}]
                :tableau-1-face-up [{:card-id 50 :suit :club :rank 9 :pile-name :tableau-1-face-up :index-in-pile 0}]}]
      (is (= [{:card-id 50 :suit :club  :rank 9 :pile-name :tableau-1-face-up :index-in-pile 0}
              {:card-id 99 :suit :heart :rank 4 :pile-name :stock :index-in-pile 0}]
             (game-to-list game)))
      (is (= game (-> game (game-to-list) (list-to-game)))))))

(deftest test-list-to-game
  (testing "Test if a list of cards can be converted to a game"
    (let [card-list [{:card-id 3 :suit :heart :rank 4 :pile-name :stock :index-in-pile 0}
                     {:card-id 4 :suit :club  :rank 9 :pile-name :tableau-1-face-up :index-in-pile 0}]]
      (is (= {:stock [{:card-id 3 :suit :heart :rank 4 :pile-name :stock :index-in-pile 0}]
              :tableau-1-face-up [{:card-id 4 :suit :club :rank 9 :pile-name :tableau-1-face-up :index-in-pile 0}]}
             (list-to-game card-list)))
      (is (= card-list (-> card-list (list-to-game) (game-to-list)))))))



