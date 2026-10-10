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
   - **NDK (Side by side)** : déplier et cocher la version **28.2.13676358** exactement, celle qu'attend Gradle (`media3` et `hificore` contiennent du C++) ;
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
- « NDK not configured » qui persiste après installation : `media3` est une construction incluse qui cherche le SDK de son côté (son propre `local.properties`, sinon la variable `ANDROID_HOME`). Copier `local.properties` de la racine dans `media3/` (fichier ignoré par git), vérifier que `ANDROID_HOME` et `ANDROID_SDK_ROOT` sont absentes ou pointent vers le même SDK, puis fermer et rouvrir Android Studio.
- Erreur sur `media3` introuvable : sous-module non initialisé, étape 2.
- « NDK not configured … Preferred NDK version is 'X' » ou « CMake 3.22.1 not found » : installer exactement la version citée (étape 3), puis **File > Sync Project with Gradle Files**.

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

1. Sur le téléphone, ouvrir les réglages d'Android Auto. Sur Android 10 et plus, Android Auto est intégré au système et n'a pas d'icône : passer par **Paramètres**, puis la loupe de recherche, et taper « Android Auto » (selon la marque : **Appareils connectés > Préférences de connexion > Android Auto**, ou **Appareils connectés > Android Auto** chez Samsung). Si rien n'apparaît, installer « Android Auto » depuis le Play Store.
2. Tout en bas de ces réglages, toucher une dizaine de fois la ligne **Version**, puis accepter l'activation du mode développeur.
3. Menu à trois points en haut à droite > **Paramètres pour les développeurs** : activer **Sources inconnues** (sinon une application non installée depuis le Play Store n'apparaît pas).
4. Même menu à trois points > **Démarrer le serveur de l'unité principale**.
5. Téléphone branché en USB, sur le PC, dans un terminal :

```bash
adb forward tcp:5277 tcp:5277
"%LOCALAPPDATA%\Android\Sdk\extras\google\auto\desktop-head-unit.exe"
```

(La deuxième ligne vaut pour l'invite de commandes `cmd` ; dans PowerShell : `& "$env:LOCALAPPDATA\Android\Sdk\extras\google\auto\desktop-head-unit.exe"`.)

Une fenêtre simule l'écran de la voiture ; Sillage apparaît dans la liste des applications multimédia.

## 8. Ce qu'il faut me renvoyer en cas d'échec

Copier le texte de la **première** erreur de l'onglet **Build** (pas la dernière) et le coller dans le fil de discussion du projet.
