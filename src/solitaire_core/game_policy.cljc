(ns solitaire-core.game-policy
  (:require [clojure.set :refer [union]]))

(def foundation-piles
  #{:foundation-1 :foundation-2 :foundation-3 :foundation-4})

(def tableau-face-up-piles
  #{:tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
    :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up})

; if there are zero cards in non-tableau piles, then we won the game
(defn won? [game]
  (let [non-tableau (juxt :stock :waste :tableau-1-face-up :tableau-2-face-up :tableau-3-face-up
          :tableau-4-face-up :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up
          :tableau-1-face-down :tableau-2-face-down :tableau-3-face-down :tableau-4-face-down
          :tableau-5-face-down :tableau-6-face-down :tableau-7-face-down)]
    (every? empty? (non-tableau game))))

(defn from-has-at-least-i+1-elements? [{:keys [m i from]}]
  (>= (count (get m from)) (inc i)))

(defn can-stack-in-foundation? [{:keys [top-card bottom-card]}]
  (and 
    (= (:suit top-card) (:suit bottom-card))
    (= (+ 1 (:rank bottom-card)) (:rank top-card))))

(defn can-stack-in-tableau? [{:keys [top-card bottom-card]}]
  (let [stack-rule {:spade   #{:diamond :heart}
                    :club    #{:diamond :heart}
                    :diamond #{:spade :club}
                    :heart   #{:spade :club}   }
        possible-top-card-suit ((:suit bottom-card) stack-rule)]
  (and
    (= (:rank top-card) (dec (:rank bottom-card)))
    (contains? possible-top-card-suit (:suit top-card)))))

(defn can-stack-piles-in-tableau? [{:keys [top-pile bottom-pile]}]
  (if (empty? bottom-pile)
    (= 13 (first top-pile))
    (can-stack-in-tableau? {:top-card (first top-pile) 
                            :bottom-card (last bottom-pile)})))

(defn valid-single-to-tableau? [{:keys [m i from to]}]
  (let [source-pile   (get m from)
        tableau-pile (get m to)
        i=0?         #(= i 0)
        can-stack-on-tableau-pile? #(can-stack-in-tableau? {:top-card (last source-pile)
                                                            :bottom-card (last tableau-pile)})]
    (and (i=0?) (can-stack-on-tableau-pile?))))


(defn valid-to-foundation? [{:keys [m i from to]}]
  (let [source-pile (get m from)
        foundation-pile (get m to)]
    (or 
      (and (empty? foundation-pile) (= 1 (-> source-pile (last) :rank)))
      (can-stack-in-foundation? {:top-card (last source-pile) :bottom-card (last foundation-pile)})
      )))

(defn valid-to-tableau? [{:keys [m i from to]}]
  (let [from-pile (get m from)
        moving-pile (take-last (- (count from-pile) i) from-pile)
        to-tableau (get m to)]
    (if (empty? to-tableau)
      (= 13 (:rank (first moving-pile)))
      (can-stack-piles-in-tableau? {:top-pile moving-pile :bottom-pile to-tableau}))))

(defn comply-with-policies? [{:keys [m i from to] :as all}]
  (let [from-waste?      (contains? #{:waste} from)
        from-tableau?    (contains? tableau-face-up-piles from)
        from-foundation? (contains? foundation-piles from)
        to-tableau?      (contains? tableau-face-up-piles to)
        to-foundation?   (contains? foundation-piles to)]
  (cond
    (and from-waste? to-tableau?)      (valid-to-tableau? all)
    (and from-foundation? to-tableau?) (valid-to-tableau? all)
    (and from-waste? to-foundation?)   (valid-to-foundation? all)
    (and from-tableau? to-foundation?) (valid-to-foundation? all)
    (and from-tableau? to-tableau?)    (valid-to-tableau? all)
    :else false)))


(defn cards-in-desc-order? [{:keys [cards]}]
  (let [ranks (map :rank cards)
        descending-seq (reverse (range (last ranks) (inc (first ranks)))) ]

    (and 
      (= ranks descending-seq)
      (= (count ranks) (count descending-seq)))))


(defn can-be-selected? [{:keys [m i from]}]
  (let [from-waste?      (contains? #{:waste} from)
        from-tableau?    (contains? tableau-face-up-piles from)
        from-foundation? (contains? foundation-piles from)
        all-cards        (from m)
        is-last-card?    (= i (- (count all-cards) 1))
        last-k-cards     (fn [] (take-last (- (count all-cards) i) all-cards))]
    (cond
      from-waste? is-last-card?
      from-foundation? is-last-card?
      from-tableau? (cards-in-desc-order? {:cards (last-k-cards)})
      :else false)))

(def can-move? 
  "input: {:m game :i index-in-pile :from from-key :to to-key}"
  (every-pred from-has-at-least-i+1-elements?
              comply-with-policies?))

  
