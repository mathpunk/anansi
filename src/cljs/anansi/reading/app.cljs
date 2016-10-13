(ns anansi.reading.app
  (:require [reagent.core :as reagent :refer [atom]]
            [anansi.reading.db :as db]))

(defn some-component []
  [:div
   [:h3 "Posts from " (:date db/data)]
   [:p.someclass
    "I represent " [:strong (db/view)]
    [:span {:style {:color "green"}} " elements"]]])

(defn calling-component []
  [:div "Good morning--"
   [some-component]])

(defn init []
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
