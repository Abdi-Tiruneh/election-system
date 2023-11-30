package com.electionSystem.candidate.dto;

import com.electionSystem.candidate.Candidate;
import com.electionSystem.position.Position;
import com.electionSystem.userManager.user.Users;
import com.electionSystem.userManager.user.dto.UserResponse;
import com.electionSystem.vote.Vote;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CandidateResponse {

    private Long id;
    private UserResponse user;
    private Position position;
    private List<Vote> votes = new ArrayList<>();

    public static CandidateResponse toResponse(Candidate candidate) {

        Users user = candidate.getUser();
        UserResponse userResponse = UserResponse.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .build();


        CandidateResponse candidateResponse = new CandidateResponse();
        candidateResponse.setId(candidate.getId());
        candidateResponse.setUser(userResponse);
        candidateResponse.setPosition(candidate.getPosition());
        candidateResponse.setVotes(candidate.getVotes());

        return candidateResponse;
    }

}
