# Yoga App !

## Description

Ce projet est une application Spring Boot qui permet de gérer le back end d'un site du yoga. Le site permet aux clients potentiels de s'inscrire à des sessions de yoga.

Ce projet à pour objectif de tester l'applcation.
Suivez les étapes ci-dessous pour réaliser tester le projet.

## Prérequis

- java 11
- maeven

## Installations

Clonez le repository :

> git clone https://github.com/Manuel-Fami/yoga-app.git

- Installer les dépendances :
  > mvn install

## Lancement des tests unitaires

Pour exécuter les tests unitaires, utiliser la commande suivante :

> mvn test

Cela lancera tous les tests dans le projet et affichera les résultats dans la console.

## Générer des rapports de couverture avec Jacoco

Pour générer des rapports de couverture de code :

- Exécutez les tests et génèrez le rapport de couverture avec la commande suivante :
  > mvn clean test

Une fois le processus terminé, le rapport de couverture ser disponible dans le répertoir suivant : target/site/jacoco

- Ouvrez le fichier _index.html_ dans un navigateur pour visualiser le rapport.
