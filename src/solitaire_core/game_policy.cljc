(ns solitaire-core.game-policy)

(def to-piles
  #{:foundation-1 :foundation-2 :foundation-3 :foundation-4
    :tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
    :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up})

(def from-piles
  (conj to-piles :waste))

(defn won? [game]
  (-> game
    (juxt :foundation-1 :foundation-2 :foundation-3 :foundation-4)
    flatten
    count
    (= 52)))

(defn from-has-at-least-n-element? [{:keys [m n from]}]
  (>= (count (get m from)) n))

(defn valid-from-to-piles? [{:keys [from to]}]
  (and
    (contains? to-piles to)
    (contains? from-piles from)))

(defn from-to-are-different? [{:keys [from to]}]
  (not= from to))

(defn can-stack-two-cards? [{:keys [top-card bottom-card]}]
  (let [stack-rule {:spade   #{:diamond :heart}
                    :club    #{:diamond :heart}
                    :diamond #{:spade :club}
                    :heart   #{:spade :club}   }
        possible-top-card-suit ((:suit bottom-card) stack-rule)]
  (and
    (= (:number top-card) (dec (:number bottom-card)))
    (contains? possible-top-card-suit (:suit top-card)))))

(defn can-stack-two-piles? [{:keys [m n from to]}]
  (let [top-pile (get m from)
        top-card (get top-pile (- (count top-pile) n))
        bottom-pile (get m to)
        bottom-card (last bottom-pile)]
    (can-stack-two-cards? {:top-card top-card :bottom-card bottom-card})))

(def can-move? 
  "input: {:m game :n num-cards :from from-key :to to-key}"
  (every-pred from-has-at-least-n-element?
              valid-from-to-piles?
              from-to-are-different?
              can-stack-two-piles?))

