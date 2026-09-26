package se331.lab.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se331.lab.entity.Event;

import java.util.List;

public interface EventRepository  extends JpaRepository<Event, Long> {
    List<Event> findAll();
}
