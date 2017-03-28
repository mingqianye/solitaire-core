(ns solitaire-core.flattener)

(defn pile-to-list [{:keys [cards pile-name]}]
  (map-indexed (fn [idx itm] (merge itm {:pile-name pile-name :index idx})) cards))
    
(defn game-to-list [game]
  (->> game
    (map (fn [[k v]] (pile-to-list {:cards v :pile-name k})))
    (flatten)
    (sort-by :card-id)))


(defn list-to-game [card-list]
  (->> card-list
      (group-by :pile-name)
      (map (fn [[pile-name card-list]] {pile-name (sort-by :index card-list)}))
      (apply merge)))

