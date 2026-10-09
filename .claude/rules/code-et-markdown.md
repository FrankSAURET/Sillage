# Style de code et Markdown

Règles transprojet de Frank (issues de Kablix), valables dans tous ses projets.

## Style de code
- Langages : TypeScript, Python, C (Arduino), C#, HTML, CSS, JavaScript.
- Commenter les grandes lignes et les passages non évidents — pas de commentaires redondants.
- Indentation : tabs = 3 ou 4 espaces selon le projet — toujours garder le style existant du fichier.
- Ne jamais imposer un style différent de celui du fichier ouvert.

## Markdown (`.md`, tous projets) — pas de saut de ligne dans le texte
- **Un paragraphe = une seule ligne**, aussi longue soit-elle. Jamais de retour à la ligne à 80 colonnes pour « mettre en forme » : ni dans un paragraphe, ni dans une puce, ni dans une citation `>`.
- Même chose pour les **deux espaces en fin de ligne** (saut forcé) : jamais au milieu d'une phrase.
- Le passage à la ligne ne sert qu'à séparer des blocs : ligne vide entre paragraphes, nouvelle puce, titre, tableau, bloc de code.
- Vaut aussi pour le Markdown **produit par un script** (modèle dans une chaîne de caractères) : corriger le modèle, pas seulement le fichier généré.
