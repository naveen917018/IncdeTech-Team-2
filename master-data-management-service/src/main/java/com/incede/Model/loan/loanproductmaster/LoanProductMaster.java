package com.incede.Model.loan.loanproductmaster;

import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(
	    name = "loan_product_master",
	    schema = "master_data")
public class LoanProductMaster extends BaseEntity{

	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "tenant_id", nullable = false)
    private Integer tenantId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_code", nullable = false, unique = true)
    private String productCode;

    @Column(name = "loan_category_id")
    private Integer loanCategoryId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "uuid", nullable = false, unique = true)
    private String UUID;
}
