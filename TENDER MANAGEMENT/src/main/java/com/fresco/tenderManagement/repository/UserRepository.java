package com.fresco.tenderManagement.repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
		
import com.fresco.tenderManagement.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    final Logger logger = LoggerFactory.getLogger(UserRepository.class);  
    UserModel findByEmail(String email);
}
