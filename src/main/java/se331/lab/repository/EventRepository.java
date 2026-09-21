package se331.lab.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Event;

import java.util.List;

public interface EventRepository  extends JpaRepository<Event, Long> {
    List<Event> findAll();
    Page<Event> findByTitleContaining(String title, Pageable pageRequest);
    Page<Event> findByTitleContainingOrDescriptionContaining(String title, String description, Pageable pageRequest);
}
