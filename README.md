# Description de l'application C4U
Application Android pour les personnes myopes

## Videos de captures hebdomadaires
*   [Videos de captures](https://drive.google.com/drive/folders/1s5DEG9MhZ2Z_znibDzHeM2-z-NPD4Blb?fbclid=IwAR3f6nvS9treE7b9epCUmL6xHOs4VVB50fBAiVJ9CiGaq48Vg1Qf144qKYY)

## Cahier de charges
1.  Contexte et définition du problème
>   Avec l'age la vision humaine se dégrade, note application vient en aide aux personnes myopes, 
>   elle leur facilite les différentes taches quotidiennes.
2.  Objectif du projet
>   Mettre en oeuvre nos aquics dans le développement mobile android et ses différentes fonctionnalités
>   natives
3.  Description fonctionnelle des besoins

##  Fonctionnalités
*   Basiques
    *   Texte en audio.
    *   Audio en action.
    *   Caméra.
        *   Image en texte 
            *   Pièces de monnaie image en texte (devise, valeur, occurrence, total).
            *   Image contenant du texte en texte.
    *   Géolocalisation.
    *   Gestures.
    *   Capteurs de mouvements.

*   Supplémentaires
    *   Aide par des volantaires (SOS).
        *   Enregistrement.
    *   Reconnaissance faciale.

## Roles
*   TAMRY Hamza
    1.  Vérification des capteurs et choix des types de capteurs.
    2.  Création d'un service de gestion des capteurs et implémentation des capteurs suivants :
        *   Capteur d'accélération
        *   Capteur d'orientation
        *   Capteur de lumière
    3.  Détection de mouvement d'agitation et des positions (portrait, landscape, vertical).
    4.  Liaison des Déclenchement des actions (OCR, MoneyDetection, GPS) après la détection des positions et de mouvement d'agitation. 
    5.  Résolution de superposition des activités après détection de differentes positions et de mouvement.
    6.  Résolution de déclenchement répété des actions par introduction d'un seuil de variation.

*   SABOUR Zakaria
    1.   Gestures.
    2.   Texte en audio.
*   TAZI Rida
    1.   Caméra - Image en texte - Pièces de monnaie image en texte (devise, valeur, occurrence, total).
    2.   Audio en action.
*   TAMTAOUI Abdelwadoud
    1.   Caméra - Image en texte - Image contenant du texte en texte.
    2.   Texte en audio.

