# Käyttöohje

Lataa tiedosto [mathpuzzles.jar](https://github.com/karoliinaemilia/ot-harjoitustyo/releases/download/loppupalautus/mathpuzzles.jar)

## Käynnistys

Ohjelma käynnistetään komennolla

```
java -jar mathpuzzles.jar
```

## Kirjautuminen sovellukseen

Sovellus avautuu kirjautumisruutuun

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/kirjautumiskuva.png)

Kirjautua voi antamalla oikea käyttäjätunnus ja salasana ja painamalla nappia _login_.

## Uusi käyttäjä

Kirjautumisnäkymästä voi siirtyä uuden käyttäjän luomiseen painamalla nappia _create new user_.

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/uusikayttajakuva.png)

Uuden käyttäjän voi luoda syöttämällä tarvittavat tiedot kenttiin ja painamalla _create_.

## Aloitusruutu

Käyttäjän kirjauduttua hänet ohjataan päänäkymään jossa käyttäjä voi valita millaisia ongelmia haluaa ratkoa.

Käyttäjä valitsee ongelmien tyypin (plus-, miinus-, jako- tai kerto-laskut) ja maksimi ja minimi arvot esiintyville numeroille.
Tämän jälkeen käyttäjä voi painaa nappia _get_.

Painamalla nappia _logout_ käyttäjä kirjautuu ulos ja sovellus siirtyy takaisin kirjautumisnäkymään.

Käyttäjä voi myös valita haastemoodi jossa hän voi ratkaista ongelmia aikarajalla.

Painamalla nappia records käyttäjä voi tarkastlla haastemoodin ennätyksiä

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/aloitusruutu.png)

## Laskujen ratkaiseminen

Kun käyttäjä on valinnut ongelmien tyypin hänet ohjataan tähän ruutuun ratkomaan ongelmia.

Käyttäjä kirjoittaa ratkaisunsa ruutuun ja painaa _submit answer_. Sovellus kertoo sitten oliko vastaus oikein.

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/ongelma.png)

Painamalla nappia _next_ käyttäjä saa esiin uuden ongelman ja painamalla nappia _back to menu_ pääsee takakaisin aloitusruutuun.

## Haaste

Jos käyttäjä valitsee haastemoodin hänet ohjataan tähän ruutuun jossa hän voi aloittaa ratkaisemisen valitulla aikarajalla

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/haaste1.png)

Painamalla nappia aloita ongelma tulee esiin ja aika lähtee käyntiin

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/haaste2.png)

## Ennätykset

Jos käyttäjä painaa nappia ennätykset hänet ohjataan tähän ruutuun jossa hän voi tarkastella ennätyksiä

![](https://github.com/karoliinaemilia/ot-harjoitustyo/blob/master/MathPuzzles/dokumentaatio/kuvat/ennatykset.png)
