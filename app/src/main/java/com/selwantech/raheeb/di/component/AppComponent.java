
package com.selwantech.raheeb.di.component;

import com.selwantech.raheeb.App;
import com.selwantech.raheeb.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class})
public interface AppComponent {

    void inject(App app);

}
