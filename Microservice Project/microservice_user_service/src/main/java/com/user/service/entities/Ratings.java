package com.user.service.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ratings {

    private String ratingId;

    private String userId;

    private String hotelId;

    private Integer rating;

    private String feedback;

    private Hotel hotel;
}
