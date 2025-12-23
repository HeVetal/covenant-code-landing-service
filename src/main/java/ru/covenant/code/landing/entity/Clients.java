package ru.covenant.code.landing.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private String massage;

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
