# spring-batch-poc
Ce projet est un POC réalisé avec Spring Batch.

Ce code exécute les étapes suivantes tant que le dossier de lecture n'est pas vide :
- Déplace un fichier du dossier de lecture au dossier de travail
- Lit le nom du fichier (<== élémentaire mais on pourrait lire d'autres métadonnées)
- Logue le nom du fichier (<== dans un vrai script batch, c'est ici qu'on modifierait des données en base en fonction des métadonnées, par exemple)
