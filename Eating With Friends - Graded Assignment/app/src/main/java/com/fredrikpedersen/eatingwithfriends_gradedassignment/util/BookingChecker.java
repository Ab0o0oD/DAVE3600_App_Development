package com.fredrikpedersen.eatingwithfriends_gradedassignment.util;

import android.app.Application;

import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Booking;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Friend;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.database.models.Restaurant;
import com.fredrikpedersen.eatingwithfriends_gradedassignment.repository.BookingRepository;

import java.util.List;

public class BookingChecker {

    public static boolean isFriendPartOfBooking(Application application, Friend friend) {
        BookingRepository bookingRepository = new BookingRepository(application);
        List<Booking> allBookings = bookingRepository.getAllBookingsAsList();

        for (Booking booking : allBookings) {
            for (Friend friendInBooking : booking.getFriends()) {
                if (friend.getFirstName().equals(friendInBooking.getFirstName()) &&
                        friend.getLastName().equals(friendInBooking.getLastName()) &&
                        friend.getPhoneNumber().equals(friendInBooking.getPhoneNumber())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isRestaurantPartOfBooking(Application application, Restaurant restaurant) {
        BookingRepository bookingRepository = new BookingRepository(application);
        List<Booking> allBookings = bookingRepository.getAllBookingsAsList();

        for (Booking booking : allBookings) {
            Restaurant restaurantInBooking = booking.getRestaurant();
            if (restaurant.getRestaurantName().equals(restaurantInBooking.getRestaurantName()) &&
                    restaurant.getAddress().equals(restaurantInBooking.getAddress())) {
                return true;
            }
        }
        return false;
    }
}
