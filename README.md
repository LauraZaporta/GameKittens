# GameKittens

Aquest repositori conté un projecte *full-stack* compost per una API i una aplicació *front-end*. L'objectiu del projecte és oferir una solució robusta, escalable i fàcil d'utilitzar per fer **GameKittens** més sostenible.

## Descripció 

Aquest projecte es basa en una aplicació per promoure la sostenibilitat dins l’empresa **GameKittens**.

L’aplicació consisteix en una mascota virtual amb la qual, mitjançant un sistema de punts sostenibles, competiràs amb els teus companys d’oficina per quedar entre els primers del rànquing. Per aconseguir aquests punts, hauràs de realitzar tasques sostenibles, les quals, a més de donar-te punts sostenibles, et proporcionaran diners (dins del joc) per alimentar i personalitzar la teva mascota.

Si no alimentes la mascota, es morirà —el que significaria que no estàs fent tasques sostenibles— i quedaràs desqualificat. L’aspecte divertit és que els **3 primers del rànquing** obtindran un premi escollit per l’empresa.

Amb aquesta aplicació fomentem la sostenibilitat dins de l'empresa, des de reciclar fins a ajudar els teus companys

## Estat Actual

En aquesta versió **beta** de l’aplicació, la funció de mascota virtual **no està implementada**. Hem decidit no implementar-la perquè volíem centrar-nos en la funció principal de l’app: el sistema de **tasques i punts**.

En futures actualitzacions, i amb el *feedback* del client, podrem implementar-la com a funcionalitat extra.

## Com s'ha desenvolupat

Aquest projecte consta de:

- **API**: Utilitzada per a la comunicació entre la nostra aplicació i la nostra base de dades.
- **App**: Aplicació Android on podràs registrar les teves tasques i utilitzar la resta de funcionalitats de l'app.
- **SQL Database**: Bàsicament és on emmagatzemem totes les dades dels empleats i de l'aplicació.

En l'esquema següent es poden observar les diferents branques realitzades. Hem separat la part de desplegament (**main**) de la part de desenvolupament (**dev**). Hem dividit la branca de desenvolupament en: desenvolupament de l'API (**api/dev**) i desenvolupament de l'app (**app/ui.development**):

```
main
│
└───dev
    ├───api/dev
    │   ├───api/refactoring
    │   ├───api/givepoints
    │   ├───api/rebuild
    │   ├───api/Images
    │   ├───api/Votes
    │   ├───api/restructure
    │   ├───api/auth
    │   └───api/controllers
    │
    └───app/ui.development
        ├───app/begin.ui.development
        ├───app/ui.development.no.pet
        ├───app/appdbcontext
        └───app/base.setup
```

Hem organitzat el projecte d’aquesta manera per evitar conflictes i poder treballar conjuntament en el projecte sense haver-nos de trepitjar el codi constantment, tal com hem après a classe.


## Documentació detallada

Per trobar més informació detallada de cada part del projecte, clica un dels enllaços següents:

- 📗 [Documentació general i Teoria](https://docs.google.com/document/d/15A9gMML3P4agQ3Egl2lDxck9P6-OxEwq7QRU53F7OLg/edit?usp=sharing)
- 📘 [API Documentació](https://docs.google.com/document/d/1R6T_yHg1oAO3CO7gpXE-bXgvs5sO4FyABR5O20chf6E/edit?usp=sharing)
- 📙 [App Documentació](https://docs.google.com/document/d/1EmhLFCvW5_7QQ0qsxGIfRCs87TEb5H0ALW8dMwDnnkM/edit?usp=sharing)
- 🖥️ [Presentació PDF](https://drive.google.com/file/d/1er3SznT2uXSKqpeGhC1BktbKm7_1HrFn/view?usp=sharing)

## Carpeta de recursos

Tots els recursos relacionats amb el projecte (imatges, documents, maquetes, etc.) estan disponibles a la carpeta de Drive següent:

📁 [Carpeta de recursos de Drive](https://drive.google.com/drive/folders/1E2QU0xWhww9-XvFphXE5SmJgjcR5ppJ3?usp=sharing)



## Aplicació per a Android
Aquest enllaç conté una APK per a Android de l'aplicació.

📱 [Descarregar aplicació](https://drive.google.com/file/d/1LYCGy05cKpsZvVMpvwsRicVUktjToSJw/view?usp=drive_link)

### Usuaris per utilitzar la app

**Laura:**
- Email: laura@gmail.com
- Contrasenya: Laura2025!

**Eudald:**
- Email: eudald@gmail.com
- Contrasenya: Eudald2025!

---

### Creat per

- **Miquel Manzano** - [@miquel-manzano](https://github.com/miquel-manzano)
- **Laura Zaporta** - [@LauraZaporta](https://github.com/LauraZaporta)
- **Unai Arévalo** - [@UnaiAreval](https://github.com/UnaiAreval)

**Empresa:** [GameKittens](https://sites.google.com/itb.cat/kitten-company/)

---