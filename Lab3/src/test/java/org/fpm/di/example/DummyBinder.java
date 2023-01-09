package org.fpm.di.example;

import org.fpm.di.Binder;

import javax.inject.Singleton;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DummyBinder implements Binder {
    private List<Class<?>> cl = new ArrayList<>();


    @Override
    public <T> void bind(Class<T> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class))
            bind(clazz, createInstance(clazz));
        else cl.add(clazz);

    }

    private <T> T createInstance(Class<T> returned) {
        try {
            return returned.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
     private Map<Class<?>,Class<?>> slov = new HashMap<>();

    @Override
    public <T> void bind(Class<T> clazz, Class<? extends T> implementation) {
        slov.put(clazz, implementation);
    }

    private Map<Class<?>,Object> slov2 = new HashMap<>();
    @Override
    public <T> void bind(Class<T> clazz, T instance) {
        slov2.put(clazz, instance);
    }

    public <T> Class<T> getCl(Class<T> clazz){
        if(cl.contains(clazz)) return clazz;
        return null;
    }

    public <T> Class<T> getslov(Class<T> clazz){
        if(slov.containsKey(clazz)) return (Class<T>) slov.get(clazz);
        return null;
    }

    public  <T> T getslov2(Class<T> clazz){
        if(slov2.containsKey(clazz)) return (T) slov2.get(clazz);
        return null;
    }
}
