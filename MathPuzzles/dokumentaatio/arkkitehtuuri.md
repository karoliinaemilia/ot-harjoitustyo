# Arkkitehtuurikuvaus

## Rakenne

Ohjelman pakkausrakenne on seuraava

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/pakkauskaavioupdate.png)

Pakkaus _gui_ sisältää käyttöliittymän, _logics_ sisältää sovelluslogiikan, _domain_ logiikkojen käyttäät luokatt ja _dao_
tietojen talletukseen liittyvät luokat.

## Käyttöliittymä

Näkymiä on viisi neljä erilaista ja ne on jokainen toteutettu scene-olioina.

## Sovelluslogiikka 

Sovelluksen logiikasta vastaavat luokat [UserLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/UserLogic.java) ja [ProblemLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/ProblemLogic.java). UserLogic huolehtii käyttäjän luomiseen ja
kirjautumiseen liittyvistä toiminnoista metoideilla kuten
- boolean createUser(String name, String username, String password)
- boolean login(String username, String password)
- void logout()
- String checkIfValidInputs

Matemaattisten ongelmien logiikasta taas vastaa ProblemLogic jossa on esim metodit
- String makeProblem(int minValue, int maxValue, Operation operation)
- boolean checkAnswer(String answer)

UserLogic saa tiedot käyttäjistä pakkauksessa _dao_ sijaitsevan rajapinnan _Dao_ toteuttavan luokan _UserDao_ kautta.

## Päätoiminnallisuudet

### Ongelman luominen

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/sekvenssi.png)

