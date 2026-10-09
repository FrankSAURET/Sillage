# Sillage — règles de travail

Avant toute tâche, lis `SILLAGE.md` (cahier des charges, choix techniques, feuille de route, décisions). Ne le recopie pas ici ; mets-le à jour quand une décision change.

- Réponds et commente en français ; le code, les identifiants et les messages de commit restent cohérents avec l'existant.
- Toute chaîne visible va dans `res/values/strings.xml` ET `res/values-fr/strings.xml` (vouvoiement, « morceau », « file d'attente »).
- Ne jamais ajouter la permission `INTERNET` ni de lecture en ligne.
- Le projet dérive de Gramophone (GPL-3.0) : conserver la licence et les mentions d'origine.
- Ne jamais affirmer qu'un build ou un test passe sans l'avoir exécuté ; le SDK Android n'est pas disponible dans l'espace cloud, le dire si c'est le cas.
- Après un clonage : `git submodule update --init --recursive`.
- Fin de session : dire à Frank ce qui a changé, pour qu'il synchronise son dépôt local.

<!-- Configs transprojet de Frank (issues de Kablix) : à ajouter ici -->
