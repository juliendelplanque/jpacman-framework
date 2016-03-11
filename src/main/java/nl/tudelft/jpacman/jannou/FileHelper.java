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
 * Handle files
 * Created by Jannou on 6/03/16.
 */
public class FileHelper {

    /**
     * creat file at path if it doesn't exist an return false, return true otherwise
     * @return true if file exist false otherwise
     */
    public static boolean exist(String path){
        File f = new File(path);
        return f.exists();
    }

    /**
     * create directory or file at path if it doesn't exist an return false
     *
     * @param path the path
     * @param file create a file if true a directory otherwise
     * @return true if file is created false otherwise
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
     * reset the file at given path
     * @param path
     * @return
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
     * load all high scores
     * @return ArrayList<ScorePlayer> of player's and player's score
     */
    public static ArrayList<ScorePlayer> loadScores(String path) {
        ArrayList<ScorePlayer> retour = new ArrayList<ScorePlayer>();
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
     * add new high score
     * @param toWrite something to be written
     * @param path file's path
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
     * load all file's name contained into path
     * @param path path of repertory
     * @return all file's name contained into path
     */
    public static ArrayList<String> loadProfils(String path) {
        ArrayList<String> retour = new ArrayList<String>();
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
     * laod a profil from a repertory
     * @param path of a profil
     * @return a profil
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
     * add new high score
     * @param toWrite something to be written
     * @param path file's path
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
    public static boolean deleteProfil(String path){
        File f = new File(path);
        if(f.exists() && f.isFile()){
             return f.delete();
        }
        return false;
    }


}
