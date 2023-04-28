/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utiles;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;

/**
 *
 * @author eqima
 */
public class Fonctions_conversion_data {

    public Fonctions_conversion_data() {
    }

    public static void base64ToImage(String base64, String typeFile, File outputfile) {

        //  BufferedImage image = null;
        byte[] imageByte;
        try {
            imageByte = Base64.getDecoder().decode(base64);
            BufferedImage image;
            try (ByteArrayInputStream bis = new ByteArrayInputStream(imageByte)) {
                image = ImageIO.read(bis);
            }
            ImageIO.write(image, typeFile, outputfile);
        } catch (IOException e) {
        }
        // return image;
    }

    public static String ImageToBase64(String typeFile, File inputFile) {
        try {
            BufferedImage bImage = ImageIO.read(inputFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bImage, typeFile, baos);
            baos.flush();
            byte[] imageInByteArray = baos.toByteArray();
            baos.close();
            String b64 = DatatypeConverter.printBase64Binary(imageInByteArray);
            return b64;
        } catch (IOException ex) {
            Logger.getLogger(Fonctions_conversion_data.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*
    * Funzioni per convertire in byte, per passare i parametri alle funzionin;
     */
    public byte IntToByte(int i) {
        Integer v = i;
        byte b = v.byteValue();
        return b;
    }

    public byte[] StringToByte(String s) {
        byte[] byteArray = s.getBytes(java.nio.charset.StandardCharsets.UTF_8);
        return byteArray;
    }

    public String ByteToString(byte[] s) {
        String ret = new String(s, StandardCharsets.UTF_8);
        return ret;
    }

    public ArrayList<byte[]> DataStringToDataByte(ArrayList<String> dataString) {
        int i = 0;
        ArrayList<byte[]> DataByte = new ArrayList<>();
        for (String data : dataString) {
            if (i == 0) {
                String[] Noms = data.split(" ");
                for (int j = 0; j < Noms.length; j++) {
                    DataByte.add(StringToByte(Noms[j]));
                    if (j == 4) {
                        break;
                    }
                }
                i++;
            } else {
                DataByte.add(StringToByte(data));
            }
        }
        return DataByte;
    }

    public String byteArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String getDecimal(String hex) {
        String digits = "0123456789abcdef";
        hex = hex.toUpperCase();
        int val = 0;
        for (int i = 0; i < hex.length(); i++) {
            char c = hex.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        if (val < 0) {
            return String.valueOf(-val);
        } else {
            return String.valueOf(val);
        }
    }

    public ArrayList<String> DataByteToDataString(ArrayList<byte[]> dataByte) {
        int i = 0;
        String a = "";
        ArrayList<String> DataString = new ArrayList<>();
        for (byte[] data : dataByte) {
            String d = ByteToString(data);
            if (!d.equals(" ")) {
                if (i < 4) {
                    a += ByteToString(data);
                    i++;
                    if (i == 3) {
                        DataString.add(a);
                    }
                } else {
                    DataString.add(ByteToString(data));
                }
            }

        }
        return DataString;
    }

    public String StringToSHA1(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
