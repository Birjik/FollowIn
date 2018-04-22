package rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import scraper.Instagram;
import me.postaddict.instagram.scraper.model.Account;

@Getter 
@Setter
@AllArgsConstructor
public class Access {
	
	String username;
	String password;
	Instagram Client;
	String Token;

}
