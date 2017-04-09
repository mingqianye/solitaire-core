(ns solitaire-core.public-api
  (:require [solitaire-core.game-command :as game-command]
            [solitaire-core.game-policy :as game-policy]
            [solitaire-core.levels :refer [get-level]]
            [solitaire-core.flattener :refer [game-to-list list-to-game]]
  ))

(defn new-game [{:keys [level-name]}]
  (-> (get-level {:level-name level-name})
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

(defn won? [list-game]
  (-> list-game
      (list-to-game)
      (game-policy/won?)))

(defn can-be-selected? [{:keys [m i from]}]
  (-> {:m (list-to-game m) :i i :from from}
      (game-policy/can-be-selected?)))

(defn can-move? [{:keys [m i from to]}]
  (-> {:m (list-to-game m) :i i :from from :to to}
      (game-policy/can-move?)))

