package com.electionSystem.vote;

import com.electionSystem.vote.dto.CandidateVoteResult;
import com.electionSystem.vote.dto.VoteRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/votes")
@Tag(name = "Vote API.")
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping("/vote")
    public ResponseEntity<Vote> vote(@RequestBody @Valid VoteRequest voteRequest) {
        Vote voted = voteService.vote(voteRequest);
        return new ResponseEntity<>(voted, HttpStatus.CREATED);

    }

    @GetMapping("/results/{positionId}")
    public ResponseEntity<List<CandidateVoteResult>> getVoteResultsByPositionId(@PathVariable Long positionId) {
        List<CandidateVoteResult> results = voteService.getVoteResultsByPositionId(positionId);
        return ResponseEntity.ok(results);
    }

}


