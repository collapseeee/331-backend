package se331.lab.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se331.lab.entity.Event;
import se331.lab.repository.EventRepository;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    final EventRepository eventRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        eventRepository.save(Event.builder()
                .category("Academic")
                .title("Midterm Exam")
                .description("A time for taking the exam")
                .location("CAMT Building")
                .date("3rd Sept")
                .time("3.00-4.00pm.")
                .petsAllowed(false)
                .organizer("CAMT")
                .build());
        eventRepository.save(Event.builder()
                .category("Academic")
                .title("Commencement Day")
                .description("A time for celebration")
                .location("CMU Convention Hall")
                .date("21th Jan")
                .time("8.00am-4.00pm.")
                .petsAllowed(false)
                .organizer("CMU")
                .build());
        eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Loy Krathong")
                .description("A time for Kra Thong")
                .location("Ping River")
                .date("21th Nov")
                .time("8.00-10.00pm.")
                .petsAllowed(false)
                .organizer("Chiang Mai")
                .build());
        eventRepository.save(Event.builder()
                .category("Cultural")
                .title("Songkran")
                .description("Let's play water")
                .location("Chiang Mai Moat")
                .date("13rd April")
                .time("10.00am-6.00pm.")
                .petsAllowed(true)
                .organizer("Chiang Mai Municipality")
                .build());
    }
}
