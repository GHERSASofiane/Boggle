package Threads;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import Graphique.Jeux;

public class Gestion_Commandes extends Thread{

	private Jeux jeux = null;
	private Socket socket = null;
	private DataInputStream canalLecture = null;
	private PrintStream canalEcriture = null;
	private String commande = "";
	private String[] cmd;
	
	public Gestion_Commandes(Jeux j) {
		this.jeux = j;
		this.socket = this.jeux.getSocket();
        try {
        	this.canalEcriture = new PrintStream(this.socket.getOutputStream());
        	this.canalLecture = new DataInputStream(this.socket.getInputStream());
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
	}
	
	@Override
	public void run() {
		while(true) {
			
			try {
				this.commande = this.canalLecture.readLine();
			} catch (IOException e) {  e.printStackTrace(); }
			
			if(this.commande!=null || !this.commande.equals("")) { // CONNECTE/user/

				System.out.println("la commande est : "+ this.commande);
				
            this.cmd = this.commande.split("/");
            
			switch (this.cmd[0]) {
			
			case "BIENVENUE":// TODO
				System.out.println("BIENVENUE"); 
				new Jeux_Lab_Messages(this.jeux.I_out_message, "Connection success").start();
				break;
				
			case "CONNECTE":
				new Jeux_Lab_Messages(this.jeux.I_out_message, "New connection of "+this.cmd[1]).start();
				break;

			case "DECONNEXION": 
				new Jeux_Lab_Messages(this.jeux.I_out_message, this.cmd[1]+"is offline ").start(); 
				break;

			case "SESSION": 
				new Jeux_Lab_Messages(this.jeux.I_out_message, "New Session").start();
				break;

			case "VAINQUEUR": // TODO
				new Jeux_Lab_Messages(this.jeux.I_out_message, "End of Session").start();
				String bilan = this.cmd[1];System.out.println(bilan);
				break;

			case "TOUR":
				System.out.println("TOUR"); this.jeux.NewGrille(this.cmd[1]);
				break;
				
			case "MVALIDE": 
				this.jeux.ValidationMot();
				break;
				
			case "MINVALIDE": 
				this.jeux.InValidationMot();
				break;
				
			case "RFIN": 
				new Jeux_Lab_Messages(this.jeux.I_out_message, "Expiry of the time limit for reflection.").start();
				break;
				
			case "ERREUR":// TODO
				System.out.println("user existe");this.jeux.setVisible(false); 
				break;
				
				

			default:
				break;
			}
			}
		}
	}
}
