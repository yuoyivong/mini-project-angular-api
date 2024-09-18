package spring.monster.model.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RealmRolesRequest {

    private String roleName;
    private String description;

}
