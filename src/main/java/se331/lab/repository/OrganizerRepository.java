package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Organizer;

import java.util.List;

public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    List<Organizer> findAll();
}
