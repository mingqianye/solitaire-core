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

(defn move [{:keys [m i from to]}]
  "input: {:m game :i index-in-pile :from from-key :to to-key}"
  (let [
        cut-len  (- (count (get m from)) i)
        new_from (take i (get m from))
        new_to   (concat (get m to) (take-last cut-len (get m from)))
        ]
    (-> m
      (assoc-in [from] new_from)
      (assoc-in [to] new_to))))

(defn stablize-one [game up-pile-name down-pile-name]
  (let [up-pile   (up-pile-name game)
        down-pile (down-pile-name game)]
    (if (and (empty? up-pile) (not (empty? down-pile)))
      (-> game
        (assoc-in [up-pile-name] (take-last 1 down-pile))
        (assoc-in [down-pile-name] (drop-last 1 down-pile)))
      game)))
      

(defn stablize [game]
  (-> game
    (stablize-one :tableau-1-face-up :tableau-1-face-down)
    (stablize-one :tableau-2-face-up :tableau-2-face-down)
    (stablize-one :tableau-3-face-up :tableau-3-face-down)
    (stablize-one :tableau-4-face-up :tableau-4-face-down)
    (stablize-one :tableau-5-face-up :tableau-5-face-down)
    (stablize-one :tableau-6-face-up :tableau-6-face-down)
    (stablize-one :tableau-7-face-up :tableau-7-face-down)))
