
package com.selwantech.raheeb.di.module;

import android.app.Application;
import android.content.Context;

import com.selwantech.raheeb.repository.DataManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }


    @Provides
    @Singleton
    DataManager provideDataManager(DataManager appDataManager) {
        return appDataManager;
    }

}
