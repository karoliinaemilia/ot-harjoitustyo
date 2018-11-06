package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void konstruktoriAsettaaSaldonOikein() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void kortilleVoiLadataRahaa() {
        kortti.lataaRahaa(500);
        assertEquals("saldo: 15.0", kortti.toString());
    }
    
    @Test
    public void kortiltaVoiOttaaRahaaJosSielläOnTarpeeksi() {
        assertEquals(true, kortti.otaRahaa(500));
        assertEquals("saldo: 5.0", kortti.toString());
    }
    
    @Test
    public void kortiltaEiVoiOttaaRahaaJosSielläEiOleTarpeeksi() {
        assertEquals(false, kortti.otaRahaa(1500));
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
    @Test
    public void saldoMetodiToimiiOikein() {
        assertEquals(1000, kortti.saldo());
    }
}
