(ns solitaire-core.game-policy-test
  (:require [clojure.test :refer :all]
            [solitaire-core.game-generator :refer :all]
            [solitaire-core.game-policy :refer :all]))

(deftest new-game-is-not-won
  (testing "Test if a new game is not won"
    (is (false? (won? (new-game))))))

(deftest test-from-has-at-least-n-element?
  (testing "Test if a new game is not won"
    (is (true? (from-has-at-least-n-element? {:m {:a [1 2 3]} :n 2 :from :a })))
    (is (false? (from-has-at-least-n-element? {:m {:a [1 2 3]} :n 4 :from :b})))))

(deftest test-can-stack-in-foundation?
  (testing "Test if two cards can be stacked together"
    (is (true? (can-stack-in-foundation? {:top-card {:suit :spade :number 2} :bottom-card {:suit :spade :number 1}})))
    (is (true? (can-stack-in-foundation? {:top-card {:suit :heart :number 4} :bottom-card {:suit :heart :number 3}})))
    (is (false? (can-stack-in-foundation? {:top-card {:suit :diamond :number 4} :bottom-card {:suit :diamond :number 6}})))
    (is (false? (can-stack-in-foundation? {:top-card {:suit :heart :number 4} :bottom-card {:suit :diamond :number 3}})))
           ))

(deftest test-can-stack-in-tableau?
  (testing "Test if two cards can be stacked together"
    (is (true? (can-stack-in-tableau? {:top-card {:suit :spade :number 1} :bottom-card {:suit :diamond :number 2}})))
    (is (true? (can-stack-in-tableau? {:top-card {:suit :heart :number 4} :bottom-card {:suit :club :number 5}})))
    (is (false? (can-stack-in-tableau? {:top-card {:suit :diamond :number 4} :bottom-card {:suit :spade :number 6}})))
           ))

(deftest test-is-to-foundation?
  (testing "Test if is to foundation"
    (is (true? (is-to-foundation? {:m nil :n nil :from nil :to :foundation-2})))))

(deftest test-valid-from-waste-to-tableau?
  (testing "Test if is a valid move from waste to tableau"
    (is (true? (valid-from-waste-to-tableau? {:m {:waste [{:suit :spade :number 1}] 
                                                  :tableau-1-face-up [{:suit :heart :number 2}]}
                                              :n 1
                                              :from :waste
                                              :to :tableau-1-face-up})))
           
    (is (false? (valid-from-waste-to-tableau? {:m {:waste [{:suit :spade :number 1}] 
                                                  :tableau-1-face-up [{:suit :heart :number 1}]}
                                              :n 1
                                              :from :waste
                                              :to :tableau-1-face-up})))
           ))

(deftest test-valid-from-waste-to-foundation?
  (testing "Test if is a valid move from waste to tableau"
    (is (true? (valid-from-waste-to-foundation? {:m {:waste [{:suit :spade :number 1}] 
                                                     :foundation-2 []}
                                                 :n 1
                                                 :from :waste
                                                 :to :foundation-2})))
           
    (is (true? (valid-from-waste-to-foundation? {:m {:waste [{:suit :spade :number 2}] 
                                                     :foundation-2 [{:suit :spade :number 1}]}
                                                 :n 1
                                                 :from :waste
                                                 :to :foundation-2})))

    (is (false? (valid-from-waste-to-foundation? {:m {:waste [{:suit :spade :number 2} {:suit :heart :number 3}] 
                                                     :foundation-2 [{:suit :spade :number 1}]}
                                                 :n 2
                                                 :from :waste
                                                 :to :foundation-2})))

    (is (false? (valid-from-waste-to-foundation? {:m {:waste [{:suit :spade :number 2}] 
                                                     :foundation-2 [{:suit :heart :number 1}]}
                                                 :n 1
                                                 :from :waste
                                                 :to :foundation-2})))
           
    (is (false? (valid-from-waste-to-foundation? {:m {:waste [{:suit :spade :number 3}] 
                                                      :tableau-1-face-up [{:suit :heart :number 3}]}
                                                  :n 1
                                                  :from :waste
                                                  :to :tableau-1-face-up})))
           ))

(deftest test-can-stack-two-piles?
  (testing "Test if two piles can be stacked together"
    (let [pile-1 [{:suit :spade :number 7} {:suit :diamond :number 6}]
          pile-2 [{:suit :spade :number 9} {:suit :heart :number 8}]
          pile-3 [{:suit :spade :number 8} {:suit :heart :number 7}]]
    
    (is (true? (can-stack-two-piles? {:m {:a pile-1 :b pile-2} :n 2 :from :a :to :b})))
    (is (false? (can-stack-two-piles? {:m {:a pile-1 :b pile-2}  :n 1 :from :a :to :b})))
    (is (false? (can-stack-two-piles? {:m {:a pile-1 :b pile-3} :n 1 :from :a :to :b}))))))

(deftest test-can-move?
  (testing "number elements in from-path"
    (let [pile-1 [{:suit :spade :number 7} {:suit :diamond :number 6}]
          pile-2 [{:suit :spade :number 9} {:suit :heart :number 8}]
          pile-3 [{:suit :spade :number 8} {:suit :heart :number 7}]]
      (is (true? (can-move? {:m {:tableau-1-face-up pile-2 :tableau-2-face-up pile-1}
                              :n 2 
                              :from :tableau-2-face-up 
                              :to :tableau-1-face-up}))) 
      (is (false? (can-move? {:m {:tableau-1-face-up pile-2 :tableau-2-face-up pile-1}
                              :n 1 
                              :from :tableau-2-face-up 
                              :to :tableau-1-face-up}))) 
           )))
