(ns solitaire-core.game-policy-test
  (:require [clojure.test :refer :all]
            [solitaire-core.game-generator :refer :all]
            [solitaire-core.game-policy :refer :all]))

(deftest new-game-is-not-won
  (testing "Test if a new game is not won"
    (is (false? (won? (new-game))))))

(deftest test-from-has-at-least-n-element?
  (testing "Test if a new game is not won"
    (is (true? (from-has-at-least-n-element? {:m {:a {:b [1 2 3]}} :n 2 :from [:a :b]})))
    (is (false? (from-has-at-least-n-element? {:m {:a {:b [1 2 3]}} :n 4 :from [:a :b]})))))

(deftest test-from-valid-piles?
  (testing "Test if is from valid piles"
    (is (true? (from-valid-piles? {:from [:waste]})))
    (is (true? (from-valid-piles? {:from [:tableau-3 :face-up]})))
    (is (true? (from-valid-piles? {:from [:foundation-2]})))
    (is (false? (from-valid-piles? {:from [:stock]})))
    (is (false? (from-valid-piles? {:from [:tableau-2 :face-down]})))))

(deftest test-to-valid-piles?
  (testing "Test if is to valid piles"
    (is (true? (to-valid-piles?  {:to [:tableau-3 :face-up]})))
    (is (true? (to-valid-piles?  {:to [:foundation-2]})))
    (is (false? (to-valid-piles? {:to [:stock]})))
    (is (false? (to-valid-piles? {:to [:tableau-2 :face-down]})))))

(deftest test-from-to-are-different?
  (testing "Test if is to valid piles"
    (is (true? (from-to-are-different? {:from [:a :b] :to [:b]})))
    (is (false? (from-to-are-different? {:from [:a :b] :to [:a :b]})))))

(deftest test-can-stack-two-cards?
  (testing "Test if two cards can be stacked together"
    (is (true? (can-stack-two-cards? {:top-card {:suit :spade :number 1} :bottom-card {:suit :diamond :number 2}})))
    (is (true? (can-stack-two-cards? {:top-card {:suit :heart :number 4} :bottom-card {:suit :club :number 5}})))
    (is (false? (can-stack-two-cards? {:top-card {:suit :diamond :number 4} :bottom-card {:suit :spade :number 6}})))
           ))

(deftest test-can-stack-two-piles?
  (testing "Test if two piles can be stacked together"
    (let [pile-1 [{:suit :spade :number 7} {:suit :diamond :number 6}]
          pile-2 [{:suit :spade :number 9} {:suit :heart :number 8}]
          pile-3 [{:suit :spade :number 8} {:suit :heart :number 7}]]
    
    (is (true? (can-stack-two-piles? {:m {:a pile-1 :b pile-2} :n 2 :from [:a] :to [:b]})))
    (is (false? (can-stack-two-piles? {:m {:a pile-1 :b pile-2}  :n 1 :from [:a] :to [:b]})))
    (is (false? (can-stack-two-piles? {:m {:a pile-1 :b pile-3} :n 1 :from [:a] :to [:b]}))))))

(deftest test-can-move?
  (testing "number elements in from-path"
    (let [pile-1 [{:suit :spade :number 7} {:suit :diamond :number 6}]
          pile-2 [{:suit :spade :number 9} {:suit :heart :number 8}]
          pile-3 [{:suit :spade :number 8} {:suit :heart :number 7}]]
      (is (true? (can-move? {:m {:tableau-1 {:face-up pile-2} :tableau-2 {:face-up pile-1}}
                              :n 2 
                              :from [:tableau-2 :face-up] 
                              :to [:tableau-1 :face-up]}))) 
      (is (false? (can-move? {:m {:tableau-1 {:face-up pile-2} :tableau-2 {:face-up pile-1}}
                              :n 1 
                              :from [:tableau-2 :face-up] 
                              :to [:tableau-1 :face-up]}))) 
           )))
