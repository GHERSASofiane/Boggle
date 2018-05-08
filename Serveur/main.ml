open Server_manager;;
open Journal;;
(*
   val main : unit -> unit
   *)
  
   let main () =
    
		if Sys.argv.(1) = "-port" 
		&& Sys.argv.(3) = "-tours" 
		(* &&  Sys.argv.(5) = "-grilles" *)
		  then
			let port = int_of_string(Sys.argv.(2)) 
			and nbtour = int_of_string(Sys.argv.(4))
			in
				open_journal ();
				(new server_maj port nbtour)#start()
		else
			print_endline "usage : server -port port -tours nbtour";;
   
    main();;