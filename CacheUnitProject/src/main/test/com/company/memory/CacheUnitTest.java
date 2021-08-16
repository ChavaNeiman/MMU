package main.test.com.company.memory;

import com.company.algorithm.LRUAlgoCacheImpl;
import main.java.com.company.dao.DaoFileImpl;
import main.java.com.company.dm.DataModel;
import main.java.com.company.memory.CacheUnit;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class CacheUnitTest {

    public CacheUnitTest(){}

    /**
     * test the CacheUnit
     */
    @Test
    public void testCacheUnit(){
        LRUAlgoCacheImpl<Long, DataModel<String>> lruAlgo=new LRUAlgoCacheImpl<>(5);
        CacheUnit<String> cache=new CacheUnit<>(lruAlgo);
        DataModel<String>[] dataModels = new DataModel[6];
        dataModels[0]=new DataModel<String>(0L,"a");
        dataModels[1]=new DataModel<String>(1L,"b");
        dataModels[2]=new DataModel<String>(2L,"c");
        dataModels[3]=new DataModel<String>(3L,"d");
        dataModels[4]=new DataModel<String>(4L,"e");
        dataModels[5]=new DataModel<String>(5L,"f");

        DataModel<String>[] pagedOutModels=cache.putDataModels(dataModels);
        Assert.assertNull(pagedOutModels[0]);
        Assert.assertNull(pagedOutModels[1]);
        Assert.assertNull(pagedOutModels[2]);
        Assert.assertNull(pagedOutModels[3]);
        Assert.assertNull(pagedOutModels[4]);
        Assert.assertEquals(pagedOutModels[5],dataModels[0]);
    }

    /**
     * test the DataModel
     */
    @Test
    public void testDataModel(){
        DataModel<String> dataModel=new DataModel<String>(0L,"a");
        DataModel<String> dataModel1=new DataModel<String>(1L,"b");
        Assert.assertEquals(dataModel.getDataModelId().intValue(),0);
        Assert.assertEquals(dataModel.getContent(),"a");
        Assert.assertNotEquals(dataModel, dataModel1);
        DataModel<String> dm3=new DataModel<String>(1L,"b");
        Assert.assertEquals(dataModel1,dm3);
    }

    /**
     * test the DaoFileImpl
     */
    @Test
    public void testDaoFileImpl() throws IOException {
        DaoFileImpl<String> daoFile=new DaoFileImpl<>("src/main/java/com/company/resources/datasource.json",20);
        DataModel<String> dataModel=new DataModel<String>(2L,"aa");
        DataModel<String> dataModel1=new DataModel<String>(0L,"a");
        DataModel<String> dataModel2=new DataModel<String>(3L,"bb");
        daoFile.save(dataModel);
        daoFile.save(dataModel2);
        daoFile.save(dataModel1);
        Assert.assertEquals(daoFile.find(0L),dataModel1);
        daoFile.delete(dataModel1);
    }

    /**
     * test the whole the system
     */
    @Test
    public void CacheUnitTest() throws IOException {
        LRUAlgoCacheImpl<Long, DataModel<String>> algo=new LRUAlgoCacheImpl<>(5);
        CacheUnit<String> cacheUnit=new CacheUnit<>(algo);
        DaoFileImpl<String> daoFile=new DaoFileImpl<>("src/main/resources/datasource.json",20);
        Long[] ids =new Long[2];
        ids[0]=0L;
        ids[1]=1L;

        DataModel<String>[] dm = new DataModel[6];
        dm[0]=new DataModel<String>(0L,"a");
        dm[1]=new DataModel<String>(1L,"b");
        dm[2]=new DataModel<String>(2L,"c");
        dm[3]=new DataModel<String>(3L,"d");
        dm[4]=new DataModel<String>(4L,"e");
        dm[5]=new DataModel<String>(5L,"f");

        if(cacheUnit.getDataModels(ids)[0]==null && cacheUnit.getDataModels(ids)[1]==null){
            cacheUnit.putDataModels(dm);
            for (DataModel<String> stringDataModel : dm) {
                daoFile.save(stringDataModel);
            }
        }

        Assert.assertEquals(cacheUnit.getDataModels(ids)[1],dm[1]);
        Assert.assertNull(cacheUnit.getDataModels(ids)[0]);

        ids[0]=0L;
        ids[1]=5L;

        DataModel<String>[] dm2 = new DataModel[1];
        dm2[0]=daoFile.find(0L);
        daoFile.save(cacheUnit.putDataModels(dm2)[0]);
    }
}
