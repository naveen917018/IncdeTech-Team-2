package com.incede.Model.taxCategory;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "tax_categories",
    schema = "master_data",
    uniqueConstraints = @UniqueConstraint(name = "uc_tax_category", columnNames = {"tenant_id","tax_cat_name"})
)
public class TaxCategory extends BaseEntity{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "tax_cat_id")
    private Integer taxCategoryId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "tax_cat_name", nullable = false)
    private String name;

    @Column(name = "is_active", nullable = false)
    private Boolean active = true;
}