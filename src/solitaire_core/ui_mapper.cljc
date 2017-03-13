(ns solitaire-core.ui-mapper)

(defn card-to-list-item [{:keys [card pile-name index]}]
  {:suit (:suit card)
   :rank (:rank card)
   :pile-name pile-name
   :index index})

(defn pile-to-list [{:keys [cards pile-name]}]
  (map-indexed (fn [idx itm] (card-to-list-item {:card itm :pile-name pile-name :index idx})) cards))
    
(defn game-to-list [game]
  (->> game
    (map (fn [[k v]] (pile-to-list {:cards v :pile-name k})))
    (flatten)))


  
(defn game-to-ui [game]
  true)
