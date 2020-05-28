package com.selwantech.raheeb.repository;


import com.selwantech.raheeb.App;
import com.selwantech.raheeb.repository.db.database.LogDatabase;
import com.selwantech.raheeb.repository.network.services.AuthService;
import com.selwantech.raheeb.repository.network.services.CategoryService;
import com.selwantech.raheeb.repository.network.services.DataExampleService;
import com.selwantech.raheeb.repository.network.services.ProductService;

import javax.inject.Singleton;

/**
 * Private Instance @Singleton Class That uses @Room for Data Management Purpose.
 * This is like the RoomDatabase Base Class to construct a room database instance.
 */
@Singleton
public class DataManager {

    private static DataManager sInstance;

    private DataManager() {
        // This class is not publicly instantiable
    }

    public static synchronized DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }


    public LogDatabase getLogDatabse() {
        return LogDatabase.getInstance(App.getInstance());
    }

    public DataExampleService getDataService() {
        return DataExampleService.getInstance();
    }

    public AuthService getAuthService() {
        return AuthService.getInstance();
    }

    public ProductService getProductService() {
        return ProductService.getInstance();
    }

    public CategoryService getCategoryService() {
        return CategoryService.getInstance();
    }
}
