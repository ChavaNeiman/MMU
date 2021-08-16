package main.java.com.company.driver;

import main.java.com.company.client.CacheUnitClientObserver;
import main.java.com.company.view.CacheUnitView;

public class CacheUnitClientDriver {
    public static void main(String[] args) {
        CacheUnitClientObserver cacheUnitClientObserver = new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();
    }
}
