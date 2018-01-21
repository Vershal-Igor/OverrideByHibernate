package com.epam.hostel.dao.impl;


import com.epam.hostel.dao.IRoomDAO;
import com.epam.hostel.dao.exception.DAOException;
import com.epam.hostel.model.order.Order;
import com.epam.hostel.model.room.Room;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for working with Room
 *
 * @author Vershal
 * @version 1.0
 */
public class RoomDAOImpl implements IRoomDAO {
    private static Logger logger = Logger.getLogger(RoomDAOImpl.class);

    private static final String SELECT_ALL_ROOMS = "SELECT * FROM room;";
    private static final String SELECT_ROOM_BY_ID = "SELECT * FROM room WHERE r_id=?;";
    private static final String INSERT_ROOM = "INSERT INTO room(room_number, room_places, price) VALUES(?, ?, ?);";
    private static final String DELETE_ROOM = "DELETE FROM room WHERE r_id=?;";
    private static final String UPDATE_ROOM_PRICE = "UPDATE room SET price=? WHERE r_id=?;";
    private static final String SHOW_FREE_ROOMS = "SELECT * FROM room WHERE NOT EXISTS(SELECT * FROM `Order` WHERE " +
            "arrival_date<=? AND depature_date>=? AND room_id=room.r_id) AND room_places>=?;";


    private BasicDataSource dataSource;
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SessionFactory sessionFactory;

    public RoomDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Retrieves all rooms
     *
     * @return List<Room>
     * @throws DAOException
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
            ConstraintViolationException.class})
    @Override
    public List<Room> findAll() throws DAOException {
        Session session = sessionFactory.getCurrentSession();
        List<Room> rooms = session.createQuery("from Room r").list();
        return rooms;
    }

    /**
     * Retrieves room by definite room id
     *
     * @param id
     * @return Room
     */
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
            ConstraintViolationException.class})
    @Override
    public Room findById(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Room.class, id);
        /*return jdbcTemplate.queryForObject(SELECT_ROOM_BY_ID, new RoomMapper(), id);*/
    }

    /**
     * Add new room to db
     *
     * @param room
     * @return long
     * @throws DAOException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
            ConstraintViolationException.class})
    @Override
    public long add(Room room) throws DAOException {
        PreparedStatementCreator psc = con -> con.prepareStatement(INSERT_ROOM, Statement.RETURN_GENERATED_KEYS);

        return jdbcTemplate.execute(psc, new PreparedStatementCallback<Long>() {

            @Override
            public Long doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setByte(1, room.getRoomNumber());
                ps.setByte(2, room.getRoomPlaces());
                ps.setBigDecimal(3, room.getPrice());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getLong(1);
                }
                return null;
            }
        });
    }

    /**
     * Delete room by definite room id
     *
     * @param id
     * @return boolean
     * @throws DAOException
     */

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
            ConstraintViolationException.class})
    @Override
    public boolean delete(Long id) throws DAOException {
        Session session = sessionFactory.getCurrentSession();
        Room room = session.get(Room.class, id);
        session.delete(room);
        return true;
        /*PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                return con.prepareStatement(DELETE_ROOM);
            }
        };
        return jdbcTemplate.execute(psc, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setLong(1, id);
                ps.executeUpdate();
                return true;
            }
        });*/
    }

    /**
     * Edit room price by definite room id
     *
     * @param room
     * @return Long
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
            ConstraintViolationException.class})
    @Override
    public Long update(Room room) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("UPDATE Room SET price =:price WHERE id =:id");
        query.setParameter("price", room.getPrice());
        query.setParameter("id", room.getId());
        query.executeUpdate();
        return room.getId();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = {ObjectNotFoundException.class,
            ConstraintViolationException.class})
    @Override
    public List<Room> showFreeRooms(Order order) throws DAOException {
        /*Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Room r where not exists " +
                "(from Order o where o.arrivalDate<=:arrival AND departureDate>=:depature AND o.id = r.id) and r.roomPlaces>=:places");
        query.setParameter("arrival",order.getArrivalDate());
        query.setParameter("depature",order.getDepartureDate());
        query.setParameter("places",order.getPlacesAmount());
        List<Room> rooms = query.list();
        return rooms;*/
        return jdbcTemplate.execute(SHOW_FREE_ROOMS, (PreparedStatementCallback<List<Room>>) ps -> {

            ps.setDate(1, (Date) order.getArrivalDate());
            ps.setDate(2, (Date) order.getDepartureDate());
            ps.setInt(3, order.getPlacesAmount());

            List<Room> rooms = new ArrayList<>();
            ResultSet rs = null;
            rs = ps.executeQuery();
            while (rs.next()) {
                Room room = new Room();

                room.setId(rs.getLong("r_id"));
                room.setRoomNumber(rs.getByte("room_number"));
                room.setRoomPlaces(rs.getByte("room_places"));
                room.setPrice(rs.getBigDecimal("price"));

                rooms.add(room);
            }
            return rooms;
        });
    }

    private final class RoomMapper implements RowMapper<Room> {
        @Override
        public Room mapRow(ResultSet rs, int rowNum) throws SQLException {
            Room room = new Room();

            room.setId(rs.getLong("r_id"));
            room.setRoomNumber(rs.getByte("room_number"));
            room.setRoomPlaces(rs.getByte("room_places"));
            room.setPrice(rs.getBigDecimal("price"));
            return room;
        }

    }


}
