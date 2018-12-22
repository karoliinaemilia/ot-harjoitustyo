# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on seuraava

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/pakkauskaavioupdate.png)

Pakkaus _gui_ sisältää käyttöliittymän, _logics_ sisältää sovelluslogiikan, _domain_ logiikkojen käyttäät luokatt ja _dao_
tietojen talletukseen liittyvät luokat.

## Käyttöliittymä

Näkymiä on kuusi erilaista ja ne on jokainen toteutettu scene-olioina. Näkymät ovat
- kirjautuminen
- uuden käyttäjän luominen
- aloitusruutu
- ongelmien ratkaisu
- haaste
- ennätykset

Käyttöliittymän rakennuksesta vastaa luokka [mathpuzzles.gui.MathPuzzlesUI](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/gui/MathPuzzlesUi.java). Joka käyttää apunaan sovelluslogiikan toteuttavia luokkia [ProblemLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/ProblemLogic.java), [RecordLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/RecordLogic.java) ja [UserLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/UserLogic.java).

## Sovelluslogiikka 

Sovelluksen logiikasta vastaavat siis luokat [UserLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/UserLogic.java), [RecordLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/RecordLogic.java) ja [ProblemLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/ProblemLogic.java). UserLogic huolehtii käyttäjän luomiseen ja kirjautumiseen liittyvistä toiminnoista metoideilla kuten
- boolean createUser(String name, String username, String password)
- boolean login(String username, String password)
- void logout()
- String checkIfValidInputs

Matemaattisten ongelmien logiikasta taas vastaa ProblemLogic jossa on esim metodit
- String makeProblem(int minValue, int maxValue, Operation operation)
- boolean checkAnswer(String answer)

Ennätyksien asettaminen ja hakeminen on luokan RecordLogic vastuulla
- List<String> getRecords(String time)
- void setRecord(String time)
  
UserLogic saa tiedot käyttäjistä pakkauksessa _dao_ sijaitsevan rajapinnan _Dao_ toteuttavan luokan _UserDao_ kautta. Samoin RecordLogic saa tiedot ennätyksistä _Dao_ toteuttavan luokan _RecordDao_ kautta.

Sovelluslogiikasta vastaavat luokat käyttävät apunaan luokkia [User], [Record] ja [Problem] jotka kuvaavat ongelmia, käyttäjiä ja ennätyksiä.

Ylläoleva pakkaus/luokkakaavio kuvaa luokkien suhteita.

## Tietojen pysyiväistalletus

Luokat RecordDao ja UserDao pakkauksessa mathpuzzles.dao vastaavat tietojen tallettamisesta tietokantaan.

Luokat noudattavat Data Access Object -suunnittelumallia.

### Tietokanta

Tiedot talletetaan tietokantaa mathpuzzles.db jossa on kakis tietokanta taulua User ja Record joihin käyttäjien ja ennätysten tiedot tallennetaan.

Taulu User on muotoa

```
User (
  id integer PRIMARY KEY, 
  name varchar(255), 
  username varchar(20), 
  password varchar(200)
);
```
eli ensin uniikki id, sitten nimi, käyttäjätunnus ja salasana.

Taulu Record on muotoa

```
Record (
  id integer PRIMARY KEY, 
  time varchar(20), 
  solved integer, 
  username varchar(20)
);
```
ensin taas id sitten aikaraja jota ennätyksessä käytettiin, montako ratkaistiin ja ratkaisijan käyttäjätunnus.

## Päätoiminnallisuudet

### Kirjautuminen

Kun käyttäjä on syöttänyt käyttäjätunnuksen ja salasanan ja painaa _login_ nappia tapahtuu seuraava: 

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/kirjautuminenseq.png)

Eli napin painalluksesta vastaava tapahtumankäsittelijä kutsuu UserLogic-luokan metodia login ja antaa parametreiksi käyttäjätunnuksen ja salasanan. UserLogic selvittää onko käyttäjän antamat tiedot oikein eli onko käyttäjä olemassa
UserDao-luokan avulla. Jos UserDao saa haettua käyttäjän tietokannasta palauttaa se sen UserLogicille joka viestii käyttöliittymälle että kirjautuminen on onnistunut ja käyttöliittymä renderöi aloitusruudun.

### Uuden käyttäjän luominen

Kun käyttäjä on syöttänyt tarvittavat tiedot ja painaa nappia _create_

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/uusikayttajaseq.png)

UserLogicin metodia createUser kutsutaan create-napin tapahtumankäsittelijässä ja sille annetaan parametreiksi nimen käyttäjätunnuksen ja salasanan. UserLogic selvittää, että onko käyttäjätunnus uniikki UserDaon avulla ja jos on niin tallentaa sen tietokantaan UserDaon avulla. Käyttöliittymä renderöi onnistuneen tallentamisen jälkeen kirjautumisruudun.

### Ongelman luominen

Käyttäjä syöttää tarvittavat parametrit ja painaa nappia _get_

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/ongelmaseq.png)

Tapahtumankäsittelijä kutsuu ProblemLogicin metodia makeProblem ja antaa parametreiksi suurimman luvun, pienimmän luvun ja operaation tyypin. ProblemLogic kutsuu luokan ProblemGenerator metodia generateProblem joka luokan Operaatio avulla luo uuden ongelman jonka ProblemGenerator palauttaa ProblemLogicille. ProblemLogic muuttaa ongelman merkkijono muotoon ja käyttöliittymä näyttää sen renderöimässään ongelmaruudussa.

### Ennätyksen luominen

Kun käyttäjä pelaa haastemoodia hänen aloittaessaan tapahtuu seuraava

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/ennatysseq.png)

Eli käyttäjän saadessa ongelma oikein käyttöliittymästä kutsutaan RecordLogicin metodia incrementAmount joka nostaa muuttujaa joka pitää kirjaa siitä montako ongelmaa käyttäjä on saanut oikein yhdellä. Kun aika on loppunut käyttöliittymästä kutsutaan RecordLogicin metodia setRecord ja sille annetaan parametriksi aika joka oli ongelmien ratkaisun rajana. RecordLogic tarkistaa RecordDaon avulla ettei käyttäjällä ole tismalleen samaa ennätystä jo ennestään ja jos ei ole niin uusi ennätys tallennetaan tietokantaan.
