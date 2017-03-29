(ns solitaire-core.public
  (:require [solitaire-core.game-generator :as game-generator]
            [solitaire-core.game-command :as game-command]
            [solitaire-core.game-policy :as game-policy]
            [solitaire-core.flattener :refer [game-to-list list-to-game]]
  ))

(defn new-game []
  (-> (game-generator/new-game)
      (game-to-list)))

(defn refresh-waste [list-game]
  (-> list-game 
      (list-to-game) 
      (game-command/refresh-waste) 
      (game-to-list)))

(defn move [{:keys [m i from to]}]
  "input: {:m list-game :i index-in-pile :from from-key :to to-key}"
  (-> {:m (list-to-game m) :i i :from from :to to}
      (game-command/move)
      (game-to-list)))

(defn stablize [list-game]
  (-> list-game 
      (list-to-game) 
      (game-command/stablize) 
      (game-to-list)))

(defn won? [list-game]
  (-> list-game
      (list-to-game)
      (game-policy/won?)))

(defn is-stable? [list-game]
  (-> list-game
      (list-to-game)
      (game-policy/is-stable?)))

(defn can-be-selected? [{:keys [m i from]}]
  (-> {:m (list-to-game m) :i i :from from}
      (game-policy/can-be-selected?)))

(defn can-move? [{:keys [m i from to]}]
  (-> {:m (list-to-game m) :i i :from from :to to}
      (game-policy/can-move?)))

