package com.electionSystem.vote.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VoteRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long candidateId;
}
