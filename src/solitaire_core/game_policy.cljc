(ns solitaire-core.game-policy
  )

(defn won? [game]
  (-> game
      :foundation
      flatten
      count
      (= 52)
      ))

(defn- from-has-at-least-n-element? [{:keys [m n from]}]
  (>= (count (get-in m from)) n))

(defn- from-valid-piles? [{:keys [from]}]
  (or
    (= [:waste] from)))

(defn can-move-last? [& {:keys [m n from to]}]
  "(m n [from-path] [to-path])"
  ((every-pred from-has-at-least-n-element?) {:m m :n n :from from :to to}) )
