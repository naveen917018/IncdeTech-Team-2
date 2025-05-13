package com.incede.Model.liability.liabilityglheadconfiguration;

import com.incede.Enum.GLAccountType;
import com.incede.Model.baseentity.BaseEntity;

import jakarta.persistence.*;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(name = "liability_scheme_gl_head_configuration",schema = "master_data",uniqueConstraints = {
			@UniqueConstraint(columnNames = {"scheme_id", "gl_account_type"})},indexes = {
				    @Index(name = "idx_scheme_gl", columnList = "scheme_id"),
				    @Index(name = "idx_gl_account", columnList = "gl_account_id")})
public class LiabilitySchemeGlHeadConfiguration extends BaseEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gl_config_id")
    private Integer glConfigId;

    @Column(name = "scheme_id", nullable = false)
    private Integer schemeId;

    @Column(name = "gl_account_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private GLAccountType glAccountType;

    @Column(name = "gl_account_id", nullable = false)
    private Integer glAccountId;
  
}
