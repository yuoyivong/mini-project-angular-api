package spring.monster.service;

import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import spring.monster.model.request.RealmRolesRequest;
import spring.monster.model.response.dto.RealmRolesResponse;

import java.util.List;
import java.util.UUID;

@Service
public class RealmRolesService {

    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;

    public RealmRolesService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

//    get all realm roles
    public List<String> getAllRoles() {

        List<String> rolesList = keycloak.realm(realm)
                .roles().list()
                .stream().map(RoleRepresentation::getName)
                .toList();

        return rolesList;
    }

//    create new role
    public RealmRolesResponse createNewRole(RealmRolesRequest rolesRequest) {

        RoleRepresentation roleRep = new RoleRepresentation();
        roleRep.setId(String.valueOf(UUID.randomUUID()));
        roleRep.setName(rolesRequest.getRoleName());
        roleRep.setDescription(rolesRequest.getDescription());

        RealmResource realmResource = keycloak.realm(realm);
        RolesResource roleResource = realmResource.roles();

        roleResource.create(roleRep);

//        fetch created role by name
        RoleRepresentation roleRepresentation = roleResource.get(roleRep.getId()).toRepresentation();

        return RealmRolesResponse.rolesResponseDTO(roleRepresentation);
    }


}
