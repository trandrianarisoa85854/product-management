package io.ennov.productmanagement.model.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * @author : t.randrianarisoa
 * @project : product-management
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Schema(description = "product title", required = true)
    @Column(name = "title", nullable = false)
    private String title;

    @Schema(description = "product price", required = true)
    @Column(name = "price", nullable = false)
    private Double price;

    @Schema(description = "product description")
    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @CreationTimestamp(source = SourceType.DB)
    @Column(updatable = false, name = "created_at")
    private Instant createdAt;

    @UpdateTimestamp(source = SourceType.DB)
    @Column(name = "updated_at")
    private Instant updatedAt;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.title, this.description);
    }

    @Override
    public String toString() {
        return "Product {" + "id=" + this.id + ", title='" + this.title+ ", price=" + this.price + ", ', description='" + this.description + "'}'";
    }
}
