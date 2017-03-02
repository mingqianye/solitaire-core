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

(defn can-stack-in-foundation? [{:keys [top-card bottom-card]}]
  (and 
    (= (:suit top-card) (:suit bottom-card))
    (= (+ 1 (:number bottom-card)) (:number top-card))))

(defn can-stack-in-tableau? [{:keys [top-card bottom-card]}]
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
    (can-stack-in-tableau? {:top-card top-card :bottom-card bottom-card})))

(defn valid-from-waste-to-tableau? [{:keys [m n from to]}]
  (let [waste-pile   (get m from)
        tableau-pile (get m to)
        n=1?         #(= n 1)
        can-stack-on-tableau-pile? #(can-stack-in-tableau? {:top-card (last waste-pile)
                                                            :bottom-card (last tableau-pile)})]
    (and (n=1?) (can-stack-on-tableau-pile?))))

(defn valid-from-waste-to-foundation? [{:keys [m n from to]}]
  (let [waste-pile (get m from)
        foundation-pile (get m to)
        n=1?       #(= n 1)
        empty-foundation? #(empty? foundation-pile)
        can-stack-on-foundation-pile? #(can-stack-in-foundation? {:top-card (last waste-pile)
                                                                  :bottom-card (last foundation-pile)})]
    (and (n=1?) 
         (or 
           (empty-foundation?) 
           (can-stack-on-foundation-pile?)))))

(defn valid-from-tableau-to-foundation? [{:keys [m n from to]}]
  true
  )

(defn valid-from-foundation-to-tableau? [{:keys [m n from to]}]
  true
  )

(defn valid-from-tableau-to-tableau? [{:keys [m n from to]}]
  true
  )

(defn comply-with-policies? [{:keys [m n from to] :as all}]
  (let [from-waste?      (contains? #{:waste} from)
        from-tableau?    (contains? tableau-face-up-piles from)
        from-foundation? (contains? foundation-piles from)
        to-tableau?      (contains? tableau-face-up-piles to)
        to-foundation?   (contains? foundation-piles to)]
  (cond
    (and from-waste? to-tableau?)      (valid-from-waste-to-tableau? all)
    (and from-waste? to-foundation?)   (valid-from-waste-to-foundation? all)
    (and from-tableau? to-foundation?) (valid-from-tableau-to-foundation? all)
    (and from-foundation? to-tableau?) (valid-from-foundation-to-tableau? all)
    (and from-tableau? to-tableau?)    (valid-from-tableau-to-tableau? all)
    :else false)))

(def can-move? 
  "input: {:m game :n num-cards :from from-key :to to-key}"
  (every-pred from-has-at-least-n-element?
              comply-with-policies?
              can-stack-two-piles?))

