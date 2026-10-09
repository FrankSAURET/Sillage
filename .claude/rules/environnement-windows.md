# Environnement Windows

Règles transprojet de Frank (issues de Kablix), valables dans tous ses projets.

## Environnement Windows — règles anti-erreurs
- Script > 3 lignes ou contenant quotes/backslash/regex : écrire un fichier `.mjs`/`.py` dans le scratchpad puis l'exécuter. Jamais de one-liner `node -e` / `python -` fragile.
- Script node avec imports npm : l'exécuter depuis la racine du projet (accès à `node_modules`), pas depuis le scratchpad.
- Chemins : toujours avec lettre de lecteur (`O:/Jeux/...`) — jamais `/o/...` dans Python, jamais `/tmp` (utiliser le scratchpad).
- Bash = POSIX pur, PowerShell = cmdlets : ne jamais mélanger les deux syntaxes dans une même commande.
