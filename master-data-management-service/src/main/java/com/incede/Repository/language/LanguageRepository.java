package com.incede.Repository.language;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incede.Model.language.Language;


@Repository
public interface LanguageRepository extends JpaRepository<Language, Integer> {

	Optional<Language> findByTenantIdAndLanguageName(Integer tenantId, String languageName);

	boolean existsByTenantId(Integer tenantId);

	Optional<Language> findByTenantIdAndLanguageNameIgnoreCase(Integer tenantId, String languageName);

	boolean existsByTenantIdAndLanguageName(Integer tenantId, String languageName);

	boolean existsByLanguageName(String languageName);

}
