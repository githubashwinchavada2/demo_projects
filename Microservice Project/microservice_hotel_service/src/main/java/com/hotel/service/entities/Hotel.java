package com.hotel.service.entities;

import java.io.Serializable;

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
@Table(name = "micro_hotel")
public class Hotel implements Serializable {

    private static final long serialVersionUID = -153235747242155761L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private String hotelId;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "about", nullable = false)
    private String about;
}
