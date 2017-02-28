(ns solitaire-core.game-policy
  )

(defn won? [game]
  (-> game
      :foundation
      flatten
      count
      (= 52)
      ))

(defn from-has-at-least-n-element? [& {:keys [m n from]}]
  (>= (count (get-in m from)) n))

(defn from-valid-piles? [& {:keys [from]}]
  (let [valid-piles #{[:waste] [:foundation-1] [:foundation-2] [:foundation-3] [:foundation-4]
                      [:tableau-1 :face-up] [:tableau-2 :face-up] [:tableau-3 :face-up] [:tableau-4 :face-up]
                      [:tableau-5 :face-up] [:tableau-6 :face-up] [:tableau-7 :face-up]}]
  (contains? valid-piles from)))

(defn to-valid-piles? [& {:keys [to]}]
  (let [valid-piles #{[:foundation-1] [:foundation-2] [:foundation-3] [:foundation-4]
                      [:tableau-1 :face-up] [:tableau-2 :face-up] [:tableau-3 :face-up] [:tableau-4 :face-up]
                      [:tableau-5 :face-up] [:tableau-6 :face-up] [:tableau-7 :face-up]}]
  (contains? valid-piles to)))

(defn from-to-are-different? [& {:keys [from to]}]
  (not= from to))

(defn can-stack-cards? [& {:keys [top-card bottom-card]}]
  (let [stack-rule {:spade   #{:diamond :heart}
                    :club    #{:diamond :heart}
                    :diamond #{:spade :club}
                    :heart   #{:spade :club}   }
        possible-top-card-suit ((:suit bottom-card) stack-rule)]
  (and
    (= (:number top-card) (dec (:number bottom-card)))
    (contains? possible-top-card-suit (:suit top-card)))))


(defn can-move-last? [& {:keys [m n from to]}]
  "(m n [from-path] [to-path])"
  ((every-pred from-has-at-least-n-element? from-valid-piles?) {:m m :n n :from from :to to}) )
