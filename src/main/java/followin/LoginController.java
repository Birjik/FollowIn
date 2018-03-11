package followin;
import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.postaddict.instagram.scraper.Instagram;
import me.postaddict.instagram.scraper.InstagramFactory;
import me.postaddict.instagram.scraper.interceptor.UserAgents;
import me.postaddict.instagram.scraper.model.Account;
import rest.Login;

@RestController
public class LoginController
{
    private static Logger logger = Logger.getLogger(LoginController.class.getName());
    @RequestMapping("/login")
    public Login login(@RequestParam(value="username", required=false) String username,
            @RequestParam(value="password", required=false) String password,
            @RequestParam(value="user-agent", required=false) String userAgent) { // UNUSED
        try {
            Instagram client = InstagramFactory.getAuthenticatedInstagramClient(username, password, UserAgents.OSX_CHROME);
            Account account = client.getAccountByUsername("kevin");
            logger.info(Integer.toString(account.getMedia().getCount())); // will print 1587
        } catch (IOException e) {
            System.out.println("Unable to login");
            e.printStackTrace();
        }
        return new Login(username, password, userAgent);
    }
}
