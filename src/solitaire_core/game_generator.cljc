(ns solitaire-core.game-generator
  (:require [solitaire-core.deck-generator :as deck-generator]
  ))

(defn new-game []
  (let [cards (deck-generator/random-deck)]
    {
     :stock (subvec cards 0 24)
     :waste []
     :foundation [[] [] [] []]
     :tableau [
               {:face-down []
                :face-up   (subvec cards 24 25)}
               {:face-down (subvec cards 25 26)
                :face-up   (subvec cards 26 27)}
               {:face-down (subvec cards 27 29)
                :face-up   (subvec cards 29 30)}
               {:face-down (subvec cards 30 33)
                :face-up   (subvec cards 33 34)}
               {:face-down (subvec cards 34 38) 
                :face-up   (subvec cards 38 39)}
               {:face-down (subvec cards 39 44)
                :face-up   (subvec cards 44 45)}
               {:face-down (subvec cards 45 51)
                :face-up   (subvec cards 51 52)}
               ]
     }
    )
  )
