package com.remiverchere.barcodeviewerandroid.checkEan;

import androidx.annotation.NonNull;

public class EanValidator {

    private EanEnum typeToCheck;

    public EanValidator( @NonNull EanEnum eanType) {
        this.typeToCheck = eanType;
    }

    public boolean isCorrectEan(@NonNull String possibleEan){
        int lenPossibleEan = possibleEan.length();

        if (this.typeToCheck == EanEnum.EAN8 && lenPossibleEan != 8){
            return false;
        } else if (this.typeToCheck == EanEnum.EAN13 && lenPossibleEan != 13) {
            return false;
        }
        try {
            double eanNumber =Double.parseDouble(possibleEan);
            if (eanNumber <= 0) {
                return false;
            }

            String possibleCheckDigit = possibleEan.substring(lenPossibleEan-1);
            String eanWithoutCheckDigit = possibleEan.substring(0, lenPossibleEan-1);

            String correctCheckDigit = this.calculateCheckDigit(eanWithoutCheckDigit);

            return correctCheckDigit.equals(possibleCheckDigit);
        } catch (Exception ex) {
            return false;
        }


    }

    private String calculateCheckDigit(@NonNull String eanDigitCheckLess) throws Exception {
        /*
        Calcul du check digit de l'ean
        */

        int lenstrCalcul = eanDigitCheckLess.length();
        int factor = 3;
        double somme = 0;

        try {
            Double.parseDouble(eanDigitCheckLess);

            for (int i = lenstrCalcul - 1; i>-1  ; i--) {
                somme += Integer.parseInt(eanDigitCheckLess.substring(i,i+1)) * factor;
                factor = 4 - factor;
            }

            return (((10 - (somme % 10))%10) + "").split("\\.")[0];

        } catch (Exception ex) {
            throw new Exception("Invalid Ean");
        }




    }
}
