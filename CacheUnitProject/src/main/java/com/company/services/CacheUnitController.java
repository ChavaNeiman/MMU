package main.java.com.company.services;


import main.java.com.company.dm.DataModel;

import java.io.FileNotFoundException;

public class CacheUnitController<T> {

    CacheUnitService<T> cacheUnitService;

    public CacheUnitController() {

        cacheUnitService =new CacheUnitService<T>();
    }

    public boolean update(DataModel<T>[] dataModels) {
        return cacheUnitService.update(dataModels);
    }

    public boolean delete(DataModel<T>[] dataModels) {
        return cacheUnitService.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) throws FileNotFoundException {
        return cacheUnitService.get(dataModels);
    }

    public String getStats() {
        return cacheUnitService.showStats();
    }

}
