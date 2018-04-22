package followin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import scraper.Instagram;
import scraper.InstagramFactory;
import me.postaddict.instagram.scraper.MediaUtil;
import me.postaddict.instagram.scraper.interceptor.UserAgents;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import me.postaddict.instagram.scraper.model.PageInfo;
import me.postaddict.instagram.scraper.model.PageObject;
import rest.Access;
import rest.Login;
import rest.User;

@RestController
public class Requests_2 {
	
	Map<String, Access> users = LoginController.users;
	Map<String, Account> accounts = LoginController.accounts;
	Requests_1 req = new Requests_1();
 
	    @RequestMapping("/getaccount")//TODO
	    public Account getaccountbyid(@RequestParam(value="token", required=false) String token,
	            @RequestParam(value="username", required=false) long id) {
	    	try {
	    		Instagram instagram = users.get(token).getClient();
	    		Account account = instagram.getAccountById(id);
	    		return account;
	    	} catch (IOException e) {
	            System.out.println("Unable to login"); e.printStackTrace();
	        }
	    	return null;
	    }

	    @RequestMapping("/getnotfollowingmeback")
	    public List<Account> notfollowingmebackbyid(@RequestParam(value="token", required=false) String token,
	    		@RequestParam(value="username", required=false) long id)
	    {
	    	Account user = getaccountbyid(token, id);
	    	List<Account> listfollowing = new LinkedList<Account>();
	    	List<Account> notlistfollowing = new LinkedList<Account>();
	    	PageObject<Account> Pagefollowers = new PageObject<Account>();
	    	PageObject<Account> Pagefollowings = new PageObject<Account>();
	    	Map<Long, Account> mapfollowers = new HashMap<Long, Account>();
	    	int p1 = 1;
	    	String cursor1 = "";
	    	while(true)
	    	{
	    		Pagefollowers = req.getfollowers(token, user.getId(), p1, cursor1);
	    		cursor1 = Pagefollowers.getPageInfo().getEndCursor();
	    		p1++;
	    		System.out.println("PageNumber(ers): " + p1);
	    		for(int i = 0; i < Pagefollowers.getNodes().size(); i++)
		    	{
		    		mapfollowers.put(Pagefollowers.getNodes().get(i).getId(), Pagefollowers.getNodes().get(i));
		    	}
	    		if(!Pagefollowers.getPageInfo().isHasNextPage())
	    		{
	    			break;
	    		}
	    		System.out.println("mapsize(ers): " + mapfollowers.size());
	    	}
	    	int p2 = 1;
	    	String cursor2 = "";
	    	while(true)
	    	{
	    		Pagefollowings = req.getfollowings(token, user.getId(), p2, cursor2);
	    		
	    		cursor2 = Pagefollowings.getPageInfo().getEndCursor();
	    		p2++;
	    		System.out.println("PageNumber(ings): " + p2);
	    		listfollowing.addAll(Pagefollowings.getNodes());
	    		System.out.println("listsize(ings): " + listfollowing.size());
	    		for(int i = 0; i < listfollowing.size(); i++)
		    	{
		    		if(mapfollowers.containsKey(listfollowing.get(i).getId()))
		    		{
		    			listfollowing.remove(i);
		    			i--;
		    		}
		    	}
	    		notlistfollowing.addAll(listfollowing);
	    		listfollowing.clear();
	    		if(!Pagefollowings.getPageInfo().isHasNextPage())
	    		{
	    			break;
	    		}
	    	}
	    	return notlistfollowing;
	    }
	    
	    @RequestMapping("/getmedia")//TODO
	    public PageObject<Media> getmediabyid(@RequestParam(value="token", required=false) String token,
	    		@RequestParam(value="id", required=false) long id,
	            @RequestParam(value="sorted-by", required=false) String sortedby) {
	    	try {
	            Instagram client = users.get(token).getClient();
	            return client.getMedias(id, 1, );
	        } catch (IOException e) {
	            System.out.println("Unable to login");
	            e.printStackTrace();
	        }
	    	return null;
	    }
	    
	    
	    @RequestMapping("/getmedialikes")//TODO
	    public PageObject<Account> getmedialikes(@RequestParam(value="token", required=false) String token,
	    		@RequestParam(value="shortcode", required=false) String shortcode,
	            @RequestParam(value="page", required=false) int page) {
	    	try {
	            Instagram client = users.get(token).getClient();
	            return client.getMediaLikes(shortcode, page);
	        } catch (IOException e) {
	            System.out.println("Unable to login");
	            e.printStackTrace();
	        }
	    	return null;
	    }
}
