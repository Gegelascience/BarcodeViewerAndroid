package com.remiverchere.barcodeviewerandroid;

import org.junit.Test;

import static org.junit.Assert.*;

import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;
import com.remiverchere.barcodeviewerandroid.checkEan.EanValidator;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class EanValidatorUnitTest {

    @Test
    public void checkEan_isCorrect() {
        EanValidator validator8 = new EanValidator(EanEnum.EAN8);
        assertTrue(validator8.isCorrectEan("12345670"));
        EanValidator validator13 = new EanValidator(EanEnum.EAN13);
        assertTrue(validator13.isCorrectEan("3666154117284"));
    }

    @Test
    public void checkCalculateCheckDigit() {
        EanValidator validator13 = new EanValidator(EanEnum.EAN13);
        try {
            assertEquals(validator13.calculateCheckDigit("366615411728"),"4");
        }catch (Exception ex){
            fail("expected that validator13.calculateCheckDigit(\"366615411728\") return 4");
        }

    }
}