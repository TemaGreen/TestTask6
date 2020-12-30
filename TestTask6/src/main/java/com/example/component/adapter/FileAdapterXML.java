package com.example.component.adapter;

import com.example.dto.Distances;
import com.example.entity.Distance;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.InputStream;
import java.util.List;

public class FileAdapterXML {

    public static List<Distance> convertToXML(File file) {
        try {
            JAXBContext context = JAXBContext.newInstance(Distances.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Distances dto = (Distances) unmarshaller.unmarshal(file);
            return dto.getDistances();
        } catch (JAXBException ex) {
            System.err.println(ex);
        }
        return null;
    }
    public static List<Distance> convertToXML(InputStream in) {
        try {
            JAXBContext context = JAXBContext.newInstance(Distances.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            Distances dto = (Distances) unmarshaller.unmarshal(in);
            return dto.getDistances();
        } catch (JAXBException ex) {
            System.err.println(ex);
        }
        return null;
    }

    public static void convertToFile(List<Distance> list, String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(Distances.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(new Distances(list), new File(filename));
        } catch (JAXBException ex) {
            System.err.println(ex);
        }
    }
}
