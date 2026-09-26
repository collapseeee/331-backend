package se331.lab.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se331.lab.entity.Event;
import se331.lab.entity.Organizer;
import se331.lab.entity.Participant;
import se331.lab.repository.EventRepository;
import se331.lab.repository.OrganizerRepository;
import se331.lab.repository.ParticipantRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;
    final ParticipantRepository participantRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Organizer org1, org2, org3;
        org1 = organizerRepository.save(Organizer.builder().name("CAMT").build());
        org2 = organizerRepository.save(Organizer.builder().name("CMU").build());
        org3 = organizerRepository.save(Organizer.builder().name("ChiangMai").build());
        Participant p1, p2, p3, p4, p5;
        p1 = participantRepository.save(Participant.builder().name("Person One").telNo("0888888881").build());
        p2 = participantRepository.save(Participant.builder().name("Person Two").telNo("0888888882").build());
        p3 = participantRepository.save(Participant.builder().name("Person Three").telNo("0888888883").build());
        p4 = participantRepository.save(Participant.builder().name("Person Four").telNo("0888888884").build());
        p5 = participantRepository.save(Participant.builder().name("Person Five").telNo("0888888885").build());
        Event tempEvent;

        tempEvent = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Midterm Exam")
                .description("A time for taking the exam")
                .location("CAMT Building")
                .date("3rd Sept")
                .time("3.00-4.00pm.")
                .petsAllowed(false)
                .build());
        tempEvent.setOrganizer(org1);
        p1.getEventHistories().add(tempEvent);
        p2.getEventHistories().add(tempEvent);
        p3.getEventHistories().add(tempEvent);
        org1.getOwnEvents().add(tempEvent);

        tempEvent = eventRepository.save(Event.builder()
                .category("Academic")
                .title("Commencement Day")
                .description("A time for celebration")
                .location("CMU Convention Hall")
                .date("21th Jan")
                .time("8.00am-4.00pm.")
                .petsAllowed(false)
                .build());
        tempEvent.setOrganizer(org1);
        p1.getEventHistories().add(tempEvent);
        p2.getEventHistories().add(tempEvent);
        p3.getEventHistories().add(tempEvent);
        p4.getEventHistories().add(tempEvent);
        org1.getOwnEvents().add(tempEvent);

        tempEvent = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Loy Krathong")
                .description("A time for Kra Thong")
                .location("Ping River")
                .date("21th Nov")
                .time("8.00-10.00pm.")
                .petsAllowed(false)
                .build());
        tempEvent.setOrganizer(org2);
        p1.getEventHistories().add(tempEvent);
        p2.getEventHistories().add(tempEvent);
        p3.getEventHistories().add(tempEvent);
        p5.getEventHistories().add(tempEvent);
        org2.getOwnEvents().add(tempEvent);

        tempEvent = eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Songkran")
                .description("Let's play water")
                .location("Chiang Mai Moat")
                .date("13rd April")
                .time("10.00am-6.00pm.")
                .petsAllowed(true)
                .build());
        tempEvent.setOrganizer(org3);
        p4.getEventHistories().add(tempEvent);
        p5.getEventHistories().add(tempEvent);
        org3.getOwnEvents().add(tempEvent);
    }
}
