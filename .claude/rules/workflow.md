# Méthode de travail et sécurité des fichiers

Règles transprojet de Frank (issues de Kablix), valables dans tous ses projets.

## Workflow
- Question bloquante → demander. Sinon → faire.
- Plan seulement si impact important (irréversible, multi-fichiers, prod).
- Agents spécialisés pour tâches complexes.
- Tester avant de livrer.
- Si erreur : corriger silencieusement, re-livrer.

## Confirmations et autonomie
- Actions risquées : documenter l'annulation dans le tableau final.
- Plusieurs approches : [Option A] / [Option B] en 1 clic.

## Suppression de fichiers — INTERDITE sauf demande explicite
- **Ne JAMAIS effacer un fichier** (`rm`, `git rm`, `Remove-Item`, écrasement) sans que Frank l'ait demandé nommément. « Fais le ménage », « nettoie », « c'est inutile » n'autorisent AUCUNE suppression.
- Ménage = **déplacer** dans un dossier `A Examiner/` à la racine du projet, arborescence d'origine conservée dessous (`A Examiner/media/x.webp`). Puis **prévenir Frank** : liste des fichiers déplacés, taille, raison — il tranche.
- `A Examiner/` et `Archives/` sont **versionnés** (git) et seulement exclus de l'artefact publié (`.vscodeignore` etc.). Ne jamais les ignorer dans git, ne jamais les vider.
- `Archives/` = tri déjà fait par Frank, conservé pour de bon. **Intouchable.**

## Reprise et longues tâches
- « reprend » / « continue » / « go » = reprendre immédiatement la tâche en cours : lire `todo.md` / `PROGRESSION.md` / le plan approuvé, continuer sans poser de question.
- Toute tâche multi-session : tenir l'état à jour dans un fichier (`todo.md`, `PROGRESSION.md`) après **chaque lot**, pour qu'un simple « reprend » suffise dans une nouvelle session.
- Proche de la saturation du contexte : sauvegarder l'état (fichier de progression + commit/push si projet git) AVANT de continuer.
- Après un compactage de contexte : re-`Read` un fichier avant tout `Edit` (l'état lu est perdu) ; rester en français et en MODE CAVEMAN.

## Nom de session — renommer AU DÉBUT
- Sur un `/reprend` (ou « continue », « go », tout équivalent) : **dès que `todo.md` est lu et la tâche identifiée**, renommer la session. Pas à la fin — à la fin, la session est souvent close ou le contexte effacé.
- Ne renommer que si le nom courant est illisible ou générique (`/reprend`, `continue`, `go`, `/livre`, un bout de prompt tronqué). Un nom déjà clair se garde tel quel.
- Titre : quelques mots max, en français, décrivant la tâche réellement prise (ex. « Autoroutage : coudes en trop », « Fiche d'aide transistor »).
- Renommage : commande `/rename <titre>` (ou l'équivalent de l'interface en cours). Si elle n'est pas invocable depuis la conversation, afficher la ligne `/rename <titre>` pour que Frank la colle.

## Build et tests
- Corriger toutes les erreurs de build d'un coup, ne pas montrer les erreurs au fur et à mesure.
- Lancer les tests automatiquement après chaque modification si une suite de tests existe.
