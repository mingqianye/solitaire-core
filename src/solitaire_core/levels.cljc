(ns solitaire-core.levels
  (:require [solitaire-core.game-generator :as game-generator])
  )

(defn all-levels []
  {:random (game-generator/new-game)
   :aces-in-tableau-piles
    {:stock []
     :waste []
     :foundation-1 []
     :foundation-2 []
     :foundation-3 []
     :foundation-4 []
     :tableau-1-face-down []
     :tableau-2-face-down []
     :tableau-3-face-down []
     :tableau-4-face-down []
     :tableau-5-face-down []
     :tableau-6-face-down []
     :tableau-7-face-down []
     :tableau-1-face-up [{:card-id 1 :suit :diamond :rank 1}]
     :tableau-2-face-up [{:card-id 2 :suit :spade :rank 1}]
     :tableau-3-face-up [{:card-id 3 :suit :heart :rank 1}]
     :tableau-4-face-up [{:card-id 4 :suit :club :rank 1}]
     :tableau-5-face-up []
     :tableau-6-face-up []
     :tableau-7-face-up []}
   })

(defn get-level [{:keys [level-name]}]
  (get (all-levels) level-name "level-name is not found/set!"))
  
