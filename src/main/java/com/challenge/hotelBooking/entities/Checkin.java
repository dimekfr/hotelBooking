package com.challenge.hotelBooking.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "checkin")
public class Checkin {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "checkin_generator")
	@Column(name = "checkin_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "guest_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Guest guest;

	@Column(name = "checkin_date")
	private LocalDateTime checkinDate;

	@Column(name = "checkout_date")
	private LocalDateTime checkoutDate;

	@Column(name = "has_vehicle")
	private Boolean hasVehicle;

}
