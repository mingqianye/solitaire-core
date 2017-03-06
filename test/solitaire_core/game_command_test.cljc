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
      (is (= {:stock [] :waste [:a]} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from waste to stock and back to waste"
    (let [game {:stock [] :waste [:a :b]}]
      (is (= {:stock [] :waste [:a :b]} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from stock to waste"
    (let [game {:stock [:a :b] :waste []}]
      (is (= {:stock [] :waste [:b :a]} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from stock to waste"
    (let [game {:stock [:a :b] :waste [:c]}]
      (is (= {:stock [] :waste [:b :a :c]} (refresh-waste game)))))
  (testing "Test if can refresh 2 cards from stock to waste"
    (let [game {:stock [:a :b :c :d] :waste [:e :f :g]}]
      (is (= {:stock [:g :f :e :a] :waste [:d :c :b]} (refresh-waste game))))))

(deftest test-move
  (testing "Test if can move ith object from [from-path] to [to-path]"
    (let [random-map {:a [12 33 44 23 49] :c [5 6 7 8]}]
      (is (= {:a [12 33] :c [5 6 7 8 44 23 49]} (move {:m random-map :i 2 :from :a :to :c}))))))
