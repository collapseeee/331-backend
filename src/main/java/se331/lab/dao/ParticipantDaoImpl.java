package se331.lab.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import se331.lab.entity.Participant;
import se331.lab.repository.ParticipantRepository;

@Repository
@RequiredArgsConstructor
@Profile("db")
public class ParticipantDaoImpl implements ParticipantDao {
    final ParticipantRepository participantRepository;
    @Override
    public Page<Participant> getParticipants(Pageable pageRequest) {
        return participantRepository.findAll(pageRequest);
    }
}
