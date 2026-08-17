package com.traved.visitor.repository;

import com.traved.visitor.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    // To check if email is already registered
    Optional<Visitor> findByEmail(String email);
}
