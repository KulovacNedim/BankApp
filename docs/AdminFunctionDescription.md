========================================================================================================
1. PRISTUP IZ PERSPEKTIVE ADMINISTRATORA
========================================================================================================

-   Nakon pokretanja RunBankApp klase u application klasi, program trazi
    unos ID/password kombinacije. Tri pogresna unosa zaustavnjaju rad
    aplikacije.

-   Nakon unosa ispravne ID/password kombinacije, program otvara BANK
    ADMIN MENU sa opcijama:
    1)  Login to specific bank
    2)  Bank settings
    3)  Admin setting
    4)  User settings
    5)  ATM operators settings
    6)  Log out of system

    7)  Bank settings i 3) Admin setting su zamisljene kao postavke
        globalnog sistema, kojima upravlja globalni admin.

    8)  User settings i 5) ATM operators settings su zamisljene kao
        postavke za specificnu banku, te prije pristupa njima, program
        trazi da se administrator loguje u neku od banaka u sistemu, te
        se cjelokupan dalji rad odnosi na izabranu banku.

  ---------------------------
  1) Login to specific bank
  ---------------------------

-   Nakon izbora ove opcije, program izlistava spisak banaka u sistemu i
    trazi unos ID broja banke za logovanje u sistem.
-   Logovanje je dozvoljeno samo u aktivnu banku.
-   Ukoliko je administrator vec logovan u sistem neke banke, dobija
    obavjestenje o tome i upit da li zeli da se loguje u sistem druge
    banke.

  ----------------------------------------
  2) Bank settings
  ----------------------------------------
  Ova opcija otvara BANK SETTINGS MENU sa
  sljedecim opcijama:

  1) Banks and officies overview --\>
  Izlistava pregled postojecih banaka i
  podruznica banaka u sistemu.

  2) Add new bank and officies --\> Trazi
  unos glavne banke, tj. sjedista banke, a
  zatim nudi opciju unossa podruznica
  banke. Po defaultu ih postavlja kao
  aktivne.

  3) Add new bank office to existing bank
  --\> izlistava samo aktivne banke i nudi
  mogucnost dodavanja podruznica nekoj od
  aktivnih banaka.

  4) Change bank and officies activity
  status --\> izlistava spsiak banaka i
  podruznica, te nudi opcije: 1) Change
  bank activity, 2) Change office
  activity, 3) Cancel

  5) Back to MAIN ADMIN MENU --\> izlaz u
  MAIN ADMIN MENU
  ----------------------------------------

3) Admin setting
----------------

Ova opcija otvara ADMIN SETTINGS MENU sa sljedecim opcijama:

    1) Overview list of global administrators
    --> izlistava globalne administratore u sistemu.

    2) Add new administrator
    --> nudi mogusnost unosa novih administratora u sistem. Podefaultu ih postavlja kao aktivne.

    3) Delete administrator
    --> izlistava globalne administratore i trazi unos ID administratora da bi ga izbrisao iz sisema.

    4) Change administrators activity status
    --> izlistava globalne administratore i trazi unos ID administratora da bi mu promjenio status aktivnosti.

    5) Back to MAIN ADMIN MENU
    --> izlaz u MAIN ADMIN MENU

  ----------------------------------------
  4) User settings
  ----------------------------------------
  Ova opciaj se odnosi na sistem konkretne
  - specificne banke, tako da ukoliko
  administrator nije logovan u sistem neke
  banke, program trazi da se prvo loguje
  (opcija 1), te poslije logovanja moze
  pristupiti ovoj opciji.

  Opcija otvara USERS SETTINGS MENU sa
  sljedecim opcijama:

  1) Overview list of users for this bank
  --\> izlistava spisak korisnika -
  uposlenika za banku u koju je
  administrator logovan.

  2) Add new user --\> nudi dodavanje
  korisnika - uposlenika u sistem banke u
  koju je administrator logovan.

  3) Delete user --\> izlistava spisak
  korisnika - uposlenika za banku u koju
  je administrator logovan, te trazi unos
  ID uposlenika kako bi ga obrisao iz
  sistema.

  4) Change users activity status --\>
  izlistava spisak korisnika - uposlenika
  za banku u koju je administrator
  logovan, te trazi unos ID uposlenika
  kako bi mu promjenio status aktivnosti.

  5) Back to MAIN ADMIN MENU --\> izlaz u
  MAIN ADMIN MENU
  ----------------------------------------

5) ATM operators settings
-------------------------

Ova opciaj se odnosi na sistem konkretne - specificne banke, tako da
ukoliko administrator nije logovan u sistem neke banke, program trazi da
se prvo loguje (opcija 1), te poslije logovanja moze pristupiti ovoj
opciji.

Opcija otvara ATM OPERATORS SETTINGS sa sljedecim opcijama:

    1) Overview list of ATM operators for this bank
    --> izlistava ATM operatore za banku u koju je administrator logovan.

    2) Set or delete ATM operator
    --> izlistava korisnike - uposlenike banke u koju je administrator logovan, te nudi unos ID broja korisnika kako bi ga mu promjenio status ATM operatora.

    3) Back to MAIN ADMIN MENU
    --> izlaz u MAIN ADMIN MENU

  ----------------------
  6) Log out of system
  ----------------------

Administrator se odloguje iz sistema, te se otvara pocetni zaslon,
odnosno mugucnost ponovnog logovanja za administratora ili korisnika
sistema.
