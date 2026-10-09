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
