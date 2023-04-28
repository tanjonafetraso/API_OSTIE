/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import api.EmpreinteDAO;
import com.machinezoo.sourceafis.FingerprintCompatibility;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import java.util.Arrays;
import java.util.List;
import models.Empreinte;

/**
 *
 * @author eqima
 */
public class FingerPrintTemplateMaching {

    public int Matching(byte[] fingerProbe, String id, String qualitiy) {
        try {
            int max = 0;
            EmpreinteDAO empDao = new EmpreinteDAO();
            FingerprintTemplate fingerTProbe = FingerprintCompatibility.importTemplate(fingerProbe);
            List<Empreinte> listeFingerTemplate = empDao.getImageEmpreinteBDD(id);
            if (listeFingerTemplate.size() > 0) {
                // si quality <20
                if (Integer.valueOf(qualitiy) <= 20) {
                    for (Empreinte template : listeFingerTemplate) {
                        if (template.getQuality() <= 20) {
                            max = 50;
                        }
                    }
                } else {
                    // si quality >20
                    for (Empreinte template : listeFingerTemplate) {
                        System.out.println(Arrays.toString(template.getDataEmpreinte()));
                        FingerprintTemplate fingerCandidate = FingerprintCompatibility.importTemplate(template.getDataEmpreinte());
                        int score1 = (int) new FingerprintMatcher(fingerCandidate).match(fingerTProbe);
                        int res = 0;
                        if (score1 > 10 && score1 < 100) {
                            res = 100 - ((int) ((score1 * 100) / Math.max(fingerCandidate.toByteArray().length, fingerTProbe.toByteArray().length)));
                        } else if (score1 > 100) {
                            res = 100;
                        } else {
                            res = (int) ((score1 * 100) / Math.max(fingerCandidate.toByteArray().length, fingerTProbe.toByteArray().length));
                        }
                        if (res > max) {
                            max = res;
                        }
                    }
                }
            } else {
                max = 0;
            }
            return max;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }

    public int MatchingGroup(List<FingerprintTemplate> fingerProbe, String id) {
        try {
            int max = 0;
            EmpreinteDAO empDao = new EmpreinteDAO();
            for (FingerprintTemplate fingerTProbe : fingerProbe) {
                List<Empreinte> listeFingerTemplate = empDao.getImageEmpreinteBDD(id);
                if (listeFingerTemplate.size() > 0) {
                    System.out.println("superieur à zero");
                    for (Empreinte template : listeFingerTemplate) {
                        System.out.println(Arrays.toString(template.getDataEmpreinte()));
                        FingerprintTemplate fingerCandidate = FingerprintCompatibility.importTemplate(template.getDataEmpreinte());
                        int score1 = (int) new FingerprintMatcher(fingerCandidate).match(fingerTProbe);
                        int res = 0;
                        if (score1 > 10 && score1 < 100) {
                            res = 100 - ((int) ((score1 * 100) / Math.max(fingerCandidate.toByteArray().length, fingerTProbe.toByteArray().length)));
                        } else if (score1 > 100) {
                            res = 100;
                        } else {
                            res = (int) ((score1 * 100) / Math.max(fingerCandidate.toByteArray().length, fingerTProbe.toByteArray().length));
                        }
                        if (res > max) {
                            max = res;
                        }
                    }
                } else {
                    System.out.println("tsy naazo");
                }
            }

            return max;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }
}
