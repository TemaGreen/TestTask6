package com.example.controller;

import com.example.component.adapter.FileAdapterXML;
import com.example.component.calculator.CrowflightCalculator;
import com.example.component.calculator.DixtraCalculator;
import com.example.dao.CityDAO;
import com.example.dao.DistanceDAO;
import com.example.dto.CalculationTypes;
import com.example.dto.RequestDistanceCalculate;
import com.example.entity.City;
import com.example.entity.Distance;
import com.example.exception.NoWayToCity;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Path("/distance")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class MainController {

    @GET
    @Path("/getAll")
    public List<City> getListCities() {
        return new CityDAO().findAll();
    }

    @POST
    @Path("/calculateDistance")
    public List<Double> calculateDistance(RequestDistanceCalculate requestDistanceCalculate) {
        List<Double> result = new LinkedList<>();
        CityDAO dao = new CityDAO();
        City c1 = dao.find(new City(requestDistanceCalculate.getFromcity()));
        City c2 = dao.find(new City(requestDistanceCalculate.getTocity()));
        if (requestDistanceCalculate.getType() != CalculationTypes.Crowflight) {
            try {
                result.add(new DixtraCalculator().calculateDistance(c1, c2));
            } catch (NoWayToCity ex) {
                ex.printStackTrace();
                result.add(-1.0);
            }
        }
        if (requestDistanceCalculate.getType() != CalculationTypes.DistanceMatrix) {
            result.add(new CrowflightCalculator().calculateDistance(c1, c2));
        }
        return result;
    }

    @POST
    @Path("/uploadFileXML")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MultipartFormDataInput multipartFormDataInput) {
        Map<String, List<InputPart>> uploadForm = multipartFormDataInput.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");
        DistanceDAO dao = new DistanceDAO();
        for (InputPart inputPart : inputParts) {
            try {
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                List<Distance> distances = FileAdapterXML.convertToXML(inputStream);
                dao.updateAndInsertAll(distances);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Response.status(200).entity("OK").build();
    }
}
