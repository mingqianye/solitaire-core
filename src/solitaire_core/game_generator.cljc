(ns solitaire-core.game_generator
  (:require [solitaire-core.deck_generator :as deck_generator]
  ))

(defn new-game []
  (let [cards (deck_generator/random_deck)]
    {
     :stock (subvec cards 0 24)
     :foundation [[] [] [] []]
     :tableau [
               (subvec cards 24 25)
               (subvec cards 25 27)
               (subvec cards 27 30)
               (subvec cards 30 34)
               (subvec cards 34 39)
               (subvec cards 39 45)
               (subvec cards 45 52) ]

     }
    )
  )
