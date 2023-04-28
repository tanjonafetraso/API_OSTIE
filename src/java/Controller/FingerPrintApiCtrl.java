/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import api.Aff_IdDAO;
import api.EmpreinteDAO;
import api.IdentificationDAO;
import api.InscriptionDAO;
import com.machinezoo.sourceafis.FingerprintCompatibility;
import com.machinezoo.sourceafis.FingerprintTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import static javax.management.Query.match;
import models.Empreinte;
import models.Identification;
import models.Inscription;
import models.ViewCSV;
import models.identificationMultiple;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author eqima
 */
@RestController
public class FingerPrintApiCtrl {

    private String key = "eyJhbGciOiJIUzM4NCJ9.eyJqdGkiOiJTZWFyY2gyMDIyIiwiaWF0IjoxNjQ3NTMyMzgwLCJzdWIiOiJPc3RpZU1hZGEiLCJpc3MiOiJFcWltYVNlcnZpY2UifQ.QaVwNnJbKbasw0RuXX6EH8J2TFM1GoXDN_TzRxLfiOuHvRX_1wf32KvnxePPbsFJ";
    int cpt = 0;
    ArrayList<identificationMultiple> ListeIdm;

    /**
     *
     * @param aff_id
     * @return
     */
    @RequestMapping(value = "/getdoigt", method = RequestMethod.POST)
    public String getDoigtEnregistr(@RequestParam("aff_id") String aff_id) {
        EmpreinteDAO empDao = new EmpreinteDAO();
        return empDao.getDoigtEnregistrer(aff_id);
    }

    /**
     *
     * @param FingerTemplate
     * @param heders
     * @param matricule
     * @param codeAgent
     * @return
     */
    @RequestMapping(value = "/idmultiples", method = RequestMethod.POST)
    public String IdentificationAPIsd(@RequestBody String FingerTemplate, @RequestHeader("x-api-key") String heders, @RequestParam("aff_id") String matricule, @RequestParam("code_agent") String codeAgent, @RequestParam("quality") String quality) {

        try {

            FingerPrintTemplateMaching match = new FingerPrintTemplateMaching();
            ListeIdm = new ArrayList<>();
            String[] ListeAffId = matricule.split(",");
            String color = "";
            if ((heders.length() > 0) && (heders.equals(key))) {
                if (FingerTemplate.length() > 0) {
                    byte[] FingerTemplateArray = Base64.getDecoder().decode(FingerTemplate);

                    for (String string : ListeAffId) {

                        int res = match.Matching(FingerTemplateArray, string, quality);
                        identificationMultiple idm = new identificationMultiple();
                        if (res < 10) {
                            idm.setCouleur("Red");
                            idm.setMatriculle(string);
                        } else if (res >= 10) {
                            idm.setCouleur("Green");
                            idm.setMatriculle(string);
                        }
                        ListeIdm.add(idm);
                    }
                    boolean isGreen = false;
                    for (int i = 0; i < ListeIdm.size(); i++) {
                        if (ListeIdm.get(i).getCouleur().equals("Green")) {
                            color += ListeIdm.get(i).getMatriculle() + "=" + ListeIdm.get(i).getCouleur();
                            isGreen = true;
                            break;
                        }
                    }
                    if (isGreen) {
                        Identification identifie = new Identification(codeAgent, matricule, color);
                        IdentificationDAO Idao = new IdentificationDAO();
                        Idao.create(identifie);
                        return "{\"resultat\":\"" + color + "\"}";
                    } else {
                        String res = "Empreinte inconnue";
                        return "{\"resultat\":\"" + res + "\"}";
                    }
                } else {
                    String res = "EMPREINTE VIDE";
                    return "{\"resultat\":\"" + res + "\"}";
                }
            } else {
                String res = "INVALID SIGNATURE";
                return "{\"resultat\":\"" + res + "\"}";
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            String res = "Reidentifier";
            return "{\"resultat\":\"" + res + "\"}";
        }
    }

    /**
     *
     * @param FingerTemplate
     * @param heders
     * @param matricule
     * @param codeAgent
     * @return
     */
    @RequestMapping(value = "/idgroupees", method = RequestMethod.POST)
    public String IdentificationAPIsdd(@RequestBody String FingerTemplate, @RequestHeader("x-api-key") String heders, @RequestParam("aff_id") String matricule, @RequestParam("code_agent") String codeAgent) {

        try {
            List<FingerprintTemplate> listeFingerTemplate = new ArrayList<>();
            FingerPrintTemplateMaching match = new FingerPrintTemplateMaching();
            ListeIdm = new ArrayList<>();
            String[] ListeAffId = matricule.split(",");
            String color = "";
            if ((heders.length() > 0) && (heders.equals(key))) {
                if (FingerTemplate.length() > 0) {

                    String[] AllFingerPrinte = FingerTemplate.split("allAmpreinte");
                    for (String string : AllFingerPrinte) {
                        System.out.println(string);
                        byte[] FingerTemplateArray = Base64.getDecoder().decode(string);
                        listeFingerTemplate.add(FingerprintCompatibility.importTemplate(FingerTemplateArray));
                    }

                    for (String string : ListeAffId) {
                        int res = match.MatchingGroup(listeFingerTemplate, string);
                        identificationMultiple idm = new identificationMultiple();
                        System.out.println("Score=" + res + "%");
                        if (res < 10) {
                            idm.setCouleur("Red");
                            idm.setMatriculle(string);
                        } else if (res >= 10) {
                            idm.setCouleur("Green");
                            idm.setMatriculle(string);
                        }
                        ListeIdm.add(idm);
                    }
                    for (int i = 0; i < ListeIdm.size(); i++) {
                        color += ListeIdm.get(i).getMatriculle() + "=" + ListeIdm.get(i).getCouleur();
                        if (i < ListeIdm.size() - 1) {
                            color += ",";
                        }
                    }
                    Identification identifie = new Identification(codeAgent, matricule, color);
                    IdentificationDAO Idao = new IdentificationDAO();
                    Idao.create(identifie);
                    return "{\"resultat\":\"" + color + "\"}";
                } else {
                    String res = "EMPREINTE VIDE";
                    return "{\"resultat\":\"" + res + "\"}";
                }
            } else {
                String res = "INVALID SIGNATURE";
                return "{\"resultat\":\"" + res + "\"}";
            }
        } catch (Exception e) {
            System.out.println("erreur" + e.getMessage());
            String res = "Reidentifier";
            return "{\"resultat\":\"" + res + "\"}";
        }

    }

    /**
     * FAIRE L'INSCRIPTION
     *
     * @param FingerTemplate
     * @param fileBody
     * @param heders
     * @param doigt
     * @param aff_id
     * @param quality
     * @param codeAgent
     * @return
     */
    @RequestMapping(value = "/inscription", method = RequestMethod.POST)
    public String writeEmpreinteAff_Id(@RequestBody String FingerTemplate, @RequestHeader("x-api-key") String heders, @RequestParam("doigt") String doigt, @RequestParam("aff_id") String aff_id, @RequestParam("code_agent") String codeAgent, @RequestParam("quality") String quality) {
        try {
            Aff_IdDAO aff = new Aff_IdDAO();
            EmpreinteDAO emp = new EmpreinteDAO();
            if ((heders.length() > 0) && (heders.equals(key))) {
                if (FingerTemplate.length() > 0) {
                    if (Integer.valueOf(quality) >= 40) {
                        byte[] FingerTemplateArray = Base64.getDecoder().decode(FingerTemplate);
                        int idExistDansBDD = aff.getAll(aff_id);

                        if (idExistDansBDD == 0) {
                            aff.create(aff_id);
                            idExistDansBDD = aff.getAll(aff_id);

                        }
                        Empreinte p = new Empreinte(doigt, idExistDansBDD, FingerTemplateArray, Integer.valueOf(quality));
                        p = emp.create(p);
                        if (p.getId() > 0) {
                            Inscription inscrit = new Inscription(codeAgent, aff_id, p.getId());
                            InscriptionDAO Idao = new InscriptionDAO();
                            Idao.create(inscrit);
                            String res = "Succes";
                            return "{\"resultat\":\"" + res + "\"}";
                        } else {
                            String res = "Echec";
                            return "{\"resultat\":\"" + res + "\"}";
                        }
                    } else {
                        String res = "Qualité doit être superieur à 40";
                        return "{\"resultat\":\"" + res + "\"}";
                    }

                } else {
                    String res = "EMPREINTE VIDE";
                    return "{\"resultat\":\"" + res + "\"}";
                }
            } else {
                String res = "INVALID SIGNATURE";
                return "{\"resultat\":\"" + res + "\"}";
            }
        } catch (Exception e) {
            String res = "Echec connexion base";
            return "{\"resultat\":\"" + res + "\"}";
        }
    }

    /**
     *
     * @param FingerTemplate
     * @param fileBody
     * @param heders
     * @param doigt
     * @param aff_id
     * @param quality
     * @param codeAgent
     * @return
     */
    @RequestMapping(value = "/updateEmpreinte", method = RequestMethod.POST)
    public String updateEmpreinte(@RequestBody String FingerTemplate, @RequestHeader("x-api-key") String heders, @RequestParam("doigt") String doigt, @RequestParam("aff_id") String aff_id, @RequestParam("code_agent") String codeAgent, @RequestParam("quality") String quality) {
        EmpreinteDAO emp = new EmpreinteDAO();
        Aff_IdDAO affDAO = new Aff_IdDAO();
        if ((heders.length() > 0) && (heders.equals(key))) {
            if (FingerTemplate.length() > 0) {
                byte[] FingerTemplateArray = Base64.getDecoder().decode(FingerTemplate);
                int AffId = affDAO.getAll(aff_id);
                String res = emp.updateAddImage(String.valueOf(AffId), doigt, FingerTemplateArray, Integer.valueOf(quality));
                return "{\"resultat\":\"" + res + "\"}";
            } else {
                String res = "EMPREINTE VIDE";
                return "{\"resultat\":\"" + res + "\"}";
            }
        } else {
            String res = "INVALID SIGNATURE";
            return "{\"resultat\":\"" + res + "\"}";
        }
    }

    @RequestMapping(value = "/getAllInscription", method = RequestMethod.POST)
    public String getAllInscription(@RequestHeader("x-api-key") String heders, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        InscriptionDAO insDao = new InscriptionDAO();
        String res = "";
        ArrayList<ViewCSV> ListeInscription = new ArrayList<>();
        ListeInscription = insDao.getAllInscription(date1, date2);
        if ((heders.length() > 0) && (heders.equals(key)) && (ListeInscription != null)) {
            for (int i = 0; i < ListeInscription.size(); i++) {

                res += ListeInscription.get(i).getAff_id() + "dataEmp" + ListeInscription.get(i).getDoigt() + "dataEmp" + Arrays.toString(ListeInscription.get(i).getEmpreinte()) + "dataEmp" + ListeInscription.get(i).getQualite() + "dataEmp" + ListeInscription.get(i).getCode_agent() + "dataEmp" + ListeInscription.get(i).getDate();
                if (i < (ListeInscription.size() - 1)) {
                    res += "dataLigne";
                }
            }
            return "{\"resultat\":\"" + res + "\"}";
        } else {
            res = "INVALID SIGNATURE";

            return "{\"resultat\":\"" + res + "\"}";
        }
    }

    @RequestMapping(value = "/getAllIdentification", method = RequestMethod.POST)
    public String getAllIdentification(@RequestHeader("x-api-key") String heders, @RequestParam("date1") String date1, @RequestParam("date2") String date2) {
        IdentificationDAO insDao = new IdentificationDAO();
        String res = "";
        ArrayList<ViewCSV> ListeInscription = new ArrayList<>();
        ListeInscription = insDao.getAllIdentification(date1, date2);
        if ((heders.length() > 0) && (heders.equals(key)) && (ListeInscription != null)) {
            for (int i = 0; i < ListeInscription.size(); i++) {

                res += ListeInscription.get(i).getAff_id() + "dataEmp" + ListeInscription.get(i).getCode_agent() + "dataEmp" + ListeInscription.get(i).getDate() + "dataEmp" + ListeInscription.get(i).getResultat();
                if (i < (ListeInscription.size() - 1)) {
                    res += "dataLigne";
                }
            }
            return "{\"resultat\":\"" + res + "\"}";
        } else {
            res = "INVALID SIGNATURE";

            return "{\"resultat\":\"" + res + "\"}";
        }
    }
}
