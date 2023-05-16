package com.hrsupportcentresq014.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hrsupportcentresq014.entities.entityUtil.Socials;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
@Builder
public class Employee extends BaseEntity implements UserDetails{

    private String firstname;

    private String middlename;

    private String phoneNo;

    @Indexed(unique = true)
    private String email;

    private String lastName;

    private String password;

    private Role role;


    private Socials social;

    private String pictureUrl;

    private String resumeUrl;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String position;

    private String reportTo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
