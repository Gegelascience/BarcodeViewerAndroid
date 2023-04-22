package com.remiverchere.barcodeviewerandroid.barcodeUtils;

import androidx.annotation.NonNull;

import com.remiverchere.barcodeviewerandroid.checkEan.EanEnum;

import java.util.Arrays;
import java.util.List;

public class BarcodeFormatter {

    private EanEnum eanType;

    public BarcodeFormatter( @NonNull EanEnum eanType) {
        this.eanType = eanType;
    }

    private final static List<String> setA = Arrays.asList(
            "0001101", "0011001", "0010011","0111101","0100011","0110001","0101111","0111011","0110111","0001011");

    private final static List<String> setB = Arrays.asList(
            "0100111", "0110011", "0011011","0100001","0011101","0111001","0000101","0010001","0001001","0010111");

    private final static List<String> setC = Arrays.asList(
            "1110010", "1100110", "1101100","1000010","1011100","1001110","1010000","1000100","1001000","1110100");

    public String getBarcodeValue(String ean) {

        if( this.eanType == EanEnum.EAN13){

            String firstPart = ean.substring(1,7);
            String seconPart = ean.substring(7);

            String prefix = ean.substring(0,1);

            StringBuilder barcodeValue = new StringBuilder("101");

            for (int i = 0; i < firstPart.length(); i++) {
                List<String> setFound = this.getSetToApply(i,prefix);

                barcodeValue.append(setFound.get(Integer.parseInt(firstPart.substring(i, i + 1))));
            }


            barcodeValue.append("01010");

            for (int i = 0; i < seconPart.length(); i++) {
                barcodeValue.append(setC.get(Integer.parseInt(seconPart.substring(i, i + 1))));
            }


            barcodeValue.append("101");

            return barcodeValue.toString();

        } else if (this.eanType == EanEnum.EAN8) {

            String firstPart = ean.substring(0,4);
            String seconPart = ean.substring(4);

            StringBuilder barcodeValue = new StringBuilder("101");

            for (int i = 0; i < seconPart.length(); i++) {
                barcodeValue.append(setA.get(Integer.parseInt(firstPart.substring(i, i + 1))));
            }

            barcodeValue.append("01010");

            for (int i = 0; i < seconPart.length(); i++) {
                barcodeValue.append(setC.get(Integer.parseInt(seconPart.substring(i, i + 1))));
            }


            barcodeValue.append("101");

            return barcodeValue.toString();
        } else {
            return null;
        }


    }

    private List<String> getSetToApply(int index, String prefix) {
        List<String> setFound;

        if (index == 0) {
            setFound =  setA;
        } else {
            switch (prefix){
                case "0":
                    setFound =  setA;
                    break;
                case "1":
                    if (index == 1 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "2":
                    if (index == 1 || index == 4) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "3":
                    if (index == 1 || index == 5) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "4":
                    if (index == 2 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "5":
                    if (index == 4 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "6":
                    if (index == 4 || index == 5) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "7":
                    if (index == 2 || index == 4) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "8":
                    if (index == 2 || index == 5) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                case "9":
                    if (index == 5 || index == 3) {
                        setFound =  setA;
                    } else {
                        setFound =  setB;
                    }
                    break;
                default:
                    setFound =  null;
                    break;
            }
        }

        return setFound;
    }
}
