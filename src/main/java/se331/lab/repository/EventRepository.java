package se331.lab.repository;

import org.springframework.data.repository.CrudRepository;
import se331.lab.entity.Event;

import java.util.List;

public interface EventRepository  extends CrudRepository<Event, Long> {
    List<Event> findAll();
}
