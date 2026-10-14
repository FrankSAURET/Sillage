# À faire


# 2026.10.0 (prochaine publication)

39. ✅ Effacement auto : titre de catégorie (doublon bleu) et titre « Comment ça marche » supprimés ; l'explication garde l'icône info, comme dans Audio. Compilé, non vu sur appareil.

40. ℹ️ Menu 3 points > Égaliseur : ouvre l'égaliseur du système Android (`AudioEffect.ACTION_DISPLAY_AUDIO_EFFECT_CONTROL_PANEL`) pour la session audio en cours ; message « égaliseur introuvable » si l'appareil n'en a pas.

38. ✅ Icônes Fluent Color / Fluent Emoji validées par Frank (14 octobre 2026).

37. ✅ Icônes des réglages : la teinte `colorOnSurface` de `layout/preference_icon.xml` aplatissait toutes les icônes en une couleur ; retirée, les couleurs Fluent s'affichent. Compilé, non vérifié sur appareil.

36. ✅ « Effacement auto » est une entrée à la racine des paramètres (`SkipSettingsActivity`, `settings_skip.xml`, icône corbeille `ic_delete_auto`) ; retirée de Comportement. Compilé, non testé sur appareil.

35. ✅ Icônes des réglages : toutes passées en couleur (Fluent Color / Fluent Emoji, MIT) ; la ligne « Lecteur » a sa propre icône `ic_setting_play`. Les ombres floues des Fluent Emoji et les traits seuls sont omis. Rendu non vérifié sur appareil.

32. ✅ Icônes colorées Fluent Color (Microsoft, MIT) : 22 icônes remplacées sous le même nom (réglages, dossier, avertissement, erreur, cœur plein, casque, palette, horloge, partage…). Couleurs unies dans `drawable/`, dégradés dans `drawable-v24/` (Android 7+). Crédits dans `NOTICE-icones.md`. Non remplacées : transport (lecture, pause, suivant…), bascules (répéter, aléatoire, favori vide), flèches, croix, coches. Une icône teintée par le thème perd ses couleurs. Ressources compilées (`processDebugResources`), rendu non vérifié sur appareil.
33. ✅ Traduit tout : aucune chaîne absente de `values-fr`.
34. ✅ Code des lots 25 à 31 compilé (`compileDebugKotlin`, JDK d'Android Studio : le JDK 18 du système est trop ancien, il faut le JDK 21).

25. ✅ Effacement auto (Réglages > Comportement, catégorie renommée) : explication, « Effacer automatiquement », seuil, durée de « passé » (`skip_window`, 30 s à 4 min par pas de 30 s, 1 min par défaut), écran `SkipListSettingsActivity` à deux onglets (« À effacer », « Score positif ») avec remise à 0 par morceau, « Tout réinitialiser » par liste, « Effacer maintenant » (onglet « À effacer ») et « Réinitialiser tous les scores » dans les réglages. Non compilé.
26. ℹ️ Effacement automatique : exécuté au démarrage de l'application (une fois par démarrage), pas en cours d'écoute : la suppression de fichiers a besoin de l'écran principal, et Android 11+ demande toujours sa confirmation.
27. ✅ Score confirmé par le code (`updateSkipScore`, GramophonePlaybackService) : fin naturelle du morceau = −1 ; « suivant » avant la durée réglée = +1 ; « précédent » ou saut ailleurs = 0 ; « suivant » après la durée = 0. Un morceau écouté en entier et jamais passé a donc un score négatif.
28. ✅ Un morceau en favori n'est jamais proposé ni effacé (`SkipReview.toDelete`) ; il reste visible dans la liste « Score positif ».
30. ✅ Propriétés d'un morceau : ligne « Score d'effacement » avec bouton de remise à 0 (`DetailDialogFragment`, `fragment_info_song.xml`).
31. ✅ Notification « Morceaux à effacer » à chaque palier de 10 morceaux dans la liste à effacer (`SkipNotifier`, canal `skipReview`). Jamais envoyée tant qu'Android Auto (ou une voiture) est connecté à la session : le palier reste en attente jusqu'à la prochaine vérification hors voiture. Inactive avec « Effacer automatiquement ». Toucher la notification ouvre la liste « À effacer ». Non compilé.
29. ⏳ Chaînes ajoutées en anglais et en français seulement (autres langues absentes, comme avant).

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
