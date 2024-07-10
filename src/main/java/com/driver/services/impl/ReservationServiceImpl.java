package com.driver.services.impl;

import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;

    //Reserve a spot in the given parkingLot such that the total price is minimum. Note that the price per hour for each spot is different
    //Note that the vehicle can only be parked in a spot having a type equal to or larger than given vehicle
    //If parkingLot is not found, user is not found, or no spot is available, throw "Cannot make reservation" exception.
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception {
        ParkingLot parkingLot = parkingLotRepository3.findById(parkingLotId)
                .orElseThrow(() -> new Exception("Cannot make reservation"));
        User user = userRepository3.findById(userId)
                .orElseThrow(() -> new Exception("Cannot make reservation"));
        List<Spot> spots = spotRepository3.findByOccupiedFalse();

        Spot spot = null;
        for (Spot s : spots) {
            if (canPark(s, numberOfWheels)) {
                if (spot == null) {
                    spot = s;
                } else if (spot.getPricePerHour() > s.getPricePerHour()) {
                    spot = s;
                }
            }
        }

        if (spot == null) {
            throw new Exception("Cannot make reservation");
        }

        Reservation reservation = new Reservation();
        reservation.setSpot(spot);
        reservation.setUser(user);
        reservation.setNumberOfHours(timeInHours);
        reservationRepository3.save(reservation);
        return reservation;
    }

    private boolean canPark(Spot spot, Integer numberOfWheels) {
        switch (numberOfWheels) {
            case 2:
                return spot.getSpotType().equals(SpotType.TWO_WHEELER) ||
                        spot.getSpotType().equals(SpotType.FOUR_WHEELER);
            case 4:
                return spot.getSpotType().equals(SpotType.FOUR_WHEELER);
            default:
                return spot.getSpotType().equals(SpotType.OTHERS);
        }
    }
}
