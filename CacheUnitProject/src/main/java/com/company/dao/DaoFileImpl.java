package main.java.com.company.dao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.java.com.company.dm.DataModel;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    private final String filePath;
    private final int capacity;
    private Map<Long, DataModel<T>> map;

    /**
     * Ctor - initializes the fields of the class,
     * fields that weren't received were set to default values
     *
     * @param filePath to be set in the filepath member of the class.
     */
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        capacity = 1000;
        map = new HashMap<Long, DataModel<T>>(capacity);
    }

    /**
     * Ctor - initializes the fields of the class.
     *
     * @param filePath to be set in the filepath member of the class.
     * @param capacity to be set in the capacity member of the class.
     */
    public DaoFileImpl(String filePath, int capacity) {
        this.filePath = filePath;
        this.capacity = capacity;
        map = new HashMap<Long, DataModel<T>>(this.capacity);
    }

    /**
     * Saves a given entity.
     *
     * @param entity - given entity.
     */
    @Override
    public void save(DataModel<T> entity) {
        try {
            readFromFile();
            if (map.size() >= capacity) {
                System.out.printf("Memory is full, cannot add entity: %s%n", entity.toString());
                return;
            }
            if (entity != null && entity != find(entity.getDataModelId())) {
                map.put(entity.getDataModelId(), entity);
            }
            writeToFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes a given entity.
     *
     * @param entity - given entity.
     * @throws IOException
     * @throws IllegalArgumentException - if entity is null.
     */
    @Override
    public void delete(DataModel<T> entity) throws IOException, IllegalArgumentException {
        if (entity == null) throw new IllegalArgumentException("Entity cant be null");
        readFromFile();
        map.remove(entity.getDataModelId());
        writeToFile();
    }

    /**
     * Retrieves an entity by its id.
     *
     * @param aLong
     * @return The entity with the given id(aLong) or null if none found.
     * @throws FileNotFoundException
     * @throws IllegalArgumentException - if id is null.
     */
    @Override
    public DataModel<T> find(Long aLong) throws FileNotFoundException, IllegalArgumentException {
        if (aLong == null) throw new IllegalArgumentException("Id cant be null.");
        readFromFile();
        return map.getOrDefault(aLong, null);
    }

    /**
     * writes the values of the map member to file(according to the filepath).
     *
     * @throws FileNotFoundException
     */
    private void writeToFile() throws FileNotFoundException {
        File file = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(file)) {
            Gson gson = new Gson();
            String jsonEntity = gson.toJson(map);
            fileWriter.write(jsonEntity);
        } catch (FileNotFoundException exception) {
            throw exception;
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * reads the content of the file(according to the filepath) into the map
     *
     * @throws FileNotFoundException
     */
    private void readFromFile() throws FileNotFoundException {
        Type arrayType = new TypeToken<HashMap<Long, DataModel<T>>>() {
        }.getType();
        File file = new File(filePath);
        try (FileReader fileReader = new FileReader(file)) {
            Gson gson = new Gson();
            HashMap<Long, DataModel<T>> fileContent = gson.fromJson(fileReader, arrayType);
            if (fileContent != null) {
                map = fileContent;
            }
        } catch (FileNotFoundException exception) {
            throw exception;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

