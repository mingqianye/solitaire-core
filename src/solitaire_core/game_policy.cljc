(ns solitaire-core.game-policy
  )

(defn won? [game]
  (-> game
      :foundation
      flatten
      count
      (= 52)
      ))
