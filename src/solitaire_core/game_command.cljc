(ns solitaire-core.game-command
  )


(def refresh-waste
  (let [copy-from-waste-to-stock (fn [g] (update-in g [:stock] #(concat (reverse (:waste g)) %)))
        clear-waste              (fn [g] (assoc-in g [:waste] []))
        copy-from-stock-to-waste (fn [g] (assoc-in g [:waste] (reverse (take-last 3 (:stock g)))))
        remove-from-stock        (fn [g] (update-in g [:stock] #(drop-last 3 %)))]
    (comp
       remove-from-stock
       copy-from-stock-to-waste
       clear-waste
       copy-from-waste-to-stock
     )))

(defn move [{:keys [m n from to]}]
  "(m n from-key to-key)"
  (let [
        new_from (drop-last n (get m from))
        new_to   (concat (get m to) (take-last n (get m from)))
        ]
    (-> m
      (assoc-in [from] new_from)
      (assoc-in [to] new_to))))
