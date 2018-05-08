open Server;;
open Connexion_manager;;
open Journal;;
open Tour;;

class server_maj port n =
   object(self)
    inherit server port n
			
	 val mutable tour_actuel = new tour users n  
	 
   method treat s sa =
	 ignore( (new connexion_maj s sa true tour_actuel )#start())
   end;;