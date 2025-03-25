# Grandma's Ice Cream 🍦

Egy fagylaltos rendelő alkalmazás Androidra, Jetpack Compose + Kotlin segítségével.

## 📱 Funkciók

- Fagylaltok listázása távoli JSON API alapján
- Képek, státuszok megjelenítése
- Extrák választása (kötelező és opcionális)
- Kosár funkció (hozzáadás, badge számláló, árak összesítése)
- Rendelés elküldése HTTP POST kéréssel
- Perzisztens kosár-állapot
- Letisztult, piros-sárga stílus a minta alapján

## 🛠️ Technológiák

- Android (Kotlin, Jetpack Compose)
- Retrofit + Gson
- Coil (képbetöltés)
- MVVM architektúra

## 🔧 Futtatás

1. Klónozd a repót:
```bash
git clone https://github.com/Hufre242/fagyi-api
```

2. Nyisd meg Android Studioban

3. Futtasd bármelyik eszközön vagy emulátoron

## 📂 Erőforrások

Az ikonokat és képeket a `res/drawable` mappában találod:

- `logo.xml` – felső logó
- `cart_outline.xml` – kosár ikon
- `placeholder.jpg` – kép helyettesítő, ha nincs URL

## 🔗 API források

- Fagylaltok: `icecreams.json`
- Extrák: `extras.json`
- Rendelés: `http://httpbin.org/post`



