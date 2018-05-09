package Tricheur;

public class tricheur {

    public static void main(String args[]) {

        System.out.println(is_exist_word("bonjour"));System.out.println(is_exist_word("BONJOUR"));System.out.println(is_exist_word("ss"));
        System.out.println(is_exist_word(""));System.out.println(is_exist_word(""));System.out.println(is_exist_word(""));

    }

public static boolean is_exist_word(String word){
    
	boolean rps = false;
		File dataFile = new File("/Dictionnaire/"+word.charAt(0)+"_dico.txt");
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