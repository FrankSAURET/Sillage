# À faire
1. ⬜ Tester le logo de Frank en icône (non compilé) : lanceur, icône à thème Android 13+, notification, Android Auto (désinstaller puis réinstaller si l'ancienne icône reste en cache) ; dire si le motif est trop petit ou trop grand dans le cercle.
1. ⬜ Tester Android Auto (non compilé) : onglet « Auto » en premier avec « Liste aléatoire » ; réglage Comportement > « Lancer la liste aléatoire par défaut » ; vérifier qu'un morceau joué ou passé disparaît de la file.
1. ⬜ Tester la liste aléatoire perpétuelle (non compilée) : menu « Nouvelle liste aléatoire », puis aller au dernier morceau de la file et vérifier qu'une nouvelle liste démarre sans couper le morceau.
1. ⬜ Bouton « efface ce morceau » dans Android Auto.


1. ⏳ Autres langues que l'anglais et le français : les textes modifiés pour Sillage (rapport de plantage, dossiers racines, type de paquet) y sont retirés et s'affichent en anglais.


# 2026.10.0 (prochaine publication)

0. ✅ Android Auto : icône = logo de Frank (`mipmap-*` adaptatif, monochrome, anciens lanceurs ; petites icônes de notification en png ; fond `drawable/ic_launcher_bg.xml`, ambre en debug ; boîte « À propos » `ic_logo.webp`). Premier onglet « Auto » avec l'élément « Liste aléatoire » (`shuffle_all`) qui lance toute la bibliothèque en aléatoire perpétuel. Réglage Comportement `auto_shuffle` : lance la liste dès l'ouverture de l'onglet Auto, une fois par connexion, si rien n'est en lecture. File renommée « Liste aléatoire » ; un morceau joué ou passé en sort (`removeFromPerpetualQueue`), liste refaite quand il n'en reste qu'un. Non compilé.
1. ✅ Allure : thème retouché sans Material You — palette fixe générée avec materialyoucolor depuis le bleu du logo (#1F6BD1) et son violet (#A972EB) en tertiaire, coins 4/8 dp, ombres retirées, couleurs selon la jaquette désactivées par défaut.
2. ✅ Liste aléatoire perpétuelle : la file porte le titre `perpetual_shuffle` ; quand son dernier morceau commence, `renewPerpetualShuffle` recharge toute la bibliothèque (liste blanche et liste noire appliquées) sans couper le morceau en cours, qui ouvre le nouveau tirage. Menu « Nouvelle liste aléatoire » et raccourci du lanceur refont la liste. Une autre file (album, dossier) en aléatoire avec « répéter tout » garde le simple re-mélange de Gramophone.
3. ✅ Fiche fastlane : `icon.png` (512 px) et `featureGraphic.png` (1024 × 500, texte anglais et français) dans `en-US/images` et `fr-FR/images`.
4. ✅ Score de skip testé par Frank (10 octobre 2026).
5. ✅ Score de skip : `SkipScores` (préférences privées `skip_scores`, clé = chemin du fichier). +1 si le morceau passe au suivant dans sa première minute, −1 à la fin naturelle ; revenir au précédent ou sauter ailleurs ne compte pas. Accroché à `onPositionDiscontinuity` du service, donc vaut aussi pour Android Auto.
6. ✅ Proposition d'effacement : boîte de dialogue à l'ouverture de l'application (une fois par démarrage) listant les morceaux dont le score atteint le seuil, tous cochés. « Effacer » passe par la suppression existante (confirmation Android 11+) ; « Garder » remet les scores à zéro ; « Plus tard » ne fait rien. Réglages : seuil 1 à 10 (3 par défaut), « Proposer maintenant » avec le nombre de morceaux concernés.
7. ℹ️ Seuil : « atteint » (score ≥ seuil), choix par défaut ; à passer en « dépasse » (>) si Frank préfère.
8. ✅ Ménage Gramophone : nom remplacé par Sillage dans toutes les langues (y compris les translittérations), noms des journaux exportés et des listes de lecture exportées, fiches fastlane anglaise et française réécrites. Restent volontairement : mention « Dérivé de Gramophone », licence, contributeurs et traducteurs d'origine, identifiants internes du code (`org.akanework.gramophone`, `GramophonePlaybackService`, `Theme.Gramophone`…).
9. ✅ Rangés dans `A Examiner/` : `README_GRAMOPHONE.md`, `readme_ja.md`, modèle de description fastlane, script des contributeurs, synchronisation Weblate, fiches fastlane des 29 autres langues, notes de version 1 à 24, captures d'écran et icône de Gramophone, icône de Noël.
10. ✅ Version de mise au point : icône Sillage sur fond ambre (remplace l'icône de Noël de Gramophone).
11. ✅ Test Android Auto réussi avec l'émulateur d'écran de voiture (Frank, 10 octobre 2026).
12. ✅ Première construction et premier lancement réussis dans Android Studio (Frank, 10 octobre 2026).
13. ✅ Rapports de plantage : bouton « Signaler sur GitHub », ouvre un ticket pré-rempli sur FrankSAURET/Sillage (extrait du journal dans l'adresse, journal complet copié dans le presse-papiers). Sans navigateur : mail à frank.sauret.pro@gmail.com avec le journal en pièce jointe.
14. ✅ Pas à pas Android Studio : `docs/compiler-avec-android-studio.md`.
15. ✅ Renommage en Sillage : nom affiché `Sillage` (toutes langues), identifiant `io.github.franksauret.sillage` (l'espace de noms du code reste `org.akanework.gramophone`), mentions visibles de Gramophone remplacées en anglais et en français, profil de référence mis à jour.
16. ✅ Nouvelle icône : premier plan adaptatif, monochrome (Android 13+, notifications, Android Auto), écran de démarrage, icônes webp anciennes versions.
17. ✅ À propos : lien du dépôt vers Sillage, lien Telegram remplacé par « Dérivé de Gramophone ».
18. ✅ Version : `versionName` 2026.10.0, `versionCode` 25.
19. ✅ Aléatoire perpétuel : sans file sauvegardée (premier lancement), tous les morceaux sont mis en file en aléatoire avec « répéter tout » ; le raccourci « tout en aléatoire » active aussi « répéter tout ». Le re-mélange à chaque tour existait déjà dans Gramophone.
20. ✅ Dossier racine : la liste blanche de Gramophone filtrait déjà un dossier et tous ses sous-dossiers ; renommée « Dossiers racines », entrée de réglage « Dossiers de musique ».
21. ✅ Import de Gramophone (GPL-3.0), sous-modules conservés.
22. ✅ Traduction française intégrale (509 chaînes, fiche fastlane, notes de version).
23. ✅ Documentation : SILLAGE.md, README, CLAUDE.md, règles transprojet dans `.claude/rules/`.
24. ℹ️ Dérogations Sillage notées dans CLAUDE.md (traductions au fil de l'eau, versions Android).
