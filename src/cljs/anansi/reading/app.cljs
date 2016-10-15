(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.components :as c]
            [clojure.walk :refer [keywordize-keys]]
            [cognitect.transit :as transit]
            [datascript.core :as d]
            [reagent-material-ui.core :refer [List ListItem]] ))
(enable-console-print!)

(def fixture
  "{\"date\":\"2016-10-13T21:21:50Z\",\"user\":\"mathpunk\",\"posts\":[{\"href\":\"https:\\/\\/twitter.com\\/vruba\\/status\\/786673570558640128?s=09\",\"description\":\"Charlie Loyd on Twitter: &quot;Nice chewy paper on the Kamilaroi and Euahlayi emus here: https:\\/\\/t.co\\/rAQJWtqMvZ https:\\/\\/t.co\\/jOvZZ1K8kg&quot;\",\"extended\":\"\",\"meta\":\"7b54d7460d80bc8f12edc8a82f52f2db\",\"hash\":\"6b848b226485d1d3a659ba455a640484\",\"time\":\"2016-10-13T21:21:50Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/mediapathic\\/status\\/786677473798918144?s=09\",\"description\":\"GSV Steen on Twitter: &quot;I need to think about the implications of this https:\\/\\/t.co\\/ES1x8Tn3eq&quot;\",\"extended\":\"\",\"meta\":\"69c216697fad6a9fa630228039d32aef\",\"hash\":\"0bf8f60e410bb3881bb6f57be40c82a1\",\"time\":\"2016-10-13T21:21:50Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/tressiemcphd\\/status\\/786665306580221952?s=09\",\"description\":\"Tressie Mc on Twitter: \\\"https:\\/\\/t.co\\/mxYE99yhNA The Queer Ontology of Digital Method. queer theory complicating quant\\/qual divide #digitalsociology\\\"\",\"extended\":\"\",\"meta\":\"8268386196a9b0069eb6a44cac077857\",\"hash\":\"0b54260099eaf8a3d9589137049e403c\",\"time\":\"2016-10-13T20:56:57Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"queer data theory\"},{\"href\":\"https:\\/\\/twitter.com\\/claudiakincaid\\/status\\/786668929942953984?s=09\",\"description\":\"Karen Gregory on Twitter: &quot;Yes! Read @benjaminhaber https:\\/\\/t.co\\/BoTgxLFD7r&quot;\",\"extended\":\"\",\"meta\":\"9fbfb1332fc8e6472723f30b6d94b8bb\",\"hash\":\"93a104213e366f5dcf03a73a955612a1\",\"time\":\"2016-10-13T20:56:56Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/drdonjohanson\\/status\\/786392248535490560?s=09\",\"description\":\"Dr. Donald Johanson on Twitter: &quot;Scientists discover hundreds of footprints left at the dawn of modern humanity https:\\/\\/t.co\\/eT5XdlURVR&quot;\",\"extended\":\"\",\"meta\":\"5f5de613fc822da406637da8f27acbc7\",\"hash\":\"db70d519fd9538bc3982e31e9edd320d\",\"time\":\"2016-10-13T20:55:21Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/Chris_arnade\\/status\\/786669741544996866?s=09\",\"description\":\"Chris Arnade on Twitter: \\\"Why are there so few books from working class women?By @TaraClancyNYC (who wrote one.) https:\\/\\/t.co\\/y9a1ZX271T https:\\/\\/t.co\\/FqNJGHwYL8\\\"\",\"extended\":\"\",\"meta\":\"927814dd24effb2fc921390455912752\",\"hash\":\"d27809596aa177b77518703da07bc6fa\",\"time\":\"2016-10-13T20:55:20Z\",\"shared\":\"yes\",\"toread\":\"no\",\"tags\":\"books women\"},{\"href\":\"https:\\/\\/twitter.com\\/jessysaurusrex\\/status\\/786664716487843842?s=09\",\"description\":\"Jessy Irwin \\u2728 on Twitter: &quot;How a Facial Recognition Mismatch Can Ruin Your Life https:\\/\\/t.co\\/ZyJDbS93QS&quot;\",\"extended\":\"\",\"meta\":\"534d1b63a5d0d36807408365f047e846\",\"hash\":\"5486102ce773204499387886889f3e50\",\"time\":\"2016-10-13T20:55:20Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/Interdome\\/status\\/786660620980461568?s=09\",\"description\":\"Adam Rothstein on Twitter: &quot;a group that seeks out and amplifies the growing radical voices of the next generation....&quot;\",\"extended\":\"\",\"meta\":\"c57a597105749f28ca84084d19c88d52\",\"hash\":\"55c05bd2d1661de72437757b31e85173\",\"time\":\"2016-10-13T20:14:26Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/mgiraldo\\/status\\/786647506172125186?s=09\",\"description\":\"mgiraldo on Twitter: &quot;awesome cartographic \\u201cvisual language of scale\\u201d by @sensescape @patriciogv https:\\/\\/t.co\\/OAWAuTVnwf&#10;(neat animation of coastlines) https:\\/\\/t.co\\/RfLNKOi1aN&quot;\",\"extended\":\"\",\"meta\":\"45d40a39e452269698d192cf894d4fdb\",\"hash\":\"e22018d8186a6664b08137a830514883\",\"time\":\"2016-10-13T20:12:41Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/runemadsen\\/status\\/786657312136134656?s=09\",\"description\":\"Rune Madsen on Twitter: \\\"I highly recommend @rushkoff's Team Human podcast episode on media literacy. https:\\/\\/t.co\\/SuleTI9a90\\\"\",\"extended\":\"\",\"meta\":\"b37328ccd36257cf40593b395c1f268e\",\"hash\":\"5ebca3cce3106def0f1a6c5717d473c6\",\"time\":\"2016-10-13T20:02:38Z\",\"shared\":\"yes\",\"toread\":\"no\",\"tags\":\"listen\"},{\"href\":\"https:\\/\\/twitter.com\\/migrationcities\\/status\\/786653293359529985\",\"description\":\"migrationcities on Twitter: \\\"Questioning how #refugee camps have become a locus through which to examine how human rights intersect with&complicate the making of cities. https:\\/\\/t.co\\/FIl8I2LkEi\\\"\",\"extended\":\"\",\"meta\":\"3498ba94b6be1d9b449a0a2cb3bc5f99\",\"hash\":\"0d5e143e491e8bb2a66f977e8df9d42c\",\"time\":\"2016-10-13T19:46:52Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"http:\\/\\/www.newyorker.com\\/culture\\/cultural-comment\\/the-lives-of-poor-white-people\",\"description\":\"The Lives of Poor White People - The New Yorker\",\"extended\":\"\",\"meta\":\"11cba25fb63cbfce1f1c323c176f9b8e\",\"hash\":\"663d229ee00491ac5bb1d66b4aaaaa3f\",\"time\":\"2016-10-13T19:42:33Z\",\"shared\":\"yes\",\"toread\":\"no\",\"tags\":\"anomie books\"},{\"href\":\"https:\\/\\/www.theguardian.com\\/media\\/2016\\/oct\\/13\\/liberal-media-bias-working-class-americans\",\"description\":\"Dangerous idiots: how the liberal media elite failed working-class Americans | Media | The Guardian\",\"extended\":\"Hillbilly Elegy\",\"meta\":\"c82fb9d803e3a025f4bf182ab4669ba6\",\"hash\":\"99b67847fb826b08f4d9fe069db97966\",\"time\":\"2016-10-13T19:42:23Z\",\"shared\":\"yes\",\"toread\":\"no\",\"tags\":\"anomie whiteness class\"},{\"href\":\"https:\\/\\/twitter.com\\/KathViner\\/status\\/786632109146279936\",\"description\":\"Katharine Viner on Twitter: \\\"Dangerous idiots: how the liberal media elite failed working-class Americans. Fasciating by @sarah_smarsh https:\\/\\/t.co\\/LEPba3MAjr\\\"\",\"extended\":\"\",\"meta\":\"991ec9109340912a977cd4d708af4ea0\",\"hash\":\"9b83ecec45ca7c54a26c1c8f4be63846\",\"time\":\"2016-10-13T18:41:52Z\",\"shared\":\"yes\",\"toread\":\"yes\",\"tags\":\"\"},{\"href\":\"https:\\/\\/twitter.com\\/Wolven\\/status\\/786624803214098432\",\"description\":\"Damien on Twitter: \\\"Explaining intersectionality to my students then reading them Camus' \\\"Myth of Sisyphus\\\" & making them sit w\\/ choice & radical responsibility\\\"\",\"extended\":\"\",\"meta\":\"bb988c199095e5876bb451f2cc45845f\",\"hash\":\"6e4b5b77d832ef929637940a3a1dc0af\",\"time\":\"2016-10-13T18:37:11Z\",\"shared\":\"yes\",\"toread\":\"no\",\"tags\":\"thread ethics curriculum readings books\"}]}\t\n")

(defn recent-pins []
  (let [r (transit/reader :json)]
    (keywordize-keys (transit/read r fixture))))

(def data
  { :date (:date recent-pins)
    :user (:user recent-pins)
    :pins (:posts recent-pins)
  })

(def schema
  ;; { :entity/attribute {:db/attribute :db.attribute/value} ... }
    {:pin/user {:db/valueType :db.type/ref}
     :pin/href {:db/cardinatlity :db.cardinality/one}
     :pin/tags {:db/cardinality :db.cardinality/many}
    })

(defonce conn (d/create-conn schema))

(defonce state
  (let [session {:unread (count (:pins data))}]
        (atom (merge data session))))

;; Views
;; =====
(defn welcome-pane []
  [:div#welcome [:h2 "Good morning--"]])

(defn summary-pane []
    [:div#summary
      [:div#today "today is 2016-10-13"]
      [:div#source
        (str "viewing " (:user @state) "'s pins, retrieved " (:date @state))]
      [:div#progress (str (count (:pins @state)) " pins to review")]
    ])

(defn review-pane []
  [:div#pins {:style {:margin-top 20}}
    (map c/card (:pins @state))
  ])

(defn app [db]
  [:div#app
    [welcome-pane]
    [summary-pane]
    [review-pane]
  ])

(defn render
  ([] (render @conn))
  ([db] (reagent/render-component [app db]
                            (.getElementById js/document "container"))))
(d/listen! conn :render
  (fn [tx-report] (render (:db-after tx-report))))
