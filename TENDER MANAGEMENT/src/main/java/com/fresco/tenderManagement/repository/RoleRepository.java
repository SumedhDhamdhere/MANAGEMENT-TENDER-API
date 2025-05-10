package com.fresco.tenderManagement.repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
		
import com.fresco.tenderManagement.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
    final Logger logger = LoggerFactory.getLogger(RoleRepository.class);
 
    RoleModel findByRolename(String rolename);
}
