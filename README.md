# Mani

## Definirea sistemului

### Obiecte

#### Account

`Account` modeleaza un cont bancar cu doua stari posibile: Activ(_Active_) si Inchis(_Closed_). 
Un cont Inchis nu poate efectua alte operatii si nu poate fi redeschis, pe cand un cont Activ poate 
efectua tranzactii.


#### Balance

`Balance` modeleaza bilantul unui cont bancar, aplicand in ordine tranzactiile din istoric, pornind de la
bilantul initial. Tranzactiile sunt ajustate la valuta contului.


#### Card

`Card` descrie un card bancar de doua tipuri: Virtual(_Virtual_) si Fizic(_Physical_). 
Cardul Virtual poate fi folosit numai la tranzactii efectuate online, nu are un termen de expirare
si nu are nevoie de pin. Cardul Fizic are nevoie de un Pin pentru a putea fi folosit la terminalele
POS, si expira o data la 4 ani, fiind revocat si inlocuit cu un alt card fizic emis.


#### Transaction

`Transaction` reprezinta o tranzactie bancara efectuata intre doua conturi, cu validari care asigura
integritatea datelor. 


#### User

`User` reprezinta un utilizator al sistemului; un utilizator poate avea mai multe conturi deschise in 
numele sau si poate fi o persoana fizica sau juridica(TODO).


#### Money

`Money` reprezinta o suma monetara non-negativa cu moneda fixata, monedele suportate fiind Euro si Ron.
Pentru conversie intre monede s-au stabilit rate fixe de schimb valutar.


#### Currency

`Currency` defineste in cadrul sistemului tipurile de monede suportate, creand o ierarhie
controlata. Pentru fiecare moneda suportata, este definita o rata de conversie fixata.


#### Timestamp

`Timestamp` modeleaza in sistem marcaje temporale relevante, in formatul Unix epoch. Obiectul
permite conversia in `Instant`, utila in contextul unei aplicatii Java si compararea facila intre
doua instante.


### Operatii

- `CreateAccount`: Permite deschiderea unui cont nou pentru un utilizator. Contul deschis va fi o instanta `Account.Active`
- `LookupAccounts`: Permite accesarea mai multor date relevante despre un cont; contul cautat poate sa nu existe, aspect
    intarit prin folosirea obiectului `Optional`
- `AccountBalance`: Permite accesarea bilantului unui cont intr-un interval de timp dorit
- `CreateTransaction`: Permite generarea tranzactiilor noi intre doua conturi active. Tranzactia va fi efectuata numai in
    cazul in care contul creditor are un bilant de credit mai mare decat suma tranzactionata
- `LookupTransactions`: Permite accesarea mai multor date relevante despre o tranzactie; tranzactia cautata poate sa nu existe, aspect
  intarit prin folosirea obiectului `Optional`
- `GetTransactions`: Permite accesarea tranzactiilor debitate sau creditate de un cont intr-o perioada stabilita
- `AddCard`: Permite crearea unui nou card asociat unui cont. Fiecare cont poate avea un singur card fizic si un singur card
   virtual
- `GetCards`: permite accesarea cardurilor unui cont
- `ChangeCardPin`: Permite schimbarea codului pin al unui card fizic. Codurile pin anterioare nu pot fi refolosite
- `RevokeCard`: permite revocarea unui card existent, nemaiputand fi utilizat
- `CloseAccount`: permite inchiderea unui cont, acesta devenind inactiv
