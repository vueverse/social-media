package org.vueverse.usermanagement.infrastructure.security.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MappedSuperclass
public abstract class BaseEntity<ID> {

    @Builder.Default
    @Column(name = "created_date", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "updated_date", columnDefinition = "timestamp", nullable = false)
    private LocalDateTime updatedDate;
    @Column(name = "deleted", columnDefinition = "timestamp")
    private LocalDateTime deleted;

    public abstract ID getId();

    public abstract void setId(ID id);
}
