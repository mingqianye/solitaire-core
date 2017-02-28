(ns solitaire-core.deck-generator)

(defn new-deck []
  (flatten
    (for [suit [:spade :heart :diamond :club]]
      (for [number [1 2 3 4 5 6 7 8 9 10 11 12 13]]
        {:suit suit :number number} ))))

(defn random-deck []
  (shuffle (new-deck)))