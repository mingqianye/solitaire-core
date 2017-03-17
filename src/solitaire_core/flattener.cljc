(ns solitaire-core.flattener)

(defn card-to-list-item [{:keys [card pile-name index]}]
  {:suit      (:suit card)
   :rank      (:rank card)
   :card-id   (:card-id card)
   :pile-name pile-name
   :index     index})

(defn pile-to-list [{:keys [cards pile-name]}]
  (map-indexed (fn [idx itm] (card-to-list-item {:card itm :pile-name pile-name :index idx})) cards))
    
(defn game-to-list [game]
  (->> game
    (map (fn [[k v]] (pile-to-list {:cards v :pile-name k})))
    (flatten)))


(defn list-to-game [card-list]
  (let [item-to-card (fn [item] {:card-id (:card-id item) :suit (:suit item) :rank (:rank item)})
        pile-to-cards (fn [pile] (->> pile (sort-by :index) (map item-to-card)))]
    (->> card-list
      (group-by :pile-name)
      (map (fn [[pile-name card-list]] {pile-name (pile-to-cards card-list)}))
      (apply merge))))

