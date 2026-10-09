# Sillage — règles de travail

Projet : @SILLAGE.md (cahier des charges, choix techniques, feuille de route, décisions). Ne pas le recopier ici ; le mettre à jour quand une décision change.

Règles transprojet de Frank : `.claude/rules/` (langue et style, méthode, Windows, code et Markdown, versions et publication).

- Réponds et commente en français ; le code, les identifiants et les messages d'enregistrement restent cohérents avec l'existant.
- Toute chaîne visible va dans `res/values/strings.xml` ET `res/values-fr/strings.xml` (vouvoiement, « morceau », « file d'attente »).
- Ne jamais ajouter la permission `INTERNET` ni de lecture en ligne.
- Le projet dérive de Gramophone (GPL-3.0) : conserver la licence et les mentions d'origine.
- Ne jamais affirmer qu'une construction ou un test réussit sans l'avoir exécuté ; le SDK Android n'est pas disponible dans l'espace cloud, le dire si c'est le cas.
- Après un clonage : `git submodule update --init --recursive`.
- Fin de session : dire à Frank ce qui a changé, pour qu'il synchronise son dépôt local.

## Dérogations aux règles transprojet (priment sur `.claude/rules/`)
- Traductions : pour Sillage, elles se font au fil de l'eau (français et langue de base ensemble), pas en un lot avant publication.
- Versions : `versionName` suit le calver ANNÉE.MOIS.incrément (champ libre, pas bloquant). `versionCode` reste un entier strictement croissant, +1 à chaque publication, indépendant du calver. Le `buildNumber` à 4 segments n'existe pas ici.
- Journal des modifications : `CHANGELOG.md` en français selon la règle transprojet ; les notes de version fastlane se nomment par `versionCode`.
