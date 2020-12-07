package models;

import org.apache.commons.dbutils.BeanProcessor;

public class BaseModel {
    private static BeanProcessor processor;

    static {
        processor = new BeanProcessor();
    }

    public static BeanProcessor getProcessor(){
        return processor;
    }
}
