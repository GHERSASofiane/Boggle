package Threads;

public class Connexion_Lab_Erreur extends Thread{

	private javax.swing.JLabel I_erreur;
	private String message;
	
	public Connexion_Lab_Erreur(javax.swing.JLabel I, String m) {
		this.I_erreur = I;
		this.message = m;
	}
	
	@Override
	public void run() {
		
		 this.I_erreur.setText(this.message);
		 try { sleep(5000); } catch (InterruptedException e) { e.printStackTrace(); }
		 this.I_erreur.setText("");
		 
	}
	
}
