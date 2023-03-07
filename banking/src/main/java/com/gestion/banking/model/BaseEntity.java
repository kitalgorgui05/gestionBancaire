package com.gestion.banking.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid",strategy = "uuid")
    @Column(name = "id", nullable = false,updatable = false,length = 32)
    private String id;

    @CreatedDate
    @PastOrPresent
    @Column(name = "dateCreate", nullable = false, updatable = false)
    private LocalDateTime dateCreate=LocalDateTime.now();

    @LastModifiedDate
    @PastOrPresent
    @Column(name = "dateUpdate", nullable = false)
    private LocalDateTime dateUpdate = LocalDateTime.now();
}
