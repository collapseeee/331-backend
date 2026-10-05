package se331.lab.entity;

import jakarta.persistence.*;
import lombok.*;
import se331.lab.security.user.User;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    Long id;
    String name;
    String address;
    @OneToMany(mappedBy = "organizer")
    @Builder.Default
    List<Event> ownEvents = new ArrayList<>();
    @ElementCollection
    List<String> images;
    @OneToOne
    User user;
}
