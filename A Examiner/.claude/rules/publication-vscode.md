# Publication VS Code (hors sujet pour Sillage)

Règle transprojet de Frank pour ses extensions VS Code. Déplacée hors du chargement automatique du dépôt ; à copier dans `~/.claude/rules/`.

## Publication (VS Code Marketplace / éditeurs)
- **INTERDICTION ABSOLUE : ne jamais publier, lancer `vsce publish`, `ovsx`, ou toute commande d'envoi vers un éditeur sans l'accord explicite, préalable et non ambigu de Frank dans la conversation en cours.** Une version prête, un paquet créé ou une tâche `todo.md` ne constituent jamais cet accord.
- **Frank publie lui-même, par défaut.** Ne jamais le relancer sur la publication : pas de « publication non faite, attend ton accord », ni en fin de réponse, ni en ⏳ dans `todo.md`. S'il veut que Claude publie, il le demande explicitement. Tous projets.
- Publisher/éditeur de Frank : **`electropol-fr`**. Toujours ce nom dans `package.json` (`"publisher": "electropol-fr"`) et dans les commandes `vsce publish` / `ovsx`.
- Tout autre publisher = erreur : la publication part sur un compte qui n'est pas le sien (déjà arrivé le 30/07/2026, publication perdue).
