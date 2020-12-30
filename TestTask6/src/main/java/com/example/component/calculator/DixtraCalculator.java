package com.example.component.calculator;

import com.example.dao.CityDAO;
import com.example.dao.DistanceDAO;
import com.example.entity.City;
import com.example.entity.Distance;
import com.example.exception.NoWayToCity;

import java.util.*;

public class DixtraCalculator implements Calculator {
    @Override
    public double calculateDistance(City fromCity, City toCity) throws NoWayToCity{
        init(fromCity, toCity);
        double result = calculate();
        if(result == Double.MAX_VALUE){
            throw new NoWayToCity(fromCity.getName(), toCity.getName());
        } else {
            return result;
        }
    }

    List<Node> nodes = new LinkedList<>();
    Node startNode, finalNode;

    private void init(City fromCity, City toCity) {
        List<City> cities = new CityDAO().findAll();
        for (City city : cities) {
            nodes.add(new Node(city));
        }
        List<Distance> distances = new DistanceDAO().findAll();
        for (Distance distance : distances) {
            int i = cities.indexOf(distance.getFromCity());
            int j = cities.indexOf(distance.getToCity());
            nodes.get(i).addNeighbor(nodes.get(j), (double) distance.getDistance());
        }
        startNode = nodes.get(cities.indexOf(fromCity));
        finalNode = nodes.get(cities.indexOf(toCity));
        startNode.setDistance(0.0);
    }

    private double calculate() {
        Set<Node> markerNodes = new HashSet<>();
        Set<Node> unmarkerNodes = new HashSet<>();
        unmarkerNodes.add(startNode);
        while (!unmarkerNodes.isEmpty()) {
            Node minNode = getNodeMinDistance(unmarkerNodes);
            unmarkerNodes.remove(minNode);
            for (Map.Entry<Node, Double> entry : minNode.getNeighboringNodes().entrySet()) {
                Node neighbor = entry.getKey();
                Double distance = entry.getValue();
                if (!markerNodes.contains(neighbor)) {
                    calculateMinimumDistance(minNode, neighbor, distance);
                    unmarkerNodes.add(neighbor);
                }
            }
            markerNodes.add(minNode);
        }
        return finalNode.getDistance();
    }

    private Node getNodeMinDistance(Set<Node> nodeSet) {
        Node result = null;
        double min = Double.MAX_VALUE;
        for (Node node : nodeSet) {
            double distance = node.getDistance();
            if (distance < min) {
                min = distance;
                result = node;
            }
        }
        return result;
    }

    private void calculateMinimumDistance(Node node, Node neighbor, Double distanceNeighbor) {
        Double nodeDistance = node.getDistance();
        Double sum = nodeDistance + distanceNeighbor;
        if (sum < neighbor.getDistance()) {
            neighbor.setDistance(sum);
        }
    }
}

class Node{
    private City city;
    private Double distance = Double.MAX_VALUE;
    Map<Node, Double> neighboringNodes = new HashMap<>();

    public void addNeighbor(Node neighbor, Double distance) {
        neighboringNodes.put(neighbor, distance);
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Map<Node, Double> getNeighboringNodes() {
        return neighboringNodes;
    }

    public void setNeighboringNodes(Map<Node, Double> neighboringNodes) {
        this.neighboringNodes = neighboringNodes;
    }

    public Node(City city) {
        this.city = city;
    }
}

