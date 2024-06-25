package io.ennov.productmanagement.model.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "`user`")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "username/email for login", required = true)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Schema(description = "password for login", required = true)
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "first name", required = true)
    @Column(name = "first_name", nullable = false, unique = true)
    private String firstname;

    @Schema(description = "last name", required = true)
    @Column(name = "last_name", nullable = false, unique = true)
    private String lastname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Schema(description = "Collection of product for user.")
    private Set<Product> products;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();


}
