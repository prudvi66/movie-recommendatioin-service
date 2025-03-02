package com.java.project.llms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.java.project.llms.entity.UserDetailsEntity;

public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity,Long> {

	
	@Query(nativeQuery=true,value="select * from t_user_info us where us.email=:email")
	UserDetailsEntity findByEmail(@Param("email")String email);

	

}
