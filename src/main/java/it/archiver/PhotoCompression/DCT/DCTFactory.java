package it.archiver.PhotoCompression.DCT;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public enum DCTFactory {
   SlowDCT(it.archiver.PhotoCompression.DCT.SlowDCT.class,8),
   FastDct(FastDCT.class,8);

   private Object instance;

    DCTFactory(Class classType, int BlockSize) {
        try {
            Constructor constructor = classType.getConstructor();
            instance = constructor.newInstance();
            Method method=instance.getClass().getMethod("initialize",int.class);
            method.invoke(instance,BlockSize);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public <T> T getInstance(){ return (T) instance;}

}