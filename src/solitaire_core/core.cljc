(ns solitaire-core.core
  (:require [solitaire-core.deck-generator :as deck-generator])
  )

(defn foo
  "I don't do a whole lot."
  []
  (println (deck-generator/new-deck)))

(defn bar
  "I don't do a whole lot."
  [x]
  (str x "kkkklllHello, World!"))
