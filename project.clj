(defproject browser "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [congomongo "2.2.1"]
                 [camel-snake-kebab "0.4.2"]
                 [environ "1.2.0"]
                 [clj-http "3.12.0"]
                 [dk.ative/docjure "1.14.0"]
                 [cheshire "5.10.0"]
                 [org.clojure/core.async "1.3.610"]
                 [clojurewerkz/elastisch "3.0.1"]
                 ;; for reveal
                 [nrepl,"0.8.3"]
                 [clj-kondo,"2020.04.05"]]
  ;;:plugins [[cider/cider-nrepl,"0.25.6"]]
  :profiles {:reveal {:dependencies [[vlaaad/reveal "1.2.186"]]
                      :repl-options {:nrepl-middleware [vlaaad.reveal.nrepl/middleware]}}}
  :repl-options {:init-ns browser.core})
                 ;;:nrepl-middleware [["cider.nrepl/cider-middleware"]]})
