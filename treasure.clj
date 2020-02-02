(ns a2.treasure
  (:require [clojure.string :as str]))

(defn input []
   (def inputString (str/trim (slurp "map.txt")))
   (println "This is my challenge: \n" )
   (println inputString)
   (println "")
   )
(input)

(def inputVector (str/split inputString #"\n"))

(def newVector [])
(defn createVector
  [treasureVector]
  (def i (atom 0))
  (while ( < @i (count inputVector) )
    (do
        (def newVector (conj newVector (str/split (str/trim-newline (nth treasureVector @i)) #"")))
        (swap! i inc)))
)
(createVector inputVector)

(def returnValue 0)
(def treasureMap newVector)

(defn findTreasure
  [i j]
    (if (= returnValue 0)
        (if (= (nth (nth treasureMap i) j) "@")
          (do
            (def returnValue 1)
          )
          (do
            (if (= (nth (nth treasureMap i )j) "-")
             (do
               (def called 0)
               (def treasureMap (assoc treasureMap i
                 (assoc (nth treasureMap i) j "+")))

               (if (and (> i 0) (not= "+" (nth (nth treasureMap (- i 1)) j) ))
                (do (if (not= (nth (nth treasureMap (- i 1) )j) "#")
                (do (def called 1)
                (findTreasure (- i 1) j))
               ))

                 )
               (if (and (> j 0) (not= "+" (nth (nth treasureMap i) (- j 1)) ))
                (do (if (not= (nth (nth treasureMap i )(- j 1)) "#")
                (do (def called 1)
                (findTreasure i (- j 1)))
               ))
                 )
              (if (and (< i (- (count treasureMap) 1)) (not= "+" (nth (nth treasureMap (+ i 1)) j) ))
                (do (if (not= (nth (nth treasureMap (+ i 1) )j) "#")
                (do (def called 1)
                (findTreasure (+ i 1) j))
               )
               )        )
               (if (and (< j (- (count (nth treasureMap i)) 1)) (not= "+" (nth (nth treasureMap i) (+ j 1)) ))
                (do (if (not= (nth (nth treasureMap i )(+ j 1)) "#")
                (do (def called 1)
                (findTreasure i (+ j 1)))
               )))
               (if (= called 0)
                     (do (def treasureMap (assoc treasureMap i
                         (assoc (nth treasureMap i) j "!")))
                         (def called 0)
                         )
                     )
                     )
              );end of if "-"

            )))
    (= returnValue 1)
  )


(defn printMap
    [finalMap]
    (def i (atom 0))
    (while ( < @i (count inputVector) )
      (do
        (doall (map #(doall (map print %)) (nth treasureMap @i)))
        (println "")
          (swap! i inc))))


(def counter (count (nth newVector 0)))
(def checker 0)
(defn checkVector
    [checkVec]
    (def i (atom 0))
    (while ( < @i (count inputVector) )
      (do
        (if (not= counter (count (nth checkVec @i)))
          (do ;(println "Sorry, the treasure map is not evenly created.")
          (def checker 1)
          )
        )
          (swap! i inc)))

        (if (= checker 0)
        (do (if (findTreasure 0 0)
              (println "Woo hoo, I found the treasure :-) \n" )
              (println "Uh oh, I could not find the treasure :-( \n" )
              )
              (printMap treasureMap))
              (println "Sorry, the treasure map is not evenly created.")))
  (checkVector newVector)
