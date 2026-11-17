package com.auca.classroom;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class Classroom implements Identifiable, Registrable {
    private String classroomId, buildingName, roomNumber;
    private int capacity, floorNumber;
    private boolean available;

    public Classroom() {}
    public Classroom(String id, String building, String room, int capacity, int floor, boolean available) {
        setClassroomId(id); setBuildingName(building); setRoomNumber(room);
        setCapacity(capacity); setFloorNumber(floor); setAvailable(available);
    }
    public String getClassroomId() { return classroomId; }
    public void setClassroomId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); classroomId = v; }
    public String getBuildingName() { return buildingName; }
    public void setBuildingName(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); buildingName = v; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); roomNumber = v; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int v) { if (v < 0) throw new IllegalArgumentException("capacity cannot be negative"); capacity = v; }
    public int getFloorNumber() { return floorNumber; }
    public void setFloorNumber(int v) { if (v < 0) throw new IllegalArgumentException("floorNumber cannot be negative"); floorNumber = v; }
    public boolean getAvailable() { return available; }
    public void setAvailable(boolean v) { available = v; }
    @Override public String getId() { return classroomId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return classroomId; }
    @Override public void validateRegistration() throws ValidationException {
        if (classroomId == null || classroomId.trim().isEmpty()) throw new ValidationException("Classroom ID required");
        if (capacity <= 0) throw new ValidationException("Capacity must be positive");
    }
    @Override public String toString() {
        return "CLASSROOM RECORD\nClassroom ID: " + classroomId + "\nBuilding: " + buildingName +
            "\nRoom: " + roomNumber + "\nCapacity: " + capacity + "\nFloor: " + floorNumber + "\nAvailable: " + available;
    }
}
