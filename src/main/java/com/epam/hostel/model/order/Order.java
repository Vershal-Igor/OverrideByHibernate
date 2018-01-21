package com.epam.hostel.model.order;

import com.epam.hostel.model.room.Room;
import com.epam.hostel.model.user.User;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Order implements Serializable {
    private Long id;
    private Date arrivalDate;
    private Date departureDate;
    private int placesAmount;
    private PayType payType;
    private BigDecimal discount;
    private OrderStatus orderStatus;
    private BigDecimal finalPrice;
    private Long userId;
    private Long roomId;
    private User userByUserId;
    private Room roomByRoomId;
    private String name;
    private String surname;
    private byte roomNumber;

    @ManyToOne
    @JoinColumn(name = "User_id", referencedColumnName = "u_id", nullable = false, insertable = false, updatable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "Room_id", referencedColumnName = "r_id", nullable = false, insertable = false, updatable = false)
    public Room getRoomByRoomId() {
        return roomByRoomId;
    }

    public void setRoomByRoomId(Room roomByRoomId) {
        this.roomByRoomId = roomByRoomId;
    }

    @Id
    @Column(name = "o_id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "arrival_date", nullable = false)
    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "depature_date", nullable = false)
    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date depatureDate) {
        this.departureDate = depatureDate;
    }

    @Basic
    @Column(name = "places_amount", nullable = false)
    public int getPlacesAmount() {
        return placesAmount;
    }

    public void setPlacesAmount(int placesAmount) {
        this.placesAmount = placesAmount;
    }

    @Basic
    @Column(name = "pay_type", nullable = false)
    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    @Basic
    @Column(name = "discount", nullable = true, precision = 2)
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    @Basic
    @Column(name = "status", nullable = true)
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    @Basic
    @Column(name = "User_id", nullable = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "Room_id", nullable = false)
    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    @Transient
    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    @Transient
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Transient
    public byte getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(byte roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Transient
    public boolean isConfirm() {
        return orderStatus.CONFIRMED == orderStatus;
    }

    @Transient
    public boolean isReject() {
        return orderStatus.REJECTED == orderStatus;
    }

    @Transient
    public boolean isArchive() {
        return orderStatus.ARCHIVED == orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (placesAmount != order.placesAmount) return false;
        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (arrivalDate != null ? !arrivalDate.equals(order.arrivalDate) : order.arrivalDate != null) return false;
        if (departureDate != null ? !departureDate.equals(order.departureDate) : order.departureDate != null)
            return false;
        if (payType != order.payType) return false;
        if (discount != null ? !discount.equals(order.discount) : order.discount != null) return false;
        if (orderStatus != order.orderStatus) return false;
        if (finalPrice != null ? !finalPrice.equals(order.finalPrice) : order.finalPrice != null) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        if (roomId != null ? !roomId.equals(order.roomId) : order.roomId != null) return false;
        if (userByUserId != null ? !userByUserId.equals(order.userByUserId) : order.userByUserId != null) return false;
        return roomByRoomId != null ? roomByRoomId.equals(order.roomByRoomId) : order.roomByRoomId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (arrivalDate != null ? arrivalDate.hashCode() : 0);
        result = 31 * result + (departureDate != null ? departureDate.hashCode() : 0);
        result = 31 * result + placesAmount;
        result = 31 * result + (payType != null ? payType.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        result = 31 * result + (finalPrice != null ? finalPrice.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (roomId != null ? roomId.hashCode() : 0);
        result = 31 * result + (userByUserId != null ? userByUserId.hashCode() : 0);
        result = 31 * result + (roomByRoomId != null ? roomByRoomId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", arrivalDate=" + arrivalDate +
                ", departureDate=" + departureDate +
                ", placesAmount=" + placesAmount +
                ", payType=" + payType +
                ", discount=" + discount +
                ", orderStatus=" + orderStatus +
                ", finalPrice=" + finalPrice +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", userByUserId=" + userByUserId +
                ", roomByRoomId=" + roomByRoomId +
                '}';
    }
}
