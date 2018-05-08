open Global_functions;;

let string_of_month = function
  0 -> "janvier"
| 1 -> "février"
| 2 -> "mars"
| 3 -> "avril"
| 4 -> "mai"
| 5 -> "juin"
| 6 -> "juillet"
| 7 -> "août"
| 8 -> "septembre"
| 9 -> "octobre"
| 10 -> "novembre"
| 11 -> "décembre"
| _ -> assert false (* on ne doit pas avoir une autre valeur *)
;;

let date_string_of_tm tm =
  let d = ((string_of_int tm.Unix.tm_mday) ^ " " ^ (string_of_month tm.Unix.tm_mon) ^ 
    " " ^ (string_of_int (1900 + tm.Unix.tm_year))) in
		d;;

let rec write_to_file oc = function
	| [] -> ()
	| e::tl -> output_string oc e; flush oc; write_to_file oc tl
	;;
 
let write_list_to_file l = 
	let jrnl =  open_out "journal.xml" in
		write_to_file jrnl l;
		close_out jrnl;;


let open_journal () = 	
	let j = ref (read_file "journal.xml") in
		let  date = Unix.gmtime(Unix.time ()) in
		begin
		match (List.exists (fun x -> x = "<journal>") !j) with
		| true -> 
					j := (List.tl (List.rev !j))@["<session><date>" ^ date_string_of_tm date]@["</date>\n"]@["</session>\n"]@["</journal>"]
		| false -> 
					j := !j@["<journal>"]@["\n"]@["<session><date>" ^ date_string_of_tm date]@["</date>\n"]@["</session>\n"]@["</journal>"]
		end;
		write_list_to_file !j;;
