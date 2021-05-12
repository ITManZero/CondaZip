package it.archiver.Algorithms;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public enum AlgorithmFactory {
    HuffmanCoding(HuffmanCodding.class),Zip(LZ77.class);

    private Object instance;

    AlgorithmFactory(Class classType) {
        try {
            Constructor constructor = classType.getConstructor();
            instance = constructor.newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public <T> T getInstance() {
        return (T) instance;
    }
}