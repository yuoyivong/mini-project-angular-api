package spring.monster.configuration;


import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakClientConfig {

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak.credentials.client-id}")
    private String clientId;

    @Value("${keycloak.auth-server-url}")
    private String authUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .serverUrl(authUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }



}
