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


