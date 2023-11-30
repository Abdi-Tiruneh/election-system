package com.electionSystem.candidate;

import com.electionSystem.candidate.dto.CandidateReq;
import com.electionSystem.position.Position;
import com.electionSystem.userManager.user.Users;

import java.util.List;

public interface CandidateService {

    List<Candidate> getAllCandidates();

    Candidate getCandidateById(Long id);

    Candidate getCandidateByUserId(Long userId);

    List<Candidate> getCandidatesByPositionId(Long positionId);

    Candidate createCandidate(CandidateReq candidateReq);

    void deleteCandidate(Long id);
}

