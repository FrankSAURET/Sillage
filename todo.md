# À faire

1. ⬜ Vérifier la construction dans Android Studio (jamais compilée à ce jour, SDK absent du cloud). Avant : créer `package.properties` à la racine avec `releaseType=SelfBuilt` (fichier ignoré par git, lu sans repli par `app/build.gradle.kts`).
2. ⬜ Score de skip par morceau et écran de suppression (seuil réglable, 3 par défaut).
3. ⬜ Bouton « efface ce morceau » dans Android Auto.
4. ⬜ Rapports de plantage : `BugHandlerActivity.kt` les envoie encore par mail au développeur de Gramophone (`nift4@posteo.net`). Choisir une adresse Sillage ou retirer l'envoi par mail.
5. ⏳ Icône : dessin provisoire (croche et sillage de vagues) ; variante de Noël `ic_launcher_xmas` inutilisée, restée celle de Gramophone.
6. ⏳ Fiches fastlane : titre passé à Sillage dans toutes les langues ; descriptions encore celles de Gramophone.
7. ⏳ Autres langues que l'anglais et le français : textes mentionnant encore Gramophone, libellés « dossiers racines » non repris.

# 2026.10.0 (prochaine publication)

1. ✅ Renommage en Sillage : nom affiché `Sillage` (toutes langues), identifiant `io.github.franksauret.sillage` (l'espace de noms du code reste `org.akanework.gramophone`), mentions visibles de Gramophone remplacées en anglais et en français, profil de référence mis à jour.
2. ✅ Nouvelle icône : premier plan adaptatif, monochrome (Android 13+, notifications, Android Auto), écran de démarrage, icônes webp anciennes versions.
3. ✅ À propos : lien du dépôt vers Sillage, lien Telegram remplacé par « Dérivé de Gramophone ».
4. ✅ Version : `versionName` 2026.10.0, `versionCode` 25.
5. ✅ Aléatoire perpétuel : sans file sauvegardée (premier lancement), tous les morceaux sont mis en file en aléatoire avec « répéter tout » ; le raccourci « tout en aléatoire » active aussi « répéter tout ». Le re-mélange à chaque tour existait déjà dans Gramophone.
6. ✅ Dossier racine : la liste blanche de Gramophone filtrait déjà un dossier et tous ses sous-dossiers ; renommée « Dossiers racines », entrée de réglage « Dossiers de musique ».
7. ✅ Import de Gramophone (GPL-3.0), sous-modules conservés.
8. ✅ Traduction française intégrale (509 chaînes, fiche fastlane, notes de version).
9. ✅ Documentation : SILLAGE.md, README, CLAUDE.md, règles transprojet dans `.claude/rules/`.
10. ℹ️ Dérogations Sillage notées dans CLAUDE.md (traductions au fil de l'eau, versions Android).
