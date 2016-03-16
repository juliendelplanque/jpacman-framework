package nl.tudelft.jpacman.jannou;

import nl.tudelft.jpacman.jannou.profil.Profil;
import nl.tudelft.jpacman.jannou.score.ScorePlayer;
import nl.tudelft.jpacman.jannou.score.feat.Feat;
import nl.tudelft.jpacman.jannou.score.feat.HandleFeat;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.util.ArrayList;

/**
 * Classe charge de la gestion des fichiers et dossier
 * Created by Jannou on 6/03/16.
 */
public class FileHelper {

    /**
     * Verifie si un File existe
     * @param path le chemin du File qu on veut verifier
     * @return true si le File existe false sinon
     */
    public static boolean exist(String path){
        File f = new File(path);
        return f.exists();
    }

    /**
     * Cree un File
     * @param path le chemin du File
     * @param file true si on veut creer un fichier, false si on veut creer un dossier
     * @return true si le File a ete cree false sinon
     */
    public static boolean init(String path, boolean file){
        assert !exist(path);
        File f = new File(path);
        if (file ) {
            try {
                return f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                return f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * Supprime et recree un File
     * @param path le chemin du File
     * @param file true si File est un fichier false si File est un dossier
     * @return true si le File a ete cree false sinon
     */
    public static boolean reset(String path, boolean file){
        File f = new File(path);
        if(f.exists() && f.isFile()){
            f.delete();
            return init(path,file);
        }
        return false;
    }

    /**
     * Lis le fichier score.txt et retourne les scores enregistes
     * @param path le chemin du fichier
     * @return ArrayList<ScorePlayer> les scores enregistes
     */
    public static ArrayList<ScorePlayer> loadScores(String path) {
        ArrayList<ScorePlayer> retour = new ArrayList<>();
        try{
            File f = new File (path);
            FileReader fr = new FileReader (f);
            BufferedReader br = new BufferedReader (fr);
            try
            {
                String line = br.readLine();
                while (line != null)
                {
                    String[] data = line.split(" :: ");
                    if (data.length == 2) {
                        ScorePlayer tp = new ScorePlayer(Integer.parseInt(data[0]),data[1]);
                        retour.add(tp);
                    }
                    line = br.readLine();
                }
                br.close();
                fr.close();
            }
            catch (IOException exception)
            {
                System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
            }
        }catch (FileNotFoundException exception){
            System.out.println ("Le fichier n'a pas été trouvé");
        }
        return retour;
    }

    /**
     * Ecris des scores dans un fichier
     * @param toWrite l ensemble des scores a ecrire
     * @param path le chemin du fichier
     */
    public static void writeScores(ArrayList<ScorePlayer> toWrite, String path){
        File f = new File(path);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            for (ScorePlayer p : toWrite) {
                pw.println("" + p.getScore() + " :: " + p.getPlayer());
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Ouvre un dossier et un retourne un ensemble contenant le nom de chaque fichier present dans le dossier
     * @param path le chemin du dossier
     * @return ArrayList<String> l ensemble contenant le nom de chaque fichier present dans le dossier
     */
    public static ArrayList<String> loadProfils(String path) {
        ArrayList<String> retour = new ArrayList<>();
        try{
            File f = new File(path);
            String [] files;
            int i;
            if(f.isDirectory()){
                files=f.list();
                for(i=0;i<files.length;i++){
                    retour.add(files[i]);
                }
            }
            else{
                return null;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return retour;
    }

    /**
     * Lis un fichier et charge le profil enregistre dans le fichier
     * @param path le chemin du fichier ou se trouve le profil
     * @return le profil enregistre dans le fichier
     */
    public static Profil loadProfil(String path) {
        Profil retour = new Profil("");
        try {
            File f = new File (path);
            DocumentBuilderFactory dbFactory= DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(f);
            doc.getDocumentElement().normalize();
            retour = new Profil(doc.getElementsByTagName("name").item(0).getTextContent());
            retour.setBestScore(Integer.parseInt(doc.getElementsByTagName("bestScore").item(0).getTextContent()));
            NodeList nList = doc.getElementsByTagName("feat");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Feat tempp = HandleFeat.createFeat(eElement.getElementsByTagName("name").item(0).getTextContent());
                    assert tempp != null;
                    tempp.setDesc(eElement.getElementsByTagName("description").item(0).getTextContent());
                    tempp.setValue(Integer.parseInt(eElement.getElementsByTagName("value").item(0).getTextContent()));
                    tempp.setRealised(Boolean.valueOf(eElement.getElementsByTagName("realised").item(0).getTextContent()));
                    tempp.setState(Integer.parseInt(eElement.getElementsByTagName("state").item(0).getTextContent()));
                    retour.addFeat(tempp);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return retour;
    }

    /**
     * Ecris un profil dans un fichier
     * @param toWrite le profil a enregistrer
     * @param path le chemin du fichier ou sera enregistre le profil
     * @return true si le profil a ete enregistre false sinon
     */
    public static boolean writeProfil(Profil toWrite, String path){
        File f = new File(path);
        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(f)));
            pw.println(toWrite.toMaps());
            pw.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime un fichier
     * @param path le chemin du fichier a supprimer
     * @return true si le fichier a ete supprime false sinon
     */
    public static boolean deleteProfil(String path) {
        File f = new File(path);
        return f.exists() && f.isFile() && f.delete();
    }


}
