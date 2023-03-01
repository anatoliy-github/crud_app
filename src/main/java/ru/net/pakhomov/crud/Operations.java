package ru.net.pakhomov.crud;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.net.pakhomov.objects.Person;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class Operations {
    private final String filePath = "src/main/resources/db.json";
    private ObjectMapper mapper = new ObjectMapper();
    File file = new File(filePath);
    public void save(Person person) {
        Map<Integer, Person> map = getMapObjects();
        int currentObjId = person.getId();
        if(!map.containsKey(currentObjId)) {
            map.put(currentObjId, person);
            saveMapToFile(map);
            System.out.println(person + " saved successfully");
        } else {
            System.out.println("An object with ID=" + currentObjId + " already exists." +
                    "Are you sure you want to overwrite the object? y / n");
            Scanner scanner = new Scanner(System.in);
            String answer = scanner.next();
            boolean tryAgain = true;
            do {
                if("y".equals(answer)) {
                    tryAgain = false;
                    update(person);
                }
                if("n".equals(answer)) {
                    tryAgain = false;
                }
            } while(tryAgain);
        }
    }

    public Person getById(int id) {
        Map<Integer, Person> map = getMapObjects();
        if(map.containsKey(id)) {
            return map.get(id);
        }
        throw new IllegalArgumentException("Wrong id");
    }

    public void update(Person person) {
        Map<Integer, Person> map = getMapObjects();
        int newId = person.getId();
        if(!map.containsKey(newId)) {
            map.remove(newId);
        }
        map.put(person.getId(), person);
        saveMapToFile(map);
    }

    public void delete(int id) {
        Map<Integer, Person> map = getMapObjects();
        map.remove(id);
        saveMapToFile(map);
    }

    private Map<Integer, Person> getMapObjects() {
        ObjectMapper mapper = getMapper();
        Map<Integer, Person> map = null;
        try {
            map = mapper.readValue(file, new TypeReference<>(){});
        } catch (IOException e) {
            System.out.println("File access error: " + e);
        }
        return map;
    }

    private void saveMapToFile(Map<Integer, Person> map) {
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(filePath);
                writer.write("{}");
                writer.close();
            } catch (IOException e) {
                System.out.println("Error creation file " + e);
            }
        }
        ObjectMapper mapper = getMapper();
        try {
            mapper.writeValue(file, map);
        } catch (IOException e) {
            System.out.println("File access error: " + e);
        }
    }

    private ObjectMapper getMapper() {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        return mapper;
    }

}
