(ns solitaire-core.game-command-test
  (:require [clojure.test :refer :all]
            [solitaire-core.game-command :refer :all]
            [solitaire-core.game-policy :refer :all]))

(deftest test-refresh-waste
  (testing "Test if can refresh 0 cards from stock to waste"
    (let [game {:stock [] :waste []}]
      (is (= {:stock [] :waste []} (refresh-waste game)))))
  (testing "Test if can refresh 1 cards from stock to waste"
    (let [game {:stock [:a] :waste []}]
      (is (= {:stock [] :waste [:a]} (refresh-waste game)))))
  (testing "Test if can refresh 1 cards from waste to stock and back to waste"
    (let [game {:stock [] :waste [:a]}]
      (is (= {:stock [:a] :waste []} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from waste to stock and back to waste"
    (let [game {:stock [] :waste [:a :b]}]
      (is (= {:stock [:b :a] :waste []} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from stock to waste"
    (let [game {:stock [:a :b] :waste []}]
      (is (= {:stock [] :waste [:b :a]} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from stock to waste"
    (let [game {:stock [:a :b] :waste [:c]}]
      (is (= {:stock [] :waste [:c :b :a]} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from stock to waste"
    (let [game {:stock [:a :b :c :d] :waste [:e :f :g]}]
      (is (= {:stock [:a] :waste [:e :f :g :d :c :b]} (refresh-waste game)))))
  (testing "Test if can restore all cards from waste to stock"
    (let [game {:stock [] :waste [:e :f :g :d :c :b :a]}]
      (is (= {:stock [:a :b :c :d :g :f :e] :waste []} (refresh-waste game))))))

(deftest test-move
  (testing "Test if can move ith object from [from-path] to [to-path]"
    (let [random-map {:a [12 33 44 23 49] :c [5 6 7 8]}]
      (is (= {:a [12 33] :c [5 6 7 8 44 23 49]} (move {:m random-map :i 2 :from :a :to :c}))))))

(deftest test-stablize-one
  (testing "Test if can stablize one tableau column"
    (is (= {:a [3] :b [1 2]} (stablize-one {:a [] :b [1 2 3]} :a :b)))
    (is (= {:a [1] :b [1 2 3]} (stablize-one {:a [1] :b [1 2 3]} :a :b)))
    (is (= {:a [] :b []} (stablize-one {:a [] :b []} :a :b)))
      ))

(deftest test-stablize
  (testing "Test if can stablize one tableau column"
    (let [game {:tableau-1-face-up [] :tableau-1-face-down [1 2 3]
                :tableau-2-face-up [1] :tableau-2-face-down [1 2 3]
                :tableau-3-face-up [] :tableau-3-face-down []
                :tableau-4-face-up [4 4] :tableau-4-face-down [1 2 3]
                :tableau-5-face-up [] :tableau-5-face-down [1]
                :tableau-6-face-up [2] :tableau-6-face-down []
                :tableau-7-face-up [33] :tableau-7-face-down [1 2 3]}]
    (is (= (stablize game) {:tableau-1-face-up [3] :tableau-1-face-down [1 2]
                            :tableau-2-face-up [1] :tableau-2-face-down [1 2 3]
                            :tableau-3-face-up [] :tableau-3-face-down []
                            :tableau-4-face-up [4 4] :tableau-4-face-down [1 2 3]
                            :tableau-5-face-up [1] :tableau-5-face-down []
                            :tableau-6-face-up [2] :tableau-6-face-down []
                            :tableau-7-face-up [33] :tableau-7-face-down [1 2 3]})))))
