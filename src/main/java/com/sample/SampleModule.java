package com.sample;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * @ClassName SampleModule
 * @Description a sample to test
 * @Author nixin
 * @Date 2020/3/1
 * @Version 1.0.0
 **/
public class SampleModule implements Module {
    @Override
    public void configure(Binder binder) {
        binder.bind(Sample.class).asEagerSingleton();
    }
}
