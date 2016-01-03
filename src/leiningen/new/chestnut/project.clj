(defproject {{full-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :source-paths ["src/clj" "src/cljs" "env/dev/clj"{{{project-source-paths}}}]

  :test-paths [{{{clj-test-src-path}}}]

  :clean-targets ^{:protect false} [:target-path :compile-path "resources/public/js"]

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.189" :scope "provided"]
                 [ring "1.4.0"]
                 [ring/ring-defaults "0.1.5"]
                 [slester/ring-browser-caching "0.1.1"]
                 [bk/ring-gzip "0.1.1"]
                 [compojure "1.4.0"]
                 [enlive "1.1.6"]
                 [org.omcljs/om "1.0.0-alpha28"]
                 [environ "1.0.1"]{{{project-clj-deps}}}]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-environ "1.0.1"]{{{project-plugins}}}]

  :min-lein-version "2.5.3"

  :uberjar-name "{{{name}}}.jar"

  :main {{project-ns}}.server

  :repl-options {:init-ns {{project-ns}}.server}

  :cljsbuild {:builds {:app {:source-paths ["src/cljs" "env/dev/cljs"{{{cljx-cljsbuild-spath}}}]
                             :compiler {:output-to     "resources/public/js/app.js"
                                        :output-dir    "resources/public/js/out"
                                        :source-map    "resources/public/js/out.js.map"
                                        :preamble      ["react/react.min.js"]
                                        :optimizations :none
                                        :pretty-print  true}}}}

  :figwheel {:http-server-root "public"
             :css-dirs ["resources/public/css"]
             :ring-handler {{project-ns}}.server/http-handler}{{#less?}}

  :env {:is-dev true
        :browser-caching {"text/javascript" 0
                          "text/html" 0}}

  :less {:source-paths ["src/less"]
         :target-path "resources/public/css"}{{/less?}}{{#sass?}}

  :sassc [{:src "src/scss/style.scss"
           :output-to "resources/public/css/style.css"}]

  :auto {"sassc"  {:file-pattern  #"\.(scss)$"}}{{/sass?}}

  :profiles {:dev {:dependencies [[figwheel "0.5.0-2"]
                                  [figwheel-sidecar "0.5.0-2"]
                                  [com.cemerick/piggieback "0.2.1"]
                                  [org.clojure/tools.nrepl "0.2.12"]{{{project-dev-deps}}}]

                   :plugins [[lein-figwheel "0.5.0-2"]{{{project-dev-plugins}}}]}

             :uberjar {:source-paths ^:replace ["src/clj" "env/prod/clj"]
                       :hooks [{{{project-uberjar-hooks}}}]
                       :env {:production true
                             :browser-caching {"text/javascript" 604800
                                               "text/html" 0}}
                       :omit-source true
                       :aot :all
                       :cljsbuild {:builds {:app
                                            {:source-paths ^:replace ["src/cljs" "env/prod/cljs"]
                                             :compiler
                                             {:optimizations :advanced
                                              :pretty-print false}}}}}})
