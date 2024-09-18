package org.example;

import java.util.*;

class Hotel {
    private String name, location;
    private List<Room> rooms = new ArrayList<>();
    public Hotel(String name, String location) { this.name = name; this.location = location; }
    public void addRoom(Room room) { rooms.add(room); }
    public List<Room> getRooms() { return rooms; }
}

class Room {
    private int roomNumber;
    private double price;
    private boolean availability;
    private List<Reservation> reservations = new ArrayList<>();

    public Room(int roomNumber, double price, boolean availability) {
        this.roomNumber = roomNumber; this.price = price; this.availability = availability;
    }
    public boolean isAvailable(Date checkIn, Date checkOut) {
        if (!availability) return false;
        for (Reservation res : reservations)
            if (isDateWithinRange(checkIn, res.getCheckInDate(), res.getCheckOutDate()) ||
                    isDateWithinRange(checkOut, res.getCheckInDate(), res.getCheckOutDate())) return false;
        return true;
    }
    private boolean isDateWithinRange(Date checkDate, Date startDate, Date endDate) {
        return checkDate.compareTo(startDate) >= 0 && checkDate.compareTo(endDate) <= 0;
    }
    public void addReservation(Reservation reservation) {
        reservations.add(reservation); this.availability = false;
    }
}

// Guest Class
class Guest {
    private String name, email, phone;
    public Guest(String name, String email, String phone) {
        this.name = name; this.email = email; this.phone = phone;
    }
    public String getName() { return name; }
}

// Reservation Class
class Reservation {
    private Guest guest;
    private Room room;
    private Date checkIn, checkOut;
    public Reservation(Guest guest, Room room, Date checkIn, Date checkOut) {
        this.guest = guest; this.room = room; this.checkIn = checkIn; this.checkOut = checkOut;
        room.addReservation(this);
    }
    public Date getCheckInDate() { return checkIn; }
    public Date getCheckOutDate() { return checkOut; }
}

// Main Class
public class HotelBookingSystem {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Sunshine", "NY");
        Room room1 = new Room(101, 150, true);
        hotel.addRoom(room1);

        Guest guest1 = new Guest("John Doe", "john@example.com", "12345");

        Calendar cal = Calendar.getInstance();
        cal.set(2024, Calendar.SEPTEMBER, 20);
        Date checkIn = cal.getTime();
        cal.set(2024, Calendar.SEPTEMBER, 25);
        Date checkOut = cal.getTime();

        if (room1.isAvailable(checkIn, checkOut)) {
            new Reservation(guest1, room1, checkIn, checkOut);
            System.out.println("Reservation successful for guest: " + guest1.getName());
        } else {
            System.out.println("Room is not available.");
        }
    }
}


