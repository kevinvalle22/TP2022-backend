package com.itconsulting.mentalHealth.core.repository;

import com.itconsulting.mentalHealth.core.entity.DAOUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<DAOUser,Long> {
    DAOUser findByUserName(String username);

    Optional<DAOUser> findDAOUserByEmail(String email);
}
