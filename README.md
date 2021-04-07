# C4U
Android app for short sighted people

## Taches
1.  Vérification des mesures des différents capteurs et choix des capteurs finales (capteur Accéléromètre, d'Orientation et de lumière).
2.  Création d'un service pour la gestion des capteurs, et définition des différents mouvements de l'appareil ainsi que l'état d'environement qui vont déclencher par la suite
    différentes actions de l'utilisateur.
3.  Ajout de la fonctionnalité de Géolocalisation.
4.  Caméra - Image en texte - Pièces de monnaie image en texte (devise, valeur, occurrence, total).

## La liste de mes capteurs

Sensor's Type                                                                             | Streaming
----------------------------------------------------------------------------------------- | -----------------------------------------------------
{Sensor name="BMC150 Acceleration Sensor", vendor="Bosch Sensortec", version=1, type=1, maxRange=19.6133, resolution=0.009576807, power=0.13, minDelay=10000}| true
{Sensor name="BMC150 Magnetic Sensor", vendor="Bosch Sensortec", version=1, type=2, maxRange=2000.0, resolution=0.06, power=6.0, minDelay=20000} | true
{Sensor name="BOSCH Uncalibrated Magnetic Sensor", vendor="Bosch Sensortec", version=1, type=14, maxRange=2000.0, resolution=0.06, power=6.0, minDelay=20000} | true
{Sensor name="TAOS TMD3782 Prox Sensor", vendor="AMS , Inc.", version=1, type=8, maxRange=8.0, resolution=8.0, power=0.75, minDelay=0} | false
{Sensor name="TAOS TMD3782 Light Sensor", vendor="AMS , Inc.", version=1, type=5, maxRange=65555.0, resolution=1.0, power=0.75, minDelay=10000} | true
{Sensor name="Screen Orientation Sensor", vendor="Samsung Electronics", version=3, type=65558, maxRange=255.0, resolution=255.0, power=0.13, minDelay=0} | false
{Sensor name="Samsung Geomagnetic Rotation Vector Sensor", vendor="Samsung Electronics.", version=1, type=20, maxRange=1.0, resolution=5.9604645E-8, power=6.13, minDelay=10000} | true
{Sensor name="Orientation Sensor", vendor="Samsung Electronics.", version=1, type=3, maxRange=360.0, resolution=0.00390625, power=6.13, minDelay=10000} | true
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
## Capteurs Choisis
*   Acceleration Sensor
*   Orientation Sensor
*   Light Sensor
