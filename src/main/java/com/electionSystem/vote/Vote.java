package com.electionSystem.vote;

import com.electionSystem.candidate.Candidate;
import com.electionSystem.position.Position;
import com.electionSystem.userManager.user.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "ES_vote")
@Data
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voter_user_id", nullable = false)
    private Users voter;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    @JsonIgnore
    private Candidate candidate;

    private LocalDateTime voteDateTime;

    public Vote(Users voter, Candidate candidate) {
        this.voter = voter;
        this.candidate = candidate;
        this.voteDateTime = LocalDateTime.now();
    }
}
