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

### Uuden käyttäjän luominen

### Ongelman luominen

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/sekvenssi.png)

### Ennätyksen luominen
