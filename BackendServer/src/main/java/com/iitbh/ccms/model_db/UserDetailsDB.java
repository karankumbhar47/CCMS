package com.iitbh.ccms.model_db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.iitbh.ccms.model.UserDetails.StatusEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "UserDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDB implements UserDetails {
    @Id
    private ObjectId objectId;
    private String userId;
    private String userName;
    private ArrayList<String> roles;
    private String email;
    private String status;
    private LocalDate dateRegistered;

    private String name;
    private String password;

    public void addRole(String role) {
        if (roles == null) {
            roles = new ArrayList<>();
        }
        roles.add(role);
    }

    @Override
    public ArrayList<RoleAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new RoleAuthority(role))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return (status != StatusEnum.BLOCKED.toString());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @AllArgsConstructor
    public class RoleAuthority implements GrantedAuthority {
        @NonNull
        String role;

        @Override
        public String getAuthority() {
            return role;
        }

    }
}
