package com.java.project.llms.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.project.llms.entity.ConfigurationEntity;

public interface ConfigurationRepository extends JpaRepository<ConfigurationEntity, Long> {

}
