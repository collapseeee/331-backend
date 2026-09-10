package se331.lab.service;

import org.springframework.data.domain.Page;
import se331.lab.entity.Organizer;

public interface OrganizerService {
    Integer getOrganizerSize();
    Page<Organizer> getOrganizers(Integer pageSize, Integer page);
    Organizer getOrganizer(Long id);
    Organizer save(Organizer organizer);
}
