package org.iresearch.android.utils;

import java.util.List;
import java.util.Arrays;
import java.lang.reflect.Array;

/**
 * @className ArrayUtils
 * @create 2014年4月16日 上午11:28:24
 * @author Jacky.Lee (loveselience@gmail.com)
 * @description 封装常用的数组操作
 */
public final class ArrayUtils {

    private ArrayUtils() {
        throw new AssertionError("You are trying to create an instance for this utility class!");
    }
	
    /**
     * 将数组转换成List集合
     * @param array
     * @return
     */
	public static <T> List<T> arrayToList(T[] array) {
		if (array == null) {
			return null;
		}
		return Arrays.asList(array);
	}
	
	/**
	 * 将List集合中元素填充至数组
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] listToArray(List<T> list) {
		if (list == null) {
			return null;
		}
		Class<?> type = list.getClass().getComponentType();
		T[] result = (T[])Array.newInstance(type, list.size());
		return list.toArray(result);
	}
	
	/**
	 * 获取数组中指定范围内的元素
	 * 
	 * @param array
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] subArray(final T[] array, final int start, final int end) {
        final int length = end - start;
        if (length < 0) throw new IllegalArgumentException();
        
        Class<?> type = array.getClass().getComponentType();
        T[] result = (T[])Array.newInstance(type, length);
        System.arraycopy(array, start, result, 0, length);
        return result;
    }
	
	/**
	 * 合并两个数组中的元素
	 * 
	 * @param head
	 * @param tail
	 * @return
	 */
    @SuppressWarnings("unchecked")
    public static <T> T[] join(T[] head, T[] tail) {
        if (head == null) {
            return tail;
        }
        if (tail == null) {
            return head;
        }
        Class<?> type = head.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(type, head.length + tail.length);

        System.arraycopy(head, 0, result, 0, head.length);
        System.arraycopy(tail, 0, result, head.length, tail.length);

        return result;
    }

    /**
     * 删除数组中指定索引位置的元素
     * 
     * @param array
     * @param index
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] delete(T[] array, int index) {
        int length = array.length;
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        T[] result = (T[]) Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    /**
     * 数组中查找指定元素的索引位置
     * 
     * @param array
     * @param object
     * @return
     */
    public static <T> int indexOf(final T[] array, final T value) {
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            if (array[i] == value) return i;
        }
        return -1;
    }
    
    /**
     * 数组中是否存在指定元素
     * 
     * @param array
     * @param value
     * @return
     */
	public static <T> boolean contains(final T[] array, final T value) {
		if (array == null) return false;
		for (final T item : array) {
			if (item == value) return true;
		}
		return false;
	}

	/**
	 * 数组中是否存在指定的一组元素
	 * 
	 * @param array
	 * @param values
	 * @return
	 */
	public static <T> boolean contains(final T[] array, final T... values) {
		if (array == null || values == null) return false;
		for (final T item : array) {
			for (final T value : values) {
				if (item == null || value == null) {
					if (item == value) return true;
					continue;
				}
				if (item.equals(value)) return true;
			}
		}
		return false;
	}

	/**
	 * 比对两个数组中元素是否相同
	 * 
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static <T> boolean contentMatch(final T[] array1, final T[] array2) {
		if (array1 == null || array2 == null) return array1 == array2;
		if (array1.length != array2.length) return false;
		final int length = array1.length;
		for (int i = 0; i < length; i++) {
			if (!contains(array2, array1[i])) return false;
		}
		return true;
	}
	
	/**
	 * 用指定字符拼接数组中元素
	 * 
	 * @param array
	 * @param token
	 * @param include_space
	 * @return
	 */
	public static String toString(final Object[] array, final char token, final boolean include_space) {
        final StringBuilder builder = new StringBuilder();
        final int length = array.length;
        for (int i = 0; i < length; i++) {
            final String id_string = String.valueOf(array[i]);
            if (id_string != null) {
                if (i > 0) {
                    builder.append(include_space ? token + " " : token);
                }
                builder.append(id_string);
            }
        }
        return builder.toString();
    }
}
