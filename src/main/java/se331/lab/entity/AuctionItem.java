package se331.lab.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;

    String description;
    String type;

    @OneToMany(mappedBy = "item")
    @Builder.Default
    List<Bid> bids = new ArrayList<>();

    @OneToOne
    Bid successfulBid;
}