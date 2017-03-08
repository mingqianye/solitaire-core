(ns solitaire-core.game-policy
  (:require [clojure.set :refer [union]]))

(def foundation-piles
  #{:foundation-1 :foundation-2 :foundation-3 :foundation-4})

(def tableau-face-up-piles
  #{:tableau-1-face-up :tableau-2-face-up :tableau-3-face-up :tableau-4-face-up
    :tableau-5-face-up :tableau-6-face-up :tableau-7-face-up})

(defn won? [game]
  (-> game
    (juxt :foundation-1 :foundation-2 :foundation-3 :foundation-4)
    flatten
    count
    (= 52)))

(defn is-stable? [game]
  (let [pile-empty?     (fn [pile-name] (empty? (pile-name game)))
        pile-not-empty? (complement pile-empty?)]
    (and
      (or (pile-not-empty? :tableau-1-face-up) (pile-empty? :tableau-1-face-down))
      (or (pile-not-empty? :tableau-2-face-up) (pile-empty? :tableau-2-face-down))
      (or (pile-not-empty? :tableau-3-face-up) (pile-empty? :tableau-3-face-down))
      (or (pile-not-empty? :tableau-4-face-up) (pile-empty? :tableau-4-face-down))
      (or (pile-not-empty? :tableau-5-face-up) (pile-empty? :tableau-5-face-down))
      (or (pile-not-empty? :tableau-6-face-up) (pile-empty? :tableau-6-face-down))
      (or (pile-not-empty? :tableau-7-face-up) (pile-empty? :tableau-7-face-down)))))

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
        foundation-pile (get m to)
        i=0?       #(= i 0)
        empty-foundation? #(empty? foundation-pile)
        can-stack-on-foundation-pile? #(can-stack-in-foundation? {:top-card (last source-pile)
                                                                  :bottom-card (last foundation-pile)})]
    (and (i=0?) 
         (or 
           (empty-foundation?) 
           (can-stack-on-foundation-pile?)))))

(defn valid-from-tableau-to-tableau? [{:keys [m i from to]}]
  (let [from-tableau (get m from)
        moving-pile (take-last (- (count from-tableau) i) from-tableau)
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
    (and from-waste? to-tableau?)      (valid-single-to-tableau? all)
    (and from-foundation? to-tableau?) (valid-single-to-tableau? all)
    (and from-waste? to-foundation?)   (valid-to-foundation? all)
    (and from-tableau? to-foundation?) (valid-to-foundation? all)
    (and from-tableau? to-tableau?)    (valid-from-tableau-to-tableau? all)
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

  
