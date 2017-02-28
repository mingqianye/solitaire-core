(ns solitaire-core.game-generator
  (:require [solitaire-core.deck-generator :as deck-generator]
  ))


(defn new-game []
  (let [cards (deck-generator/random-deck)]
    {
     :stock (subvec cards 0 24)
     :waste []
     :foundation-1 []
     :foundation-2 []
     :foundation-3 []
     :foundation-4 []
     :tableau-1-face-down []
     :tableau-1-face-up   (subvec cards 24 25)
     :tableau-2-face-down (subvec cards 25 26) 
     :tableau-2-face-up   (subvec cards 26 27)
     :tableau-3-face-down (subvec cards 27 29) 
     :tableau-3-face-up   (subvec cards 29 30)
     :tableau-4-face-down (subvec cards 30 33) 
     :tableau-4-face-up   (subvec cards 33 34)
     :tableau-5-face-down (subvec cards 34 38) 
     :tableau-5-face-up   (subvec cards 38 39)
     :tableau-6-face-down (subvec cards 39 44) 
     :tableau-6-face-up   (subvec cards 44 45)
     :tableau-7-face-down (subvec cards 45 51) 
     :tableau-7-face-up   (subvec cards 51 52)
     }
    )
  )
