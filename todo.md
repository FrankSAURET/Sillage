# À faire

1. ⬜ Score de skip par morceau et écran de suppression (seuil réglable, 3 par défaut).
2. ⬜ Bouton « efface ce morceau » dans Android Auto.
3. ⏳ Icône : dessin provisoire (croche et sillage de vagues). Fiche fastlane sans icône, bannière ni captures d'écran (celles de Gramophone sont dans `A Examiner/`).
4. ⏳ Autres langues que l'anglais et le français : les textes modifiés pour Sillage (rapport de plantage, dossiers racines, type de paquet) y sont retirés et s'affichent en anglais.
5. ⏳ `A Examiner/` : fichiers de Gramophone mis de côté le 10 octobre 2026, à trier par Frank.

# 2026.10.0 (prochaine publication)

1. ✅ Ménage Gramophone : nom remplacé par Sillage dans toutes les langues (y compris les translittérations), noms des journaux exportés et des listes de lecture exportées, fiches fastlane anglaise et française réécrites. Restent volontairement : mention « Dérivé de Gramophone », licence, contributeurs et traducteurs d'origine, identifiants internes du code (`org.akanework.gramophone`, `GramophonePlaybackService`, `Theme.Gramophone`…).
2. ✅ Rangés dans `A Examiner/` : `README_GRAMOPHONE.md`, `readme_ja.md`, modèle de description fastlane, script des contributeurs, synchronisation Weblate, fiches fastlane des 29 autres langues, notes de version 1 à 24, captures d'écran et icône de Gramophone, icône de Noël.
3. ✅ Version de mise au point : icône Sillage sur fond ambre (remplace l'icône de Noël de Gramophone).
4. ✅ Test Android Auto réussi avec l'émulateur d'écran de voiture (Frank, 10 octobre 2026).
5. ✅ Première construction et premier lancement réussis dans Android Studio (Frank, 10 octobre 2026).
6. ✅ Rapports de plantage : bouton « Signaler sur GitHub », ouvre un ticket pré-rempli sur FrankSAURET/Sillage (extrait du journal dans l'adresse, journal complet copié dans le presse-papiers). Sans navigateur : mail à frank.sauret.pro@gmail.com avec le journal en pièce jointe.
7. ✅ Pas à pas Android Studio : `docs/compiler-avec-android-studio.md`.
8. ✅ Renommage en Sillage : nom affiché `Sillage` (toutes langues), identifiant `io.github.franksauret.sillage` (l'espace de noms du code reste `org.akanework.gramophone`), mentions visibles de Gramophone remplacées en anglais et en français, profil de référence mis à jour.
9. ✅ Nouvelle icône : premier plan adaptatif, monochrome (Android 13+, notifications, Android Auto), écran de démarrage, icônes webp anciennes versions.
10. ✅ À propos : lien du dépôt vers Sillage, lien Telegram remplacé par « Dérivé de Gramophone ».
11. ✅ Version : `versionName` 2026.10.0, `versionCode` 25.
12. ✅ Aléatoire perpétuel : sans file sauvegardée (premier lancement), tous les morceaux sont mis en file en aléatoire avec « répéter tout » ; le raccourci « tout en aléatoire » active aussi « répéter tout ». Le re-mélange à chaque tour existait déjà dans Gramophone.
13. ✅ Dossier racine : la liste blanche de Gramophone filtrait déjà un dossier et tous ses sous-dossiers ; renommée « Dossiers racines », entrée de réglage « Dossiers de musique ».
14. ✅ Import de Gramophone (GPL-3.0), sous-modules conservés.
15. ✅ Traduction française intégrale (509 chaînes, fiche fastlane, notes de version).
16. ✅ Documentation : SILLAGE.md, README, CLAUDE.md, règles transprojet dans `.claude/rules/`.
17. ℹ️ Dérogations Sillage notées dans CLAUDE.md (traductions au fil de l'eau, versions Android).
