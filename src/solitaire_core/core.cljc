(ns solitaire-core.core
  (:require [solitaire-core.deck_generator :as deck_generator])
  )

(defn foo
  "I don't do a whole lot."
  []
  (println (deck_generator/new_deck)))

(defn bar
  "I don't do a whole lot."
  [x]
  (str x "kkkklllHello, World!"))
