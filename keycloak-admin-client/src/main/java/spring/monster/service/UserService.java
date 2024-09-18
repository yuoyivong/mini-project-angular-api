package spring.monster.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.monster.model.Credentials;
import spring.monster.model.request.UserRequest;
import spring.monster.model.response.dto.UserResponse;

import java.net.URI;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    public UserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    //    create new user and store in keycloak
    public UserResponse createNewUser(UserRequest userRequest) {
        // Create password credentials for the user
        CredentialRepresentation credential = Credentials.createPasswordCredentials(userRequest.getPassword());

        // Create a new user representation
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(userRequest.getUsername());
        newUser.setEmail(userRequest.getEmail());
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastName(userRequest.getLastName());
        newUser.setEnabled(true);
        newUser.setCredentials(Collections.singletonList(credential));

        // Get the UsersResource through the realm
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        // Create the new user and get the response
        Response response = usersResource.create(newUser);
        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user");
        }

        // Get the user ID from the location header
        URI location = response.getLocation();
        String userId = location.getPath().replaceAll(".*/([^/]+)$", "$1");

        // Fetch the newly created user details using the user ID
        UserRepresentation createdUser = usersResource.get(userId).toRepresentation();

        return UserResponse.userResponseDTO(createdUser);
    }

    //    get all users
    public List<UserResponse> getAllUsers() {
        List<UserRepresentation> userRepresentationList = keycloak.realm(realm).users().list();

        return userRepresentationList.stream().map(UserResponse::userResponseDTO).toList();
    }

    //    get user by username
    public List<UserResponse> getUserByUsername(String username) {
        List<UserRepresentation> userRepresentationList = keycloak.realm(realm).users().search(username);

        return userRepresentationList.stream().map(UserResponse::userResponseDTO).toList();
    }

    //    get user by userId
    public UserResponse getUserById(String userId) {
//        get the realms resource
        RealmResource realmResource = keycloak.realm(realm);

//        get the user resource
        UsersResource usersResource = realmResource.users();

//        fetch user by user id
        UserRepresentation userRepresentation = usersResource.get(userId).toRepresentation();

        return UserResponse.userResponseDTO(userRepresentation);
    }

//    get user by email
    public List<UserResponse> getUserByEmail(String email) {
        List<UserRepresentation> userRepresentationList = keycloak.realm(realm).users().search(null, null, null, email, null, null);

        return userRepresentationList.stream().map(UserResponse::userResponseDTO).toList();
    }

//    update user by user id
    public UserResponse updateUser(String id, UserRequest userRequest) {

        // Fetch the existing user
        UserResource userResource = keycloak.realm(realm).users().get(id);
        UserRepresentation existingUser = userResource.toRepresentation();
        System.out.println("User " + id + userRequest.toString());

        // Update the fields only if they are modified
        existingUser.setUsername(userRequest.getUsername());
        existingUser.setEmail(userRequest.getEmail());
        existingUser.setFirstName(userRequest.getFirstName());
        existingUser.setLastName(userRequest.getLastName());

        // Update the user in Keycloak
        userResource.update(existingUser);

        // If the password is provided, update the password separately
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            CredentialRepresentation credentials = Credentials.createPasswordCredentials(userRequest.getPassword());
            userResource.resetPassword(credentials);
        }

        return UserResponse.userResponseDTO(existingUser);

    }

//    delete user by user id
    public void deleteUser(String id) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        usersResource.get(id).remove();
    }

}
