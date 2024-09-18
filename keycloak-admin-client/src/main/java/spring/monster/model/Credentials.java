package spring.monster.model;

import org.keycloak.representations.idm.CredentialRepresentation;

public class Credentials {

    public static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredential = new CredentialRepresentation();
        passwordCredential.setType(CredentialRepresentation.PASSWORD);
        passwordCredential.setValue(password);
        passwordCredential.setTemporary(false);

        return passwordCredential;
    }
}
