package Tricheur;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;

import Threads.Jeux_Lab_Messages;

public class cammandes extends Thread{

	private tricheur tr=null;
	private DataInputStream canalLecture=null;
	private PrintStream canalEcriture=null;
	private String commande = "";
	private String[] cmd;
	
	public cammandes(tricheur t, DataInputStream l, PrintStream e) {
		this.tr = t;
		this.canalLecture = l;
		this.canalEcriture = e;
	}
	
	@Override
	public void run() {
		while(!this.isInterrupted()) {

			try {
				this.commande = this.canalLecture.readLine();
			} catch (IOException e) { this.interrupt(); e.printStackTrace(); }
			
			if(this.commande!=null && !this.commande.equals("")) {  
 
				 this.cmd = this.commande.split("/");
		            
		            
					switch (this.cmd[0]) {
					

					case "BIENVENUE": 
						this.tr.receveLettres(this.cmd[1]);
						break;

					case "TOUR":
						this.tr.receveLettres(this.cmd[1]);
						break;

						
					case "MVALIDE": 
						System.out.println("Mot valide : "+ this.cmd[1]);
						break;
						
					case "MINVALIDE": 
						System.out.println("Mot invalide !");
						break;
					default:
						break;
						
					}
				
			}
		}
	}
}
