package main.java.com.company.client;

import main.java.com.company.view.CacheUnitView;
import java.beans.PropertyChangeListener;
import java.util.Objects;


public class CacheUnitClientObserver implements PropertyChangeListener
{
    private final CacheUnitClient cacheUnitClient;
    private CacheUnitView cacheUnitView;


    public CacheUnitClientObserver() {
        cacheUnitClient = new CacheUnitClient();
    }
    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt)
    {
        String res = null;
        cacheUnitView = (CacheUnitView) evt.getSource();
        try
        {
            res = cacheUnitClient.send(evt.getNewValue().toString());
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        cacheUnitView.updateUIData(Objects.requireNonNull(res));
    }
}
