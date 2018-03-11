package followin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rest.Login;

@RestController
public class LoginController
{
    @RequestMapping("/login")
    public Login login(@RequestParam(value="username", required=false) String username,
            @RequestParam(value="password", required=false) String password,
            @RequestParam(value="user-agent", required=false) String userAgent) {
        return new Login(username, password, userAgent);
    }
}
