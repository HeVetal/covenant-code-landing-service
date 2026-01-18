package ru.covenant.code.landing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.covenant.code.landing.entity.enumerated.Status;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "clients_db")
public class Clients {

    @Id
    @GeneratedValue
    @JdbcTypeCode(SqlTypes.CHAR) // Хранить UUID как CHAR(36)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR DEFAULT 'NEW'")
    private Status status;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now();
        updatedAt = OffsetDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
