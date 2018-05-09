package Tricheur;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class tricheur {


    public void lanceTricheur() {
    	String port1 = "2018";
    	int port = 2018;
    	String ip = "127.0.0.1";
    	Socket sok = null ;
    	DataInputStream canalLecture=null;
    	PrintStream canalEcriture=null;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Bienvenue sur le client tricheur ;) ");
    	System.out.println("Donner l'adresse IP de serveur ");
//    	ip = sc.nextLine();
    	System.out.println("Donner le PORT de serveur ");
//    	port1 = sc.nextLine();
    	port = Integer.parseInt(port1);
    	
    	try {
			sok = new Socket(ip, port);
		} catch (UnknownHostException e) { e.printStackTrace();
		} catch (IOException e) { e.printStackTrace(); }
    	
    	if(sok!=null) {
    		try {
				canalLecture = new DataInputStream(sok.getInputStream());
				canalEcriture = new PrintStream(sok.getOutputStream());
			} catch (IOException e) { e.printStackTrace(); }
    		
    		
    	}
    	
    	cammandes com = new cammandes(this, canalLecture, canalEcriture);
    	com.start();
    	canalEcriture.print("CONNEXION/Tricheur/\n");canalEcriture.flush();
    	
        System.out.println(is_exist_word("Bonjour"));System.out.println(is_exist_word("BONJOUR"));System.out.println(is_exist_word("Ss"));

        
    }

public void receveLettres(String plateux) {
	
	System.out.println("debut de recherche avec : "+plateux);
}
    
public static boolean is_exist_word(String word){
    word = word.toUpperCase();
	boolean rps = false;
		File dataFile = new File("src/Tricheur/Dictionnaire/"+word.charAt(0)+"_dico.txt");
		   	try {
		   	InputStream ips = new FileInputStream(dataFile);
		   	InputStreamReader ipsr = new InputStreamReader(ips);
		   	BufferedReader br = new BufferedReader(ipsr);
		   	String ligne;
		   	while ((ligne = br.readLine()) != null) {
		   		 
		   		if (word.equals(ligne)) {
					rps = true;
				}
		   	}
		   	
		   	br.close();
		   	} catch (Exception e) { System.out.println(e.toString()); }
return rps;
}

}