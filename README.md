# Application ChâTop (OCR Projet 3)

## Introduction
Après avoir récupéré le projet sur GitHub, il faudra démarrer 3 éléments pour utiliser l'application:
- La base de données
- Le serveur backend (API)
- Le serveur frontend

## Pré-requis
Avant de récupérer le projet, il faudra avoir installé un certain nombre d'éléments au préablable:

### Serveur MySQL
Disponible à cette adresse: 

`https://dev.mysql.com/downloads/installer/`

### Apache Maven
Disponible à cette adresse: 

`https://maven.apache.org/download.cgi`

### Java Development Kit (JDK), comprenant Java Runtime Environment (JRE)
Disponible à cette adresse: 

`https://www.oracle.com/java/technologies/downloads/`

Il est préférable de prendre la dernière version LTS (Long term support)

### Node Package Manager (NPM)
Utiliser la commande `npm install` dans l'invite de commandes (cmd), en mode administrateur si besoin

## Récupérer le projet 
Pour récupérer le projet, il est possible de télécharger le projet sur GitHub en format compressé (.zip), ou de le récupérer en le clonant à l'aide de cette commande (à condition que Git soit installé sur votre poste): 

`git clone https://github.com/Keijur0/Developpez-le-back-end-en-utilisant-Java-et-Spring.git`

Décompresser l'archive (.zip) à l'emplacement souhaité si besoin.

## Mettre en place le serveur MySQL
Lors de l'installation de MySQL Server, prenez soin de créer un compte qui ser autorisé à lire et écrire dans la base de données.

Après avoir effectué l'installation de MySQL Server, il faut créer la variable d'environnement MySQL, qui pointe vers le répertoire `/bin` de MySQL Server.

### Se connecter à la base de données
Une fois effectué, aller dans l'invite de commandes et utilisez la commande suivante:

`mysql -u <nom d'utilisateur> -p`

Utilisez le nom d'utilisateur et le mot de passe que vous avez créé lors de l'installation de MySQL Server.

Vous aurez besoin de ces identifiants lors du lancement de l'API. 

Je nommerai ces éléments `<nom d'utilisateur>` et  `<mot de passe>` dans la commande de lancement.

### Créer la base de données
Pour créer la base de données, utilisez la commande suivante:

`CREATE DATABASE chatop`

C'est le nom de la base de données indiquée dans les paramètres de l'API. Si vous souhaitez l'appeler autrement, il sera nécessaire d'ajuster les propriétés de l'API.

### Création des tables
Tout d'abord, quittez MySQL Server en tappant `exit` pour vous retrouver dans l'invite de commandes.

Tappez ensuite `mysql -u <nom d'utilisateur> -p chatop < <chemin/vers/le/fichier/script.sql>`

Puis tappez le mot de passe associé à ce compte utilisateur.

(Le fichier se trouve ici: `<chemin\où\se\trouve\le\projet>\Developpez-le-back-end-en-utilisant-Java-et-Spring\frontend\ressources\sql\script.sql`)

### Vérification de la base
Pour vérifier si la base a été installée tappez:

`mysql -u <nom d'utilisateur> -p` puis Validez. Ensuite tappez le `<mot de passe>` pour vous connecter au serveur

`SHOW DATABASES;` pour vérifier si `chatop` se trouve parmi les bases existantes

`USE chatop;` pour choisir d'utiliser cette base

`SHOW TABLES;` pour montrer les tables de la base

`SHOW COLUMNS FROM <nom de la table>` pour voir le nom des colonnes de chaque table

## Lancer le projet Java (API / Backend)
Pour lancer le projet Java, il vous faut d'abord créer les variables d'environnement pour Java et Maven, pointant vers leurs dossiers respectifs `/bin`.

Ensuite, vous aurez besoin de générer une clé secrète à partir de ce site: `https://asecuritysite.com/encryption/plain`.

Choisissez une clé de `256-bit` et cliquez sur `Determine`.

La `Hex Key` sera la `<clé secrète>` dans la commande suivante.

Une fois effectué, placez-vous dans le dossier `/backend/api` du projet et tappez la commande:

`mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.username=<nom d'utilisateur> --spring.datasource.password=<mot de passe> --jwt.secret=<clé secrète>"`

## Documentation Swagger de l'API

`http://localhost:9000/swagger-ui.html`

## Lancer le projet Angular (Frontend)
Pour lancer le projet Angular, placez-vous dans le dossier `frontend` du projet et tappez la commande:

`npm run start`

## Utiliser l'application
Pour utiliser l'application, rendez vous à l'url suivante sur votre navigateur web: 

`http://localhost:4200`