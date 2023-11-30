package com.electionSystem.candidate;

import com.electionSystem.candidate.dto.CandidateReq;
import com.electionSystem.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.electionSystem.exceptions.customExceptions.ResourceNotFoundException;
import com.electionSystem.position.Position;
import com.electionSystem.position.PositionService;
import com.electionSystem.userManager.user.UserService;
import com.electionSystem.userManager.user.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final UserService userService;
    private final PositionService positionService;

    public CandidateServiceImpl(CandidateRepository candidateRepository, UserService userService, PositionService positionService) {
        this.candidateRepository = candidateRepository;
        this.userService = userService;
        this.positionService = positionService;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found"));
    }

    @Override
    public Candidate getCandidateByUserId(Long userId) {
        return candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Vote not found"));
    }

    @Override
    public List<Candidate> getCandidatesByPositionId(Long positionId) {
        return candidateRepository.findByPositionId(positionId);
    }

    @Override
    public Candidate createCandidate(CandidateReq candidateReq) {

        Long userId = candidateReq.getUserId();
        Long positionId = candidateReq.getPositionId();

        Optional<Candidate> existingCandidate = candidateRepository.findByUserId(userId);
        if (existingCandidate.isPresent())
            throw new ResourceAlreadyExistsException("The user is already a candidate for the " + existingCandidate.get().getPosition().getName() + " position.");

        Users user = userService.getUserById(userId);
        Position position = positionService.getPositionById(positionId);

        Candidate candidate = new Candidate(user, position);
        return candidateRepository.save(candidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        getCandidateById(id);
        candidateRepository.deleteById(id);
    }

}
