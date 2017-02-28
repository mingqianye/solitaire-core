(ns solitaire-core.game-policy)

(defn won? [game]
  (-> game
      :foundation
      flatten
      count
      (= 52)
      ))

(defn from-has-at-least-n-element? [{:keys [m n from]}]
  (>= (count (get m from)) n))

(defn from-valid-piles? [{:keys [from]}]
  (let [valid-piles #{:waste :foundation-1 :foundation-2 :foundation-3 :foundation-4
                      :tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
                      :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up}]
  (contains? valid-piles from)))

(defn to-valid-piles? [{:keys [to]}]
  (let [valid-piles #{:foundation-1 :foundation-2 :foundation-3 :foundation-4
                      :tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
                      :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up}]
  (contains? valid-piles to)))

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
              from-valid-piles?
              to-valid-piles?
              from-to-are-different?
              can-stack-two-piles?))

