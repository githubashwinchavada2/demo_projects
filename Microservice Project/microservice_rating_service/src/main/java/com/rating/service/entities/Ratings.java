package com.rating.service.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "micro_ratings")
public class Ratings {

    @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String ratingId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "hotel_id", nullable = false)
    private String hotelId;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "feedback", nullable = false)
    private String feedback;

}
