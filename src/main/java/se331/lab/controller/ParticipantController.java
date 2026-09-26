package se331.lab.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se331.lab.service.ParticipantService;
import se331.lab.util.LabMapper;

@RestController
@RequiredArgsConstructor
public class ParticipantController {
    final ParticipantService participantService;
    @GetMapping("/participants")
    ResponseEntity<?> getParticipants() {
        return ResponseEntity.ok(LabMapper.INSTANCE.getParticipantDto(participantService.getAllParticipants()));
    }
}
