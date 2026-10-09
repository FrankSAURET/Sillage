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
- Bibliothèque lue via MediaStore avec liste noire de dossiers ; il manque un filtre « dossier racine ».
- Aucune base de données locale : le score de skip demandera un stockage à créer.
- Compilation non vérifiée à ce jour : le SDK Android n'est pas téléchargeable depuis l'espace de travail cloud.

## Feuille de route

1. [fait] Traduction française intégrale (chaînes, fiche de l'application, notes de version).
2. Renommage de l'application en Sillage (nom, identifiant, icône).
3. Filtre sur un dossier racine.
4. Aléatoire perpétuel par défaut.
5. Score de skip et écran de suppression.
6. Bouton « efface ce morceau » dans Android Auto.

## Traduction : points à vérifier sur un appareil en français

- Libellés des autorisations système dans `grant_images`, `deny_images`, `grant_audio` : « Photos et vidéos » et « Musique et audio » sont mes traductions des libellés anglais ; à comparer avec ce qu'Android affiche réellement.
- `layout/lyric_widget_preview.xml` contient cinq lignes d'exemple en anglais, non traduites (voir la décision en suspens).
