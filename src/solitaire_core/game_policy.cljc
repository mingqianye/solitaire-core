(ns solitaire-core.game-policy
  (:require [clojure.set :refer [union]]))

(def foundation-piles
  #{:foundation-1 :foundation-2 :foundation-3 :foundation-4})

(def tableau-face-up-piles
  #{:tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
    :tableau-5-face-up :tableau-6-face-up :tableau-7-face-u})

(defn won? [game]
  (-> game
    (juxt :foundation-1 :foundation-2 :foundation-3 :foundation-4)
    flatten
    count
    (= 52)))

(defn from-has-at-least-n-element? [{:keys [m n from]}]
  (>= (count (get m from)) n))

(defn is-to-foundation? [{:keys [to]}]
  (contains? foundation-piles to))


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
  (let [top-pile    (get m from)
        top-card    (get top-pile (- (count top-pile) n))
        bottom-pile (get m to)
        bottom-card (last bottom-pile)]
    (can-stack-two-cards? {:top-card top-card :bottom-card bottom-card})))

(defn from-waste-to-tableau? [{:keys [m n from to]}]
  true
  )

(defn from-waste-to-foundation? [{:keys [m n from to]}]
  true
  )

(defn from-tableau-to-foundation? [{:keys [m n from to]}]
  true
  )

(defn from-foundation-to-tableau? [{:keys [m n from to]}]
  true
  )

(defn from-tableau-to-tableau? [{:keys [m n from to]}]
  true
  )

(defn comply-with-policies? [{:keys [m n from to] :as all}]
  (cond
    (and (contains? #{:waste} from) (contains? tableau-face-up-piles to)) (from-waste-to-tableau? all)
    (and (contains? #{:waste} from) (contains? foundation-piles to)) (from-waste-to-foundation? all)
    (and (contains? tableau-face-up-piles from) (contains? foundation-piles to)) (from-tableau-to-foundation? all)
    (and (contains? foundation-piles from) (contains? tableau-face-up-piles to)) (from-foundation-to-tableau? all)
    (and (contains? tableau-face-up-piles from) (contains? tableau-face-up-piles to)) (from-tableau-to-tableau? all)
    :else false
  ))

(def can-move? 
  "input: {:m game :n num-cards :from from-key :to to-key}"
  (every-pred from-has-at-least-n-element?
              comply-with-policies?
              can-stack-two-piles?))

