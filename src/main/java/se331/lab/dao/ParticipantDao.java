package se331.lab.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import se331.lab.entity.Participant;

public interface ParticipantDao {
    Page<Participant> getParticipants(Pageable pageRequest);
}
