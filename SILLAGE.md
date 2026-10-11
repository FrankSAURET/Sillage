# Sillage

Lecteur de musique Android libre, hors ligne, dérivé de [Gramophone](https://github.com/FoedusProgramme/Gramophone) (GPL-3.0). Les modifications restent sous la même licence.

Base : Gramophone, branche `beta`, commit du 2 octobre 2026 (dépôt amont ajouté comme remote `upstream`).

## Cahier des charges

1. Application libre, publiée sur GitHub.
2. Choix d'un dossier racine de musique, avec lecture de tous ses sous-dossiers.
3. Aucune lecture de musique en ligne.
4. Liste de lecture automatique de tous les morceaux, en ordre aléatoire, renouvelée automatiquement à la fin.
5. Intégration à Android Auto.
6. Commandes vocales : suivant, précédent, « efface ce morceau ».
7. Score de skip par morceau : +1 si passé dans la première minute, −1 si écouté en entier.
8. Proposition d'effacement des morceaux dont le score dépasse un seuil réglable (3 par défaut).
9. Récupération automatique des jaquettes.

## Pourquoi Gramophone

- Une seule session Media3 (`MediaLibraryService`) pour le téléphone et Android Auto.
- Pas de permission `INTERNET`.
- Re-mélange de la file à la fin quand l'aléatoire et « répéter tout » sont actifs.
- Suppression de fichiers déjà gérée via la demande de suppression d'Android 11+.
- Boutons personnalisés déjà utilisés (favori), réutilisables pour « efface ce morceau ».

## Points d'attention

- Service principal : `app/src/main/java/org/akanework/gramophone/logic/GramophonePlaybackService.kt` (environ 2 000 lignes).
- Media3 est un sous-module Git (fork) : `git submodule update --init --recursive` avant de compiler.
- Bibliothèque lue via MediaStore avec liste noire et liste blanche de dossiers ; la liste blanche (sous-dossiers inclus) sert de filtre « dossier racine ».
- Aucune base de données locale : le score de skip est rangé dans les préférences privées `skip_scores` (clé = chemin du fichier).
- Compilation vérifiée par Frank dans Android Studio le 10 octobre 2026. Le SDK Android n'est pas téléchargeable depuis l'espace de travail cloud : rien n'y est compilé.

## Feuille de route

1. [fait] Traduction française intégrale (chaînes, fiche de l'application, notes de version).
2. [fait] Renommage de l'application en Sillage (nom, identifiant `io.github.franksauret.sillage`, icône provisoire).
3. [fait] Filtre sur un dossier racine (liste blanche existante, renommée « Dossiers racines »).
4. [fait] Aléatoire perpétuel par défaut. File reconnue à son titre (`perpetual_shuffle`) ; refaite depuis la bibliothèque quand son dernier morceau commence (`renewPerpetualShuffle`), ou par le menu « Nouvelle liste aléatoire ».
5. [fait, à tester] Score de skip et proposition d'effacement (`logic/utils/SkipScores.kt`, `ui/SkipReview.kt`).
6. Bouton « efface ce morceau » dans Android Auto.

## Identité : décisions

- Identifiant d'application : `io.github.franksauret.sillage` (choix par défaut, 9 octobre 2026). L'espace de noms Kotlin `org.akanework.gramophone` est conservé pour faciliter la reprise des évolutions amont.
- Version : `versionName` 2026.10.0, `versionCode` 25 (suite de Gramophone 24).

## Traduction : décisions

- Libellés des autorisations système (`grant_images`, `deny_images`, `grant_audio`) : « Photos et vidéos » et « Musique et audio », vérifiés sur appareil le 9 octobre 2026.
- `layout/lyric_widget_preview.xml` : les cinq lignes d'exemple restent en anglais (décision de Frank, 9 octobre 2026).

## Dépôt et organisation

- Dépôt : https://github.com/FrankSAURET/Sillage (branche `main`). Frank travaille seul : enregistrer et envoyer directement sur `main`, sans branche ni PR (décision du 9 octobre 2026).
- Compilation : `docs/compiler-avec-android-studio.md`. Rapports de plantage : tickets GitHub du dépôt, à défaut mail à frank.sauret.pro@gmail.com.
- L'amont Gramophone n'est pas inclus dans l'historique : le premier commit est un import de son état du 2 octobre 2026. Pour récupérer ses évolutions, ajouter un remote `upstream` et comparer.
- `readme.md` et `readme_ja.md` d'origine sont dans `A Examiner/` (ménage du 10 octobre 2026), avec les fiches fastlane des autres langues, les notes de version de Gramophone, ses captures d'écran et l'icône de Noël.
- Après un clonage : `git submodule update --init --recursive`.
