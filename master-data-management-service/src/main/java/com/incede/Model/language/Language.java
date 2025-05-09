package com.incede.Model.language;
import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(
    name = "languages",
    schema = "master_data",
    uniqueConstraints = {
        @UniqueConstraint(name = "uc_language", columnNames = {"tenant_id", "language_name"})
    },
    indexes = {
        @Index(name = "idx_language_name", columnList = "language_name")
    }
)
public class Language extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer languageId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "language_name", nullable = false, unique = true)
    private String languageName;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

}

