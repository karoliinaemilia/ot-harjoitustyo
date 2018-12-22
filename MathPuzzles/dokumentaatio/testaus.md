# Testausdokumentti

Ohjelma on testattu automatisoiduin yksikkö- ja integraatiotestein JUnitilla ja manuaalisesti
järjestelmätason testeillä.

## Yksikkö- ja integraatiotestaus

### Sovelluslogiikka

Soovelluslogiikkaa, eli pakkauksen 
[mathpuzzles.logics] luokkia testaavat integraatiotestit [ProblemLogicTest](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/test/java/mathpuzzles/logics/ProblemLogicTest.java),
[RecordLogicTest](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/test/java/mathpuzzles/logics/RecordLogicTest.java) ja
[UserLogicTest](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/test/java/mathpuzzles/logics/UserLogicTest.java),
jotka mallintavat käyttöliittymän käyttämiä luokkia [ProblemLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/ProblemLogic.java), [RecordLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/RecordLogic.java) ja [UserLogic](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/src/main/java/mathpuzzles/logics/UserLogic.java)
ja niiden toiminnallisuuksia.

### Dao-luokat

Dao-luokkia on testattu käyttämällä erillistä tietokantaa.

### Testauskattavuus

Sovelluksen testauksen rivikattavuus on 96% ja haarautumakattavuus 80%, käyttöliittymäluokkaa lukuunottamatta.

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/testauskattavuus2.png)

## Järjestelmätestaus

Järjestelmätestaus sovellukselle on suoritettu manuaalisesti.

## Asennus
Sovellusta on testattu käyttöohjeen kuvaamalla tavalla.

Sovellusta on testattu sekä tilanteissa, jossa käyttäjät ja ennätykset tallettava tietokanta ja sen taulut ovat olleet olemassa ja joissa niitä ei ole ollut jolloin ohjelma on luonut ne itse.

## Toiminnallisuudet
Määrittelydokumentin ja käyttöohjeen listaamat toiminnallisuudet on testattu. Toiminnallisuuksien testaamisessa on yritetty käyttää myös virheellisiä arvoja.

