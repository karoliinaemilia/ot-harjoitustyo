
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;

    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void kassapaatteenRahamaaraOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassapaatteenLounasMaaraOikein() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiEdulliselleLounaalleJosMaksuOnRiittava() {
        assertEquals(10, kassa.syoEdullisesti(250));
        assertEquals(100240, kassa.kassassaRahaa());
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiEdulliselleLounaalleJosMaksuEiOleRiittava() {
        assertEquals(230, kassa.syoEdullisesti(230));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiMaukkaalleLounaalleJosMaksuOnRiittava() {
        assertEquals(50, kassa.syoMaukkaasti(450));
        assertEquals(100400, kassa.kassassaRahaa());
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kateisostoToimiiMaukkaalleLounaalleJosMaksuEiOleRiittava() {
        assertEquals(380, kassa.syoMaukkaasti(380));
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoToimiiEdulliselleLounaalleJosMaksuOnRiittava() {
        kassa.syoEdullisesti(kortti);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
        assertEquals(760, kortti.saldo());
    }
   
    @Test
    public void korttiostoToimiiEdulliselleLounaalleJosMaksuEiOleRiittava() {
        kortti.otaRahaa(800);
        assertEquals(false, kassa.syoEdullisesti(kortti));
        assertEquals(200, kortti.saldo());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void korttiostoToimiiMaukkaalleLounaalleJosMaksuOnRiittava() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void korttiostoToimiiMaukkaalleLounaalleJosMaksuEiOleRiittava() {
        kortti.otaRahaa(800);
        assertEquals(false, kassa.syoMaukkaasti(kortti));
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void korttiostoEiMuutaKassanSaldoa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void rahanLataaminenToimii() {
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(100500, kassa.kassassaRahaa());
        assertEquals(1500, kortti.saldo());
    }
    
    @Test
    public void rahanLataaminenEiToimiNegatiivisellaSummalla() {
        kassa.lataaRahaaKortille(kortti, -50);
        assertEquals(100000, kassa.kassassaRahaa());
        assertEquals(1000, kortti.saldo());
    }
}
