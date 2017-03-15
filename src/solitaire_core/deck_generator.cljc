(ns solitaire-core.deck-generator)

(defn new-deck []
    (for [suit [:spade :heart :diamond :club]
          rank [1 2 3 4 5 6 7 8 9 10 11 12 13]]
      {:suit suit :rank rank} ))

(defn random-deck []
  (shuffle (new-deck)))
