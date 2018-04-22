package followin;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import scraper.Instagram;
import scraper.InstagramFactory;
import me.postaddict.instagram.scraper.MediaUtil;
import me.postaddict.instagram.scraper.interceptor.UserAgents;
import me.postaddict.instagram.scraper.model.Account;
import me.postaddict.instagram.scraper.model.Media;
import me.postaddict.instagram.scraper.model.PageObject;
import rest.Access;
import rest.Login;
import rest.User;


@RestController
public class LoginController
{
    private static Logger logger = Logger.getLogger(LoginController.class.getName());
    static Map<String, Access> users = new HashMap<String, Access>(); // Временное хранилище для токенов
    static Map<String, Account> accounts = new HashMap<String, Account>(); // Временное хранилище для data

    
    @RequestMapping("/login")
    public Login login(@RequestParam(value="username", required=false) String username,
            @RequestParam(value="password", required=false) String password,
            @RequestParam(value="user-agent", required=false) String userAgent) { // UNUSED
    	try {
            Instagram client = InstagramFactory.getAuthenticatedInstagramClient(username, password, UserAgents.WIN10_CHROME);
            Access access = new Access(username, password, client, username + password);
            users.put(username + password, access);
            System.out.println(access.getToken() + " " + access.getUsername());
            
            Account account = client.getAccountByUsername(username);
            accounts.put(username, account);
    	} catch (IOException e) {
            System.out.println("Unable to login");
            e.printStackTrace();
        }
        return new Login(username, password, userAgent);
    }
}
