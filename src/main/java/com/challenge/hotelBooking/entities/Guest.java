package com.challenge.hotelBooking.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "guest")
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "guest_generator")
	@Column(name = "guest_id")
	private Long id;

	private String name;

	@Column(name = "document_number")
	private String documentNumber;

	private String phone;

	@OneToMany(mappedBy = "guest", targetEntity = Checkin.class, fetch = FetchType.LAZY, cascade = {
			CascadeType.ALL }, orphanRemoval = true)
	private List<Checkin> checkins = new ArrayList<>();

}
