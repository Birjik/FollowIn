package rest;
import lombok.*;

@Getter
@AllArgsConstructor
public class Login
{
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private String userAgent;
}
