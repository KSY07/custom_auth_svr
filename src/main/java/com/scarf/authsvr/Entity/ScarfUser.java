package com.scarf.authsvr.Entity;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

import com.scarf.authsvr.Entity.Constant.Roles;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScarfUser extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Getter long id;

    @Email
    private @Getter @Setter String email;

    @NotNull
    private @Getter @Setter String password;

    private @Getter @Setter String username;

    private @Getter @Setter String company;

    private @Getter @Setter Boolean email_verify;

    private @Getter @Setter Boolean isLocked;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "users_roles", 
                joinColumns = @JoinColumn( name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn( name = "role_id", referencedColumnName = "id"))
    private @Getter @Setter Collection<ScarfUserRole> roles;


}
