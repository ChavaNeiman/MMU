package main.java.com.company.services;
import com.company.algorithm.IAlgoCache;
import com.company.algorithm.LRUAlgoCacheImpl;
import main.java.com.company.dao.DaoFileImpl;
import main.java.com.company.dao.IDao;
import main.java.com.company.dm.DataModel;
import main.java.com.company.memory.CacheUnit;

import java.io.FileNotFoundException;

public class CacheUnitService<T> extends java.lang.Object {

    private static final String Algorithm = "LRU";
    private static final Integer Capacity = 4;
    private	Integer swaps=0,requests=0, counter =0;
    IAlgoCache<Long, DataModel<T>> algo;
    CacheUnit<T> cacheUnit;
    IDao<Long, DataModel<T>> dao;

    public CacheUnitService() {
        algo = new LRUAlgoCacheImpl<Long, DataModel<T>>(Capacity);
        cacheUnit =  new CacheUnit<T>(algo);
        dao = new DaoFileImpl<>("src\\main\\resources\\datasource.json",500);
    }

    public boolean update(DataModel<T>[] dataModels) {
        try {
            DataModel<T>[] temp = cacheUnit.putDataModels(dataModels);
            for(int i=0; i < dataModels.length;i++) {
                counter++;
                if(temp[i] != null) {
                    dao.save(temp[i]);
                    swaps++;
                }
            }
            requests++;
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }
    public boolean delete(DataModel<T>[] dataModels)
    {
        try {
            int length = dataModels.length;
            Long [] dmId = new Long[length];
            for(int i =0; i < length; i++) {
                dmId[i] = dataModels[i].getDataModelId();
                T t = (T)dataModels[i].getContent();
                DataModel<T> entity = new DataModel<T>(dmId[i], t);
                dao.delete(entity);
                counter++;
            }
            requests++;
            cacheUnit.removeDataModels(dmId);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) throws FileNotFoundException {
        requests++;
        int dmSize = dataModels.length;
        Long [] dmId = new Long[dmSize];
        for(int i =0; i < dmSize; i++)
        {
            dmId[i] = dataModels[i].getDataModelId();
            counter++;
        }
        DataModel<T>[] temp = new DataModel[dataModels.length];
        temp = (DataModel<T>[]) cacheUnit.getDataModels(dmId);

        for (int i = 0; i < dataModels.length; i++) {
            if (temp[i] == null) {
                temp[i] = (DataModel<T>) dao.find(dataModels[i].getDataModelId());
            }
        }
        cacheUnit.putDataModels(temp);
        return temp;
    }

    public String showStats() {
        return Algorithm + "," + Capacity + "," + swaps + "," + requests + "," + counter;
    }

}