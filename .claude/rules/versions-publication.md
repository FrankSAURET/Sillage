# Versions, journal des modifications, traductions, publication

Règles transprojet de Frank (issues de Kablix), valables dans tous ses projets.

## Publication (VS Code Marketplace / éditeurs)
- **INTERDICTION ABSOLUE : ne jamais publier, lancer `vsce publish`, `ovsx`, ou toute commande d'envoi vers un éditeur sans l'accord explicite, préalable et non ambigu de Frank dans la conversation en cours.** Une version prête, un paquet créé ou une tâche `todo.md` ne constituent jamais cet accord.
- **Frank publie lui-même, par défaut.** Ne jamais le relancer sur la publication : pas de « publication non faite, attend ton accord », ni en fin de réponse, ni en ⏳ dans `todo.md`. S'il veut que Claude publie, il le demande explicitement. Tous projets.
- Publisher/éditeur de Frank : **`electropol-fr`**. Toujours ce nom dans `package.json` (`"publisher": "electropol-fr"`) et dans les commandes `vsce publish` / `ovsx`.
- Tout autre publisher = erreur : la publication part sur un compte qui n'est pas le sien (déjà arrivé le 30/07/2026, publication perdue).

## Suivi todo et versions (tout projet)
- Fichier `todo.md` : coches vertes ✅ pour le fait (jamais `- [x]`), ⏳ pour le différé/hors-périmètre, ⬜ pour le reste à faire.
- Liste « à faire » en tête : items **numérotés** (1. 2. 3.).
- Journal organisé par version : une section `# vX` par lot. **Le numéro de version est TOUJOURS au-dessus** de ses modifications (jamais en dessous) ; versions les plus récentes en haut du fichier. Items **numérotés** (1. 2. 3.) chacun préfixé ✅/⏳/ℹ️.
- Un **nouveau numéro interne à chaque lot** livré (bump du `buildNumber` dans le manifeste : package.json, etc.). La version publique ne bouge qu'à la publication.

### Deux numéros de version — règle FORTE, tous projets
- **Version publique** : calver `ANNÉE.MOIS.incrément` (`2026.8.102`). Elle vaut toujours celui de la **PRÉCÉDENTE publication** et n'avance qu'à une publication réelle.
- **Version interne (développeurs)** : la publique suivie d'un 4e segment, `2026.8.102.7`. Champ `buildNumber` du manifeste (jamais dans `version`, qui reste semver-compatible). Compteur qui **démarre à 1 et ne repart JAMAIS à 0** — ni au changement de mois, ni au bump du public. Pas de zéro à gauche.
- **À chaque lot livré : on incrémente le buildNumber**, pas la version publique. Le public ne bouge qu'au moment de publier (et applique alors le calver du mois du jour).
- L'interface affiche le numéro à 4 segments **seulement hors production** ; l'utilisateur ne voit jamais que le public.

### Versions calver (ANNÉE.MOIS.incrément) — règle FORTE, tous projets
- Avant CHAQUE bump : comparer le mois de la version courante à la **date du jour**. Mois différent → passer à `ANNÉE.MOISDUJOUR.0` (l'incrément **repart à 0**), jamais `.suivant` du mois écoulé.
- Exemple : version `2026.7.269` bumpée le 5 août 2026 → **`2026.8.0`** (et non `2026.7.270`).
- Vérifier la date réelle, ne jamais la déduire de la dernière version du fichier.
- **Calver pour TOUT numéro de version**, pas seulement le manifeste du projet : composants publiés, plugins, paquets, ressources versionnées, bibliothèques internes. Aucun semver `1.2.0` nulle part.
- À chaque lot livré, systématiquement : **commit + push**.
- **Jamais de build d'artefact automatique** (`.vsix`, exe, paquet…) : attendre une demande explicite de Frank.

### CHANGELOG.md — rempli AU FIL DE L'EAU, sous la prochaine publication
- **À chaque lot livré, le CHANGELOG est complété** — il ne s'écrit plus juste avant de publier. (Règle posée le 12/09/2026 ; elle REMPLACE l'ancienne « ne jamais y toucher spontanément ».)
- Les entrées s'ajoutent sous le numéro de la **PROCHAINE publication**, et à la place de la date on écrit **`prochaine publication`** :
  `## 2026.9.4 (prochaine publication)`
- Quand Frank dit **« on prépare la publication »** : on garde ce numéro et on remplace `prochaine publication` par la **date du jour**. Le numéro ne change pas à ce moment-là, il était déjà le bon.
- **Une section DATÉE = une version PUBLIÉE. Règle générale, tous projets.** Une date dans un titre de CHANGELOG signifie que ce lot est en ligne : on n'y ajoute plus rien, on ouvre la section suivante. Doute sur ce qui est publié → vérifier (historique git, place de marché) ou demander à Frank. Ne jamais supposer.
- **Tous les CHANGELOG de Frank s'écrivent en FRANÇAIS**, dans tous les projets (les traducteurs automatiques font très bien le reste). Un CHANGELOG existant encore en anglais se poursuit en français à partir de la section en cours ; on ne réécrit pas l'historique.
- **Structure imposée, tous projets** : chaque version du CHANGELOG se découpe en 3 parties, dans cet ordre — **Nouveauté**, **Modification**, **Correction**. Une partie vide s'omet.
- Le CHANGELOG est écrit **côté utilisateur** : ce que le lot change pour qui se sert du logiciel, pas le détail du code (ça, c'est `todo.md`).

### Traductions — jamais au fil de l'eau, tout avant publication
- **Ne JAMAIS créer ni retoucher une traduction pendant le travail courant.** Tous projets, toutes langues, tous formats : fichiers `l10n`/`i18n`, docs traduites (`docs/en/`, `docs/fr/`…), README localisés, chaînes d'interface.
- Seule la **langue de base** (celle où le texte est écrit à l'origine : la chaîne dans le code, la doc que Frank rédige) est tenue à jour au fil des lots.
- Les autres langues se font **en un seul lot, juste avant une publication**, sur demande expresse de Frank. (Le CHANGELOG, lui, ne suit plus cette règle : il se remplit au fil de l'eau et reste en français.)
- Un composant, une fiche d'aide ou une chaîne nouvelle est **livrable sans sa traduction** : noter le manque en ⏳ dans `todo.md`, ne pas bloquer le lot.
- Une instruction de projet qui exige « FR **et** EN » se lit désormais « langue de base maintenant, autre langue avant publication ».
