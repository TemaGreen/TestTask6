package com.example.dao;

import com.example.component.ConnectManager;
import com.example.entity.City;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CityDAO implements DataAccessObject<City> {
    public void insert(City value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "INSERT INTO city(" +
                            "name, " +
                            "latitude" +
                            "longitude" +
                            ") VALUES (?,?,?);"
            );
            preparedStatement.setString(1, value.getName());
            preparedStatement.setDouble(2, value.getLatitude());
            preparedStatement.setDouble(3, value.getLongitude());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void insertAll(List<City> value) {
        try {
            int i = value.size();
            Connection connect = ConnectManager.getConnection();
            String sqlRequest = "INSERT INTO city(name, latitude, longitude) VALUES";
            while (i > 1) {
                sqlRequest += " (?,?,?),";
                i--;
            }
            sqlRequest += " (?,?,?);";
            PreparedStatement preparedStatement = connect.prepareStatement(sqlRequest);
            ListIterator<City> listIterator = value.listIterator();
            while (listIterator.hasNext()) {
                City city = listIterator.next();
                preparedStatement.setString(i++, city.getName());
                preparedStatement.setDouble(i++, city.getLatitude());
                preparedStatement.setDouble(i++, city.getLongitude());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void delete(City value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM city WHERE name = ?;"
            );
            preparedStatement.setString(1, value.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void deleteAll() {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "DELETE FROM city;"
            );
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public void update(City old, City value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "UPDATE city SET name = ?, latitude = ?, longitude = ? WHERE name = ?;"
            );
            preparedStatement.setString(1, value.getName());
            preparedStatement.setDouble(2, value.getLatitude());
            preparedStatement.setDouble(3, value.getLongitude());
            preparedStatement.setString(4, old.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    @Override
    public void updateAndInsertAll(List<City> value) {
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT name FROM city",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            Set<String> setNameCities = new HashSet<>();
            for (int i = 1; i <= count; i++) {
                resultSet.next();
                setNameCities.add(
                        resultSet.getString(1)
                );
            }
            List<City> cities = new LinkedList<>();
            preparedStatement = connect.prepareStatement(
                    "UPDATE city SET latitude = ?, longitude = ? WHERE name = ?;"
            );
            for (City city : value) {
                if (setNameCities.contains(city.getName())) {
                    preparedStatement.setDouble(1, city.getLatitude());
                    preparedStatement.setDouble(2, city.getLongitude());
                    preparedStatement.setString(3, city.getName());
                    preparedStatement.executeUpdate();
                } else {
                    cities.add(city);
                }
            }
            int i = value.size();
            String sqlRequest = "INSERT INTO city(name, latitude, longitude) VALUES";
            while (i > 1) {
                sqlRequest += " (?,?,?),";
                i--;
            }
            sqlRequest += " (?,?,?);";
            preparedStatement = connect.prepareStatement(sqlRequest);
            ListIterator<City> listIterator = value.listIterator();
            while (listIterator.hasNext()) {
                City city = listIterator.next();
                preparedStatement.setString(i++, city.getName());
                preparedStatement.setDouble(i++, city.getLatitude());
                preparedStatement.setDouble(i++, city.getLongitude());
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.getSQLState();
        }
    }

    public City find(City value) {
        City city = null;
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    " SELECT name, latitude, longitude FROM city WHERE name = ?;",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            preparedStatement.setString(1, value.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            city = new City(
                    resultSet.getString(1),
                    resultSet.getDouble(2),
                    resultSet.getDouble(3)
            );
        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            return city;
        }
    }

    public List<City> findAll() {
        List<City> result = new LinkedList<>();
        try {
            Connection connect = ConnectManager.getConnection();
            PreparedStatement preparedStatement = connect.prepareStatement(
                    "SELECT name, latitude, longitude FROM city ORDER BY name ASC",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.last();
            int count = resultSet.getRow();
            resultSet.beforeFirst();
            City city;
            for (int i = 1; i <= count; i++) {
                resultSet.next();
                city = new City(
                        resultSet.getString(1),
                        resultSet.getDouble(2),
                        resultSet.getDouble(3)
                );
                result.add(city);
            }
        } catch (SQLException ex) {
            ex.getSQLState();
        } finally {
            return result;
        }
    }
}