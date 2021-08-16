package main.java.com.company.memory;


import com.company.algorithm.IAlgoCache;
import main.java.com.company.dm.DataModel;

/**
 * This class is in charge of the memory.
 * @param <T>
 */
public class CacheUnit<T> {

    private final IAlgoCache<Long, DataModel<T>> algo;

    public CacheUnit(IAlgoCache<Long,DataModel<T>> algorithm)
    {
        super();
        this.algo =  algorithm;
    }

    /**
     * @param ids - a list of dataModel ids.
     * @return DataModel according to the ids received
     */
    public DataModel<T>[] getDataModels(java.lang.Long[] ids)
    {
        int  idSize = ids.length;
        DataModel<T>[] dataModel = new DataModel[idSize];
        for(int i =0; i < idSize; i++)
        {
            dataModel[i] = algo.getElement(ids[i]);
        }
        return dataModel;
    }

    /**
     * this method sets the memory according to the datamodels
     * @param dataModels - an array to be put into memory.
     * @return an array of the values that were paged out of the memory.
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] dataModels)
    {
        int numOfPagedOut  = 0;
        int dmSize = dataModels.length;
        DataModel<T>[] dataModel = new DataModel[dmSize];
        for (DataModel<T> model : dataModels) {
            DataModel<T> temp = null;
            temp = algo.putElement(model.getDataModelId(), model);
            if (temp != null)
                dataModel[numOfPagedOut++] = new DataModel<T>(temp.getDataModelId(), temp.getContent());
        }
        return dataModel;
    }

    /**
     * removes ids received from the DataModels.
     * @param ids - of models to be removed from DataModels
     */
    public void removeDataModels(Long[] ids) {
        for (Long id : ids) {
            algo.removeElement(id);
        }
    }

}