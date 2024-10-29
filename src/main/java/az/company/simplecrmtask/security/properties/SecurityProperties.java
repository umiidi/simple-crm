package az.company.simplecrmtask.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private final JwtProperties jwtProperties = new JwtProperties();

    @Getter
    @Setter
    public static class JwtProperties {

        private String secretKey;
        private long accessTokenValidityInSeconds;

    }

}
