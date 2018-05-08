open Connexion_manager;;
open Global_functions;;
open Journal;;

let users = ref [];;
let mutex = Mutex.create ();;
let cond = Condition.create ();;
let matrice = Array.make_matrix 4 4 "";;
let num_tour = ref 1;;
let mutex = Mutex.create ();;


(*lancer les des *)
let des () = 
  let list_des = read_file "des.dat" and index = ref 1
	and tab_string = ref "" in
  for i = 0 to (Array.length matrice) - 1 do
    for j = 0 to (Array.length matrice.(i)) - 1 do
        let rand = Random.int 6 and
            case = List.nth list_des !index 
            in 
              matrice.(i).(j) <- (String.make 1 case.[rand]);
              index := !index + 1   
      done
  done;;


class tour (usrs : infos list ref) nb_tour = 
	object(self)
	initializer 
		self#setTirage ()
		
	
	(* method debut_tour = *) 
	
	val mutable tirage = matrice
	val mutable words_found = [""]

	method getClients = usrs
	
	method getDictionnaire = read_file "dictionnaire.dat"
  (*tirage : matrice 4*4 *)
  method getTirage = matrice
	method setTirage () = 
												des ();
												tirage <- matrice
												
	
	method bilan () =
		ignore (
			List.map (fun x -> 
				 let mots = ref "" and scores = ref ((string_of_int !num_tour) ^ "*") in
					ignore(List.map (fun y -> mots := !mots ^ y.user ^ ":" ^  !(y.motsproposes) ^ ";";
					                  scores := !scores ^ y.user ^  "*" ^ (string_of_int !(y.score) ^ "*")) !(self#getClients));
						
					let message = "BILANMOTS/" ^ !mots ^ "/" ^ !scores ^ "/\n" in							
						output_string x.outchan message;
            flush x.outchan					
				) !(self#getClients)
				) 	
	
	method fin_tour () =  Thread.create (fun x -> self#expiration self#getClients)() 
	method getNumTour () = !num_tour
	
	(* experation de tour *)
	method expiration clients = 
		Thread.delay 15.0;
		print_endline ("fin de temps repartie pour le tour " ^ string_of_int !num_tour);
		let message =  "RFIN/\n" in
		let t = ref ["\n<tour>\n<num_tour>" ^ string_of_int !num_tour ^ "</num_tour>\n"] in	
          ignore (List.map (
                      fun x -> 
                                output_string x.outchan message;
                								flush x.outchan;
																t := !t@["<client>\n"^ 
																									"<name>\n" ^ x.user ^ "</name>\n" ^
																									"<score>\n" ^ string_of_int !(x.score) ^ "</score>\n" ^
																									"</client>\n"];		
                    ) !clients);
										t := !t@["</tour>\n"];
										
										
										
										let j = ref  (List.rev (List.tl (List.tl (List.rev (read_file "journal.xml")))))
										in
										
										j := !j@(!t)@["</session>\n"]@["</journal>\n"];
										
										write_list_to_file !j;
										
										self#bilan ();
										
										num_tour := !num_tour + 1; 
										ignore (Thread.create (fun x -> self#start_tour !num_tour)());
		Thread.exit ()	
	
	method start_tour num = 
		if !num_tour < nb_tour then 
			begin
				
				print_endline ("debut de tour " ^ string_of_int num);

				if (!num_tour > 1) then
					begin
    				self#setTirage ();
    				let message = "TOUR/" ^ (array_to_string self#getTirage) ^ "/\n" in
    								ignore (List.map (
    						                      fun x -> 
    						                                output_string x.outchan message;
    						                								flush x.outchan;
    						                    ) !users)
					end;							
				ignore(self#fin_tour ());
				
			end
			else
				(
					self#end_session ()
				)
	(*fin d'une session *)
   method end_session () = 
            let message =  "VAINQUEUR/" ^ (scores users) ^ "\n" in
						ignore(List.map (fun x ->  output_string x.outchan message;
    						                								flush x.outchan
    						     ) !users)
            
						
	method getWords = words_found
	
	method add_word word = 
		Mutex.lock mutex;
		words_found <- words_found@[word];
		Mutex.unlock mutex	

end;;