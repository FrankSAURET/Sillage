# Icône et images de Sillage

Toutes les images de base de l'application et de la fiche de publication, avec la façon la plus simple de les remplacer.

## Méthode la plus simple : l'assistant d'Android Studio

1. Préparer le dessin en SVG (de préférence) ou en PNG 1024 × 1024, motif centré, avec une bonne marge : seul le disque central d'environ 66 % est toujours visible.
2. Dans Android Studio, panneau **Project** (vue « Android »), clic droit sur `app` > **New > Image Asset**.
3. **Icon type** : « Launcher Icons (Adaptive and Legacy) ». **Name** : `ic_launcher` (garder ce nom).
4. Onglet **Foreground Layer** : choisir le fichier. Onglet **Background Layer** : une couleur. Onglet **Options** : laisser « Legacy Icon » coché.
5. **Next** puis **Finish** : l'assistant remplace d'un coup `drawable/ic_launcher_foreground.xml`, `mipmap-anydpi-v26/ic_launcher.xml`, `values/ic_launcher_background.xml` et les `mipmap-*/ic_launcher.webp`.

Ensuite, refaire à la main les versions monochromes (voir le tableau) : l'assistant ne les touche pas.

## Où se trouve chaque image

Chemins relatifs à `app/src/main/res/`.

| Image | Fichier(s) | Format |
|---|---|---|
| Icône adaptative, premier plan | `drawable/ic_launcher_foreground.xml` | vecteur 108 × 108 dp |
| Icône adaptative, fond | `values/ic_launcher_background.xml` (couleur `ic_launcher_background`) | couleur |
| Icône pour Android 6 et 7 | `mipmap-mdpi` à `mipmap-xxxhdpi/ic_launcher.webp` | 48, 72, 96, 144, 192 px |
| Icône à thème (Android 13+) | `mipmap-anydpi-v26/ic_launcher.xml`, ligne `monochrome` (réutilise le premier plan) | vecteur |
| Petite icône des notifications et d'Android Auto | `drawable/ic_gramophone_monochrome.xml` et `drawable/ic_gramophone_mono16.xml` (nom interne conservé) | vecteur blanc sur transparent |
| Écran de démarrage (Android 11 et moins) | `drawable/ic_splash_foreground.xml`, couleur `splash_foreground_color` dans `values/colors.xml` | vecteur |
| Logo de la boîte « À propos » | réutilise `drawable/ic_launcher_foreground.xml`, teinté par le thème | — |
| Icône de la version de mise au point | `app/src/debug/res/values/ic_launcher_background.xml` (fond ambre) | couleur |
| Raccourci « tout en aléatoire » | `mipmap-*/ic_shortcut_shuffle.*` et `drawable/ic_shortcut_shuffle_foreground.xml` | générique, pas à changer |

## Fiche de publication (F-Droid, GitHub, Play Store)

Dans `fastlane/metadata/android/en-US/images/` (et `fr-FR/images/` pour la version française ; une langue sans image reprend celles de `en-US`). L'icône et la bannière existent déjà, dessinées d'après l'icône provisoire ; il manque les captures d'écran :

| Image | Fichier | Taille |
|---|---|---|
| Icône | `icon.png` | 512 × 512 px |
| Bannière | `featureGraphic.png` | 1024 × 500 px |
| Captures du téléphone | `phoneScreenshots/1.png`, `2.png`… | format du téléphone |

Une capture se prend depuis Android Studio : panneau **Running Devices** ou **Logcat** > icône d'appareil photo, ou bien sur le téléphone avec Marche + Volume bas.

## Après un changement

**Build > Clean Project** puis relancer : sinon le lanceur du téléphone garde parfois l'ancienne icône en cache. Si elle persiste, désinstaller l'application et la réinstaller.
