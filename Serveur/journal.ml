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


class journal = 
object(self)
initializer self#open_journal

val jrnl = open_out "journal.xml"

method open_journal = 
	let date = Unix.gmtime(Unix.time ()) in
		let j = "<journal>\n" 
		 ^ "<session>\n<date>" ^ date_string_of_tm date ^ "</date>\n" ^ 
		 "</session>\n"
		 ^ "</journal>" in
		self#write_to_journal j

method write_to_journal message =
		let pos = pos_out jrnl in
		if pos > 0 then
			begin
			print_endline ("pos : " ^ string_of_int pos);
			seek_out jrnl (pos - String.length "</session>\n</journal>");
			output_string jrnl message;
			flush jrnl
			end
			else (
		output_string jrnl message;
		flush jrnl
			)
		
		
		
method close_journal () = close_out jrnl

end;;