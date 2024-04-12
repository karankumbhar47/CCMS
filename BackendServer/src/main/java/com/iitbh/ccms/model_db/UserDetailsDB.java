package com.iitbh.ccms.model_db;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.iitbh.ccms.model.Role;
import com.iitbh.ccms.model.UserInfo;
import com.iitbh.ccms.model.UserInfo.StatusEnum;

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
    private List<String> roles;
    private String email;
    private String status;
    private LocalDate dateRegistered;

    private String name;
    private String password;
    private String department;
    private String phoneNumber;

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

    public UserInfo convertToUserInfo() {
        UserInfo details = new UserInfo();
        details.setUserId(this.userId);
        details.setUserName(this.userName);
        details.setRoles(this.getRoleArray(this.roles));
        details.setEmail(this.email);
        details.setStatus(getUserStatus(this.status));
        details.setDateRegistered(this.dateRegistered);

        details.setName(this.name);
        details.setPassword(this.password);
        details.setDepartment(this.department);
        details.setPhoneNumber(this.phoneNumber);
        return details;
    }

    public UserDetailsDB(UserInfo userInfo) {
        userId = userInfo.getUserId();
        userName = userInfo.getUserName();
        roles = convertRolesToString(userInfo.getRoles());
        email = userInfo.getEmail();
        status = userInfo.getStatus().toString();
        dateRegistered = userInfo.getDateRegistered();
        name = userInfo.getName();
        password = userInfo.getPassword();
        department = userInfo.getDepartment();
        phoneNumber = userInfo.getPhoneNumber();
    }

    public static List<String> convertRolesToString(List<Role> roles) {
        return roles.stream()
                .map(Role::toString)
                .collect(Collectors.toList());
    }

    List<Role> getRoleArray(List<String> roles) {
        List<Role> roleList = new ArrayList<>();
        for (String role : roles) {
            if (role.equalsIgnoreCase("admin")) {
                roleList.add(Role.ADMIN);
            } else {
                roleList.add(Role.USER);
            }
        }
        return roleList;
    }

    public UserInfo.StatusEnum getUserStatus(String status) {
        if (status.equalsIgnoreCase("active")) {
            return UserInfo.StatusEnum.ACTIVE;
        } else {
            return UserInfo.StatusEnum.BLOCKED;
        }
    }

}
