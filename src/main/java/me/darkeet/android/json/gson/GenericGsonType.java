package me.darkeet.android.json.gson;

import java.lang.reflect.Type;
import java.lang.reflect.ParameterizedType;

/**
 * Name: Generic
 * User: Lee (darkeet.me@gmail.com)
 * Date: 2015/10/20 17:18
 * Desc: 用于泛型反向序列化Gson字符串
 *       参考：http://stackoverflow.com/questions/14139437/java-type-generic-as-argument-for-gson
 */
public class GenericGsonType<X, Y> implements ParameterizedType {

    private final Class<X> container;
    private final Class<Y> wrapped;

    public GenericGsonType(Class<X> container, Class<Y> wrapped) {
        this.container = container;
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[]{wrapped};
    }

    public Type getRawType() {
        return container;
    }

    public Type getOwnerType() {
        return null;
    }
}
