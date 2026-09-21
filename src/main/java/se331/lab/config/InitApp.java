package se331.lab.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import se331.lab.entity.*;
import se331.lab.repository.*;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitApp implements ApplicationListener<ApplicationReadyEvent> {
    final EventRepository eventRepository;
    final OrganizerRepository organizerRepository;
    final ParticipantRepository participantRepository;
    final AuctionItemRepository auctionItemRepository;
    final BidRepository bidRepository;

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

//* Task 3 of Lab09 part */
        AuctionItem item1, item2, item3, item4, item5;
        Bid b1, b2, b3;

// ==========================================
// Item 1: 3 Bids, HAS Successful Bid
// ==========================================
        item1 = auctionItemRepository.save(AuctionItem.builder()
                .description("Item 1")
                .type("Type 1")
                .build());

        b1 = bidRepository.save(Bid.builder().amount(500.0).datetime("2026-09-21 10:00:00").item(item1).build());
        b2 = bidRepository.save(Bid.builder().amount(600.0).datetime("2026-09-21 10:30:00").item(item1).build());
        b3 = bidRepository.save(Bid.builder().amount(750.0).datetime("2026-09-21 11:00:00").item(item1).build());

        item1.getBids().add(b1);
        item1.getBids().add(b2);
        item1.getBids().add(b3);
        item1.setSuccessfulBid(b3); // Successful Bid 1

// ==========================================
// Item 2: 3 Bids, HAS Successful Bid
// ==========================================
        item2 = auctionItemRepository.save(AuctionItem.builder()
                .description("Item 2")
                .type("Type 2")
                .build());

        b1 = bidRepository.save(Bid.builder().amount(1200.0).datetime("2026-09-21 11:15:00").item(item2).build());
        b2 = bidRepository.save(Bid.builder().amount(1500.0).datetime("2026-09-21 11:45:00").item(item2).build());
        b3 = bidRepository.save(Bid.builder().amount(1800.0).datetime("2026-09-21 12:00:00").item(item2).build());

        item2.getBids().add(b1);
        item2.getBids().add(b2);
        item2.getBids().add(b3);
        item2.setSuccessfulBid(b3); // Successful Bid 2

// ==========================================
// Item 3: 3 Bids, HAS Successful Bid
// ==========================================
        item3 = auctionItemRepository.save(AuctionItem.builder()
                .description("Item 3")
                .type("Type 3")
                .build());

        b1 = bidRepository.save(Bid.builder().amount(200.0).datetime("2026-09-21 12:30:00").item(item3).build());
        b2 = bidRepository.save(Bid.builder().amount(350.0).datetime("2026-09-21 13:00:00").item(item3).build());
        b3 = bidRepository.save(Bid.builder().amount(400.0).datetime("2026-09-21 13:15:00").item(item3).build());

        item3.getBids().add(b1);
        item3.getBids().add(b2);
        item3.getBids().add(b3);
        item3.setSuccessfulBid(b3); // Successful Bid 3

// ==========================================
// Item 4: 3 Bids, NO Successful Bid
// ==========================================
        item4 = auctionItemRepository.save(AuctionItem.builder()
                .description("Item 4")
                .type("Type 2")
                .build());

        b1 = bidRepository.save(Bid.builder().amount(2500.0).datetime("2026-09-21 14:00:00").item(item4).build());
        b2 = bidRepository.save(Bid.builder().amount(2700.0).datetime("2026-09-21 14:30:00").item(item4).build());
        b3 = bidRepository.save(Bid.builder().amount(3000.0).datetime("2026-09-21 15:00:00").item(item4).build());

        item4.getBids().add(b1);
        item4.getBids().add(b2);
        item4.getBids().add(b3);
// No successful bid set for item4

// ==========================================
// Item 5: 3 Bids, NO Successful Bid
// ==========================================
        item5 = auctionItemRepository.save(AuctionItem.builder()
                .description("Item 5")
                .type("Type 1")
                .build());

        b1 = bidRepository.save(Bid.builder().amount(800.0).datetime("2026-09-21 15:15:00").item(item5).build());
        b2 = bidRepository.save(Bid.builder().amount(950.0).datetime("2026-09-21 15:30:00").item(item5).build());
        b3 = bidRepository.save(Bid.builder().amount(1100.0).datetime("2026-09-21 15:45:00").item(item5).build());

        item5.getBids().add(b1);
        item5.getBids().add(b2);
        item5.getBids().add(b3);
// No successful bid set for item5
    }
}
