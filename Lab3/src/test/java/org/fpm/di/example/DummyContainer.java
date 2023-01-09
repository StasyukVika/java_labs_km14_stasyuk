package org.fpm.di.example;

import org.fpm.di.Container;

import javax.inject.Inject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class DummyContainer implements Container {
    DummyBinder binder;
    public DummyContainer(DummyBinder binder) {
        this.binder = binder;
    }

    @Override
    public <T> T getComponent(Class<T> clazz) {
        Class<T> returned;
        if ((returned = binder.getCl(clazz))!=null)
            return createInstance(returned);
        if ((returned = binder.getslov(clazz))!=null)
            return getComponent(returned);
        T obj;
        if ((obj = binder.getslov2(clazz))!=null)
            return obj;

        return null;
    }

    private <T> T createInstance(Class<T> returned) {
        for (Constructor<?> constructor: returned.getConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)){
                Object[] masiv = new Object[constructor.getParameterCount()];
                for (int i =0;i<constructor.getParameterCount();i++){
                    masiv[i] = getComponent(constructor.getParameterTypes()[i]);
                }
                try {
                    return (T) constructor.newInstance(masiv);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        try {
            return returned.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }



}
