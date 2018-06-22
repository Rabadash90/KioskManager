package kiosk;

import ch.alexandrahauri.kiosk.business.Kiosk;
import ch.alexandrahauri.kiosk.business.KioskManager;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;

/**
 * @author: Alexandra
 * @since: 22.06.2018
 **/
public class KioskTest {
    @Test
    public void shouldCreateKiosk() {
        KioskManager kioskManager = KioskManager.getInstance();
        kioskManager.createKiosk("MeinKiosk", "Brugg", Double.valueOf(500), "Hans");
        HashSet<Kiosk> kiosks = kioskManager.getKiosks();
        Boolean foundCreatedKiosk = false;
        for (Kiosk kiosk : kiosks) {
            if (kiosk.getName().equals("MeinKiosk")) {
                foundCreatedKiosk = true;
            }
        }
        assertEquals("Expect MeinKiosk to be found in kiosk list", Boolean.TRUE, foundCreatedKiosk);
    }

    @Test
    public void shouldBuyArticle() {
        // NOT YET IMPLEMENTED
    }

    @Test
    public void shouldFailToBuyArticle() {
        // NOT YET IMPLEMENTED
        // client too young
    }
}
