(defproject solitaire-core "0.1.29"
  :description "Logic for the classic solitaire game"
  :url "https://github.com/mingqianye/solitaire-core"
  :license {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}
  :deploy-repositories [["releases"  {:sign-releases false :url "https://clojars.org/repo"}]
                        ["snapshots" {:sign-releases false :url "https://clojars.org/repo"}]]
  :dependencies [[org.clojure/clojure "1.8.0"]])
