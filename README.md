# Proiect - Sistem Energetic

Rețeaua de curent electric a unei țări este un sistem complex, format din producători, distribuitori, consumatori 
(casnici sau industriali) și instituții ale statului care reglementează și supraveghează buna funcționare a sistemului.

In acest proiect se vor modela **3 entitati** ale unui astfel de sistem: *producatori*, *consumatori*, *distribuitori*, 
si vom simula lunar interactiunile acestor entitati.

## Consumatori

* Au un *buget initial* si *venit lunar*

* Doresc o sursa de energie, aleg de la **distribuitori** *contractul cu cea mai mica rata lunara* si platesc rata 
stabilita la inceput pentru o perioada stabilita la alegerea contractului

* In cazul in care ramane fara bani, poate *amana o luna plata facturii*, cu conditia ca in urmatoarea luna sa plateasca 
factura curenta si pe luna trecutam, dar si o penalizare.

* Daca nici luna viitoare nu poate plati spre acelasi distribuitor, **va declara faliment** si va fi exclus din joc

* Un **consumator** este considerat clientul unui **distribuitor** pana isi alege alt contract sau pana la finalul lunii 
in care da faliment

## Distribuitori

* Vor fi vizitati lunar de **consumatorii** dornici sa afle ce oferte pot obtine

* Au un *buget de inceput*, la care lunar se va adauga *profitul obtinut*

* **Au de achitat** lunar costuri pentru *infrastructura* si *productie*

* *Pretul contractului* va fi recaltulat la inceputul fiecarei luni

* Au nevoie de o cantitate de *energie lunara*

* In prima runda isi aleg unul sau mai multi **producatori** care sa le ofere cantitatea de energie necesara

* Au o anumita strategie de alegere a producatorilor: 

    * **Green Strategy** - alege producatorii prioritizandu-i pe cei cu *energie regenerabila* intai, apoi dupa *pret*, apo dupa *cantitate*
    
    * **Price Strategy** - alege producatorii prioritizandu-i dupa *pret*, apoi dupa *cantitate*
    
    * **Quantity Strategy** - alege producatorii prioritizandu-i dupa *cantitate*

* Daca a aparut o *schimbare a cantitatii de energie* oferita de un **producator**, atunci la inceputul lunii urmatoare 
**distribuitorii** care iau energie de la acel **producator** vor *aplica din nou strategia de alegere producatori*

## Producatori

* Produc o *energie de un anumit tip* si o vand mai multor **distribuitori**

* Au proprietati precum: *pret pe KWh*, *cantitatea de energie oferita lunar*, *numar maxim de distribuitori care care pot oferi energie*

* Lunar *pot aparea schimbari* legate de *energia oferita* spre **distribuitori**

## Simulare

Simularea interactiunii dintre entitati se bazeaza pe *runde*, al caror numar maxim e stabilit in input si se termina
cand s-au rulat *numberOfTurns + 1* si se afiseaza **starea curenta a simularii**.

Datele de intrare despre **entitati** si **schimbarile lunare** sunt primite in format *JSON*. Rezultatele simularii
de la finalul rundelor vor fi scrise tot in format *JSON*.

Etapele simularii sunt urmatoarele:

**Runda Initiala**

1. Se incarca datele de intrare despre entitati

2. Distribuitorii

    * isi aleg producatorii
    
    * isi calculeaza costul de productie
    
    * isi calculeaza costul initial al contractelor

3. Consumatorii - isi aleg distribuitorii si ii platesc

4. Distribuitorii obtin bani de la consumatori si isi platesc costurile

**Flow in fiecare luna**

* Inceputul lunii:

    1. se obtin noile valori din test
    
    2. se actualizeaza valorile pentru distribuitori
    
    3. se actualizeaza valorile pentru consumatori
    
    4. consumatorii isi aleg distribuitorii si ii platesc
    
    5. distribuitorii isi platesc costurile
    
* In timpul lunii

    1. se actualizeaza valorile din test pentru luna respectiva pentru producatori
    
* La sfarsitul lunii

    1. Toti distribuitorii care nu au dat faliment isi actualizeaza costul de productie daca e cazul
    
    2. Se retin cati distribuitori a avut fiecare producator.
    
**La finalul rundelor** se scriu in format *JSON* in fisierul de output informatii despre starea entitatilor

## Design Patterns

In implementarea simularii am fost folosite urmatoarele **design patterns**:

* **Singleton** si **Factory** pentru a construi *strategiile* de alegere ale distribuitorilor si pentru a stabili o singura 
entitate in mod global care se ocupa de acest lucru

* **Strategy** pentru a separa distribuitorii de alegerea *algoritmului de cautare a producatorilor* si pentru a oferi
posibilitatea de extindere a acestei functionalitati

* **Observer** pentrua facilita sistemul de notificare a distribuitorilor de catre producator in cazul in care acesta isi
schimba pretul energiei

## Extra

Entrypoint-ul proiectului este clasa Main.

Biblioteci necesare pentru implementare (parsarea datelor JSON):

* Jackson Core

* Jackson Databind

* Jackson Annotations

Am inclus exemplele de [input](https://github.com/btudorache/energy-system/blob/master/extra/inputExample.json) 
si de [output](https://github.com/btudorache/energy-system/blob/master/extra/outputExample.json) din enuntul problemei.

Pentru cerintele complete ale proiectului consultati enuntul pentru [prima etapa](https://github.com/btudorache/energy-system/blob/master/extra/enunt_etapa1.pdf) 
si a [doua etapa](https://github.com/btudorache/energy-system/blob/master/extra/enunt_etapa2.pdf).

Pentru detalii complete de implementare consultati fisierele README pentru [prima etapa](https://github.com/btudorache/energy-system/blob/master/extra/README_etapa1) 
si a [doua etapa](https://github.com/btudorache/energy-system/blob/master/extra/README_etapa2).
