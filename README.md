# Math Puzzles

Käyttäjä voi luoda sovellukseen käyttäjätunnuksen ja kirjauduttuaan sisään harjoitella perus aritmetiikkaa.

## Releaset

[Loppupalautus](https://github.com/karoliinaemilia/ot-harjoitustyo/releases/tag/loppupalautus)

[Viikko5](https://github.com/karoliinaemilia/ot-harjoitustyo/releases/tag/viikko5)

[Viikko6](https://github.com/karoliinaemilia/ot-harjoitustyo/releases/tag/viikko6)

## Dokumentaatio

[Käyttöohje](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/testaus.md)

[Työaikakirjanpito](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/tuntikirjanpito.md)

### Testaaminen

Testit saa suoritettua komennolla

```
mvn test
```

Testikattavuusraportti taas luodaan komennolla

```
mvn jacoco:report
```

Sen voi katsoa avaamalla selaimella tiedoston _target/site/jacoco/index.html_

### Jar generointi

```
mvn package
```

jar-tiedosto _MathPuzzles-1.0-SNAPSHOT.jar_ löytyy sen jälkeen hakemistosta _target_

### JavaDoc

JavaDocin saa generoitua komennolla

```
mvn javadoc:javadoc
```
Sen voi katsoa avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Määrittelyt löytyy tiedostosta [checkstyle.xml](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/checkstyle.xml) 

Mahdolliset virheilmoitukset löytyvät avaamalla selaimella tiedosto _target/site/checkstyle.html_
