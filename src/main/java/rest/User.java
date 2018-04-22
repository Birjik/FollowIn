package rest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.postaddict.instagram.scraper.Instagram;

@Getter 
@Setter
@AllArgsConstructor
public class User {
	String username;
	String profile_picture;
	String full_name;
	long id;
	int followers_amount;
	int followings_amount;
}
