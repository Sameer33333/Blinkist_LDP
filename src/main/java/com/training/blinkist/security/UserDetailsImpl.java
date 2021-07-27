package com.training.blinkist.security;


import com.training.blinkist.modelentity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

    public class UserDetailsImpl implements UserDetails {

        private static final long serialVersionUID = 1L;

        private String rolePrefix = "ROLE_";

        private String username;
        private String password;
        private boolean isActive;
        private boolean isAdmin;

        private String role;

        public UserDetailsImpl(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.isActive = user.isActive();
            this.isAdmin = user.isAdmin();

            if (this.isAdmin) {
                this.role = "ADMIN";
            }
            else {
                this.role = "USER";
            }
        }

        public UserDetailsImpl() {}

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> list = new ArrayList<>();

            list.add(new SimpleGrantedAuthority(rolePrefix + role));

            return list;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {

            return username;
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
            return isActive;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setActive(boolean active) {
            isActive = active;
        }

        public boolean isAdmin() {
            return isAdmin;
        }

        public void setAdmin(boolean admin) {
            isAdmin = admin;
        }
    }

