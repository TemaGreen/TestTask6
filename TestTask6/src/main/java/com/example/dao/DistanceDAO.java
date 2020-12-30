package com.example.dao;

import com.example.component.ConnectManager;
import com.example.dto.DistanceDTO;
import com.example.entity.City;
import com.example.entity.Distance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DistanceDAO implements DataAccessObject<Distance> {
    public void insert(Distance value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "INSERT INTO distance(" +
                            "fromcity," +
                            "tocity, " +
                            "distance, " +
                            ") VALUES (?,?,?);"
            );
            preparedStatement.setString(1, value.getFromCity().getName());
            preparedStatement.setString(2, value.getToCity().getName());
            preparedStatement.setDouble(3, value.getDistance());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void insertAll(List<Distance> value) {
        try {
            int i = value.size();
            Connection connect = ConnectManager.getConnection();
            String sqlRequest = "INSERT INTO distance(fromcity, tocity, distance) VALUES";
            while (i > 1) {
                sqlRequest += " (?,?,?),";
                i--;
            }
            sqlRequest += " (?,?,?);";
            PreparedStatement preparedStatement = connect.prepareStatement(sqlRequest);
            ListIterator<Distance> listIterator = value.listIterator();
            while (listIterator.hasNext()) {
                Distance distance = listIterator.next();
                preparedStatement.setString(i++, distance.getFromCity().getName());
                preparedStatement.setString(i++, distance.getToCity().getName());
                preparedStatement.setDouble(i++, distance.getDistance());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void delete(Distance value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM distance WHERE fromcity = ?, tocity;"
            );
            preparedStatement.setString(1, value.getFromCity().getName());
            preparedStatement.setString(2, value.getToCity().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void deleteAll() {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM distance;"
            );
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void update(Distance old, Distance value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "UPDATE distance SET fromcity = ?, tocity = ?, distance = ? " +
                            "WHERE fromcity = ?, tocity = ?;"
            );
            preparedStatement.setString(1, value.getFromCity().getName());
            preparedStatement.setString(2, value.getToCity().getName());
            preparedStatement.setDouble(3, value.getDistance());
            preparedStatement.setString(4, old.getFromCity().getName());
            preparedStatement.setString(5, old.getToCity().getName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public void updateAndInsertAll(List<Distance> value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT fromcity, tocity FROM distance",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            Set<DistanceDTO> setDistances = new HashSet<>();
            for (int i = 1; i <= count; i++) {
                resultSet.next();
                setDistances.add(new DistanceDTO(
                        resultSet.getString(1),
                        resultSet.getString(2)
                ));
            }
            List<Distance> distances = new LinkedList<>();
            preparedStatement = connect.prepareStatement(
                    "UPDATE distance SET distance = ? " +
                            "WHERE fromcity = ?, tocity = ?;"
            );
            for (Distance distance : value) {
                if (setDistances.contains(new DistanceDTO(distance.getFromCity().getName(), distance.getToCity().getName()))) {
                    preparedStatement.setDouble(1, distance.getDistance());
                    preparedStatement.setString(2, distance.getFromCity().getName());
                    preparedStatement.setString(3, distance.getToCity().getName());
                    preparedStatement.executeUpdate();
                } else {
                    distances.add(distance);
                }
            }
            int i = value.size();
            String sqlRequest = "INSERT INTO distance(fromcity, tocity, distance) VALUES";
            while (i > 1) {
                sqlRequest += " (?,?,?),";
                i--;
            }
            sqlRequest += " (?,?,?);";
            preparedStatement = connect.prepareStatement(sqlRequest);
            ListIterator<Distance> listIterator = value.listIterator();
            while (listIterator.hasNext()) {
                Distance distance = listIterator.next();
                preparedStatement.setString(i++, distance.getFromCity().getName());
                preparedStatement.setString(i++, distance.getToCity().getName());
                preparedStatement.setDouble(i++, distance.getDistance());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public Distance find(Distance value) {
        Distance distance = null;
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT " +
                            "c1.name, c1.latitude, c1.longitude, " +
                            "c2.name, c2.latitude, c2.longitude, " +
                            "d.distance " +
                            "FROM city AS c1 JOIN distance AS d ON  c1.name = d.fromcity " +
                            "JOIN city AS c2 ON c2.name = d.tocity " +
                            "WHERE c1.name = ? AND c2.name = ?",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            preparedStatement.setString(1, value.getFromCity().getName());
            preparedStatement.setString(2, value.getFromCity().getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            distance = new Distance(
                    new City(
                            resultSet.getString(1),
                            resultSet.getDouble(2),
                            resultSet.getDouble(3)
                    ),
                    new City(
                            resultSet.getString(4),
                            resultSet.getDouble(5),
                            resultSet.getDouble(6)),
                    resultSet.getDouble(7)
            );
        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            return distance;
        }
    }

    public List<Distance> findAll() {
        List<Distance> result = new LinkedList<>();
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT c1.name, c1.latitude, c1.longitude, " +
                            "c2.name, c2.latitude, c2.longitude, " +
                            "d.distance " +
                            "FROM city AS c1 JOIN distance AS d ON  c1.name = d.fromcity " +
                            "JOIN city AS c2 ON c2.name = d.tocity",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            Distance distance;
            for (int i = 1; i <= count; i++) {
                resultSet.next();
                distance = new Distance(
                        new City(
                                resultSet.getString(1),
                                resultSet.getDouble(2),
                                resultSet.getDouble(3)
                        ),
                        new City(
                                resultSet.getString(4),
                                resultSet.getDouble(5),
                                resultSet.getDouble(6)),
                        resultSet.getDouble(7)
                );
                result.add(distance);
            }
        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            return result;
        }
    }
}