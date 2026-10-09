# Compiler Sillage avec Android Studio (Windows)

Pas à pas pour une première ouverture. Le projet utilise le greffon Android pour Gradle 9.2 et Gradle 9.4 : il faut une version **récente** d'Android Studio.

## 1. Installer Android Studio

1. Télécharger la dernière version stable sur https://developer.android.com/studio et l'installer (options par défaut).
2. Au premier lancement, choisir l'installation « Standard ». L'assistant télécharge le SDK Android et un émulateur.
3. Si une ancienne version est déjà installée, la désinstaller avant ou la mettre à jour : une version de plus d'un an refusera le projet.

## 2. Préparer le dépôt

Dans un terminal (Git Bash ou PowerShell), à la racine de votre clone :

```bash
git config core.longpaths true
git pull origin main
git submodule update --init --recursive
```

La première ligne évite les erreurs de chemins trop longs sous Windows (le sous-module `media3` est profond). La troisième récupère `media3` : sans elle, rien ne compile.

Créer ensuite à la racine un fichier `package.properties` contenant une seule ligne :

```
releaseType=SelfBuilt
```

Ce fichier est ignoré par git. Sans lui, la synchronisation Gradle échoue dès le départ.

## 3. Installer les composants du SDK

Menu **Tools > SDK Manager** (ou l'icône de cube avec flèche).

1. Onglet **SDK Platforms** : cocher **Android API 37** (le projet compile avec `compileSdk = 37`).
2. Onglet **SDK Tools**, cocher « Show Package Details » en bas à droite, puis :
   - **Android SDK Build-Tools** : la dernière version ;
   - **NDK (Side by side)** : la dernière version (le module `hificore` contient du C++) ;
   - **CMake** : la version **3.22.1** ;
   - **Android Auto Desktop Head Unit Emulator** : pour tester Android Auto sur le PC (étape 7).
3. Appliquer, accepter les licences, attendre la fin des téléchargements.

## 4. Ouvrir le projet

1. **File > Open**, aller dans le dossier racine du dépôt et sélectionner le **fichier** `settings.gradle.kts`, puis OK. Si une question s'affiche, répondre **Open as Project**.
2. Répondre **Trust Project** à la question de confiance.
3. Android Studio lance la synchronisation Gradle (barre de progression en bas). La première fois, elle télécharge Gradle et toutes les dépendances : compter plusieurs minutes.
4. Vérifier le JDK : **File > Settings > Build, Execution, Deployment > Build Tools > Gradle**, champ **Gradle JDK** = le JDK fourni avec Android Studio (« jbr-21 » ou plus récent). Le projet exige Java 21.

Si la synchronisation échoue, lire la première erreur dans l'onglet **Build** (en bas) :

- « SDK location not found » : rouvrir le projet, Android Studio crée `local.properties` tout seul ; sinon le créer avec `sdk.dir=C\:\\Users\\VOUS\\AppData\\Local\\Android\\Sdk`.
- « package.properties (Le fichier spécifié est introuvable) » : étape 2.
- Erreur sur `media3` introuvable : sous-module non initialisé, étape 2.
- « NDK not configured » ou « CMake 3.22.1 not found » : étape 3.

## 5. Compiler

1. Menu **Build > Select Build Variant** : pour le module `app`, choisir **debug**.
2. Menu **Build > Make Project** (Ctrl+F9).
3. Le fichier produit se trouve dans `app/build/outputs/apk/debug/`.

La version debug porte l'identifiant `io.github.franksauret.sillage.debug` : elle s'installe à côté d'une éventuelle version publiée sans l'écraser.

## 6. Lancer sur le téléphone

1. Sur le téléphone : **Paramètres > À propos du téléphone**, toucher 7 fois **Numéro de build** pour activer les options pour les développeurs.
2. **Paramètres > Système > Options pour les développeurs** : activer **Débogage USB**.
3. Brancher le téléphone en USB, accepter la demande d'autorisation qui s'affiche dessus.
4. Dans Android Studio, choisir le téléphone dans la liste des appareils (barre du haut), puis **Run** (triangle vert, Maj+F10).

Sans téléphone, **Tools > Device Manager** permet de créer un émulateur (prendre une image système API 35 ou plus récente).

## 7. Tester Android Auto sur le PC

1. Sur le téléphone, ouvrir les réglages d'Android Auto, toucher 10 fois la ligne **Version** pour activer le mode développeur.
2. Menu à trois points > **Paramètres pour les développeurs** : activer **Sources inconnues** (sinon une application non installée depuis le Play Store n'apparaît pas).
3. Même menu > **Démarrer le serveur de l'unité principale**.
4. Sur le PC, dans un terminal :

```bash
adb forward tcp:5277 tcp:5277
"%LOCALAPPDATA%\Android\Sdk\extras\google\auto\desktop-head-unit.exe"
```

(La deuxième ligne vaut pour l'invite de commandes `cmd` ; dans PowerShell : `& "$env:LOCALAPPDATA\Android\Sdk\extras\google\auto\desktop-head-unit.exe"`.)

Une fenêtre simule l'écran de la voiture ; Sillage apparaît dans la liste des applications multimédia.

## 8. Ce qu'il faut me renvoyer en cas d'échec

Copier le texte de la **première** erreur de l'onglet **Build** (pas la dernière) et le coller dans le fil de discussion du projet.
