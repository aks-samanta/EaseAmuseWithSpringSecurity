package com.EaseAmuse.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.EaseAmuse.models.Manager;

@Repository
public interface ManagerRepo extends JpaRepository<Manager, Integer> {
	
	Optional<Manager> findByEmail(String email);

}
