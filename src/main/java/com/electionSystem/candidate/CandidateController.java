package com.electionSystem.candidate;

import com.electionSystem.candidate.dto.CandidateReq;
import com.electionSystem.position.Position;
import com.electionSystem.userManager.user.Users;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
@Tag(name = "Vote API.")
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping
    public List<Candidate> getAllCandidates() {
        return candidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidate> getCandidateById(@PathVariable Long id) {
        return ResponseEntity.ok(candidateService.getCandidateById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Candidate> getCandidateByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(candidateService.getCandidateByUserId(userId));
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<Candidate>> getCandidatesByPositionId(@PathVariable Long positionId) {
        return ResponseEntity.ok(candidateService.getCandidatesByPositionId(positionId));
    }

    @PostMapping
    public ResponseEntity<Candidate> createCandidate(@RequestBody @Valid CandidateReq candidateReq) {
        Candidate createdCandidate = candidateService.createCandidate(candidateReq);
        return new ResponseEntity<>(createdCandidate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


