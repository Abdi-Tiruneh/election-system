package com.electionSystem.vote;

import com.electionSystem.candidate.Candidate;
import com.electionSystem.candidate.CandidateService;
import com.electionSystem.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.electionSystem.userManager.user.UserService;
import com.electionSystem.userManager.user.Users;
import com.electionSystem.vote.dto.CandidateVoteResult;
import com.electionSystem.vote.dto.VoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;
    private final CandidateService candidateService;
    private final UserService userService;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, CandidateService candidateService, UserService userService) {
        this.voteRepository = voteRepository;
        this.candidateService = candidateService;
        this.userService = userService;
    }

    @Override
    public List<Vote> getVotesByCandidateId(Long candidateId) {
        return voteRepository.findByCandidateId(candidateId);
    }

    @Override
    public Vote vote(VoteRequest voteRequest) {

        Long userId = voteRequest.getUserId();
        Long candidateId = voteRequest.getCandidateId();

        // Check if the user has already voted for the candidate
        if (voteRepository.existsByVoterIdAndCandidateId(userId, candidateId))
            throw new ResourceAlreadyExistsException("Already voted for this candidate");

        Users voter = userService.getUserById(userId);
        Candidate candidate = candidateService.getCandidateById(candidateId);

        Vote vote = new Vote(voter, candidate);
        voteRepository.save(vote);

        // Update the candidate's votes
        List<Vote> candidateVotes = candidate.getVotes();
        candidateVotes.add(vote);
        candidate.setVotes(candidateVotes);

        return vote;
    }

    @Override
    public List<CandidateVoteResult> getVoteResultsByPositionId(Long positionId) {
        List<Candidate> candidates = candidateService.getCandidatesByPositionId(positionId);

        return candidates.stream()
                .map(candidate -> {
                    List<Vote> votes = voteRepository.findByCandidateId(candidate.getId());
                    int voteCount = votes.size();

                    CandidateVoteResult result = new CandidateVoteResult();
                    result.setCandidateId(candidate.getId());
                    result.setCandidateName(candidate.getUser().getUsername());
                    result.setVoteCount(voteCount);

                    return result;
                })
                .toList();
    }

}

