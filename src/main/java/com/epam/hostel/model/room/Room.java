package com.epam.hostel.model.room;

import com.epam.hostel.model.order.Order;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;


@Entity
public class Room implements Serializable {
    private Long id;
    private byte roomNumber;
    private byte roomPlaces;
    private BigDecimal price;
    private Collection<Order> ordersByRId;

    @OneToMany(mappedBy = "roomByRoomId")
    public Collection<Order> getOrdersByRId() {
        return ordersByRId;
    }

    public void setOrdersByRId(Collection<Order> ordersByRId) {
        this.ordersByRId = ordersByRId;
    }

    @Id
    @Column(name = "r_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "room_number", nullable = false)
    public byte getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(byte roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Basic
    @Column(name = "room_places", nullable = false)
    public byte getRoomPlaces() {
        return roomPlaces;
    }

    public void setRoomPlaces(byte roomPlaces) {
        this.roomPlaces = roomPlaces;
    }

    @Basic
    @Column(name = "price", nullable = false, precision = 2)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (roomNumber != room.roomNumber) return false;
        if (roomPlaces != room.roomPlaces) return false;
        if (id != null ? !id.equals(room.id) : room.id != null) return false;
        if (price != null ? !price.equals(room.price) : room.price != null) return false;
        return ordersByRId != null ? ordersByRId.equals(room.ordersByRId) : room.ordersByRId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (int) roomNumber;
        result = 31 * result + (int) roomPlaces;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (ordersByRId != null ? ordersByRId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", roomPlaces=" + roomPlaces +
                ", price=" + price +
                ", ordersByRId=" + ordersByRId +
                '}';
    }
}
