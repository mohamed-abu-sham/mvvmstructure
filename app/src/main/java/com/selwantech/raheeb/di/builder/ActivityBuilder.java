
package com.selwantech.raheeb.di.builder;


import com.selwantech.raheeb.ui.emptyactivity.EmptyActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {


    @ContributesAndroidInjector
    abstract EmptyActivity bindLoginActivity();

//    @ContributesAndroidInjector(modules = {
//            AboutFragmentProvider.class,
//            RateUsDialogProvider.class})
//    abstract MainActivity bindMainActivity();

}
