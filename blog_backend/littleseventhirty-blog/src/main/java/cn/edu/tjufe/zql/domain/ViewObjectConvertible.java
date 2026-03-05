package cn.edu.tjufe.zql.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.function.Consumer;

/**
 * @author: littleseventhirty
 * @description: 完成对dto向vo转换功能的接口
 * @date: 2025/12/15-16:37
 **/
public interface ViewObjectConvertible {
    /**
     * 将对应实体转为另外一种类型的实体，同时提供一个有输入无输出的实体操作回调函数兜底
     *
     * @param clazz    目标转换实体类型
     * @param consumer 准换实体后需要进行的后续操作
     * @param <V>
     * @return 返回目标被操作过后的实体
     */
    default <V> V asViewObject(Class<V> clazz, Consumer<V> consumer) {
        V v = this.asViewObject(clazz);
        consumer.accept(v);
        return v;
    }

    /**
     * 只完成将源实体的字段数据按照和目标实体等价的字段进行赋值
     *
     * @param clazz 目标实体类型
     * @param <V>
     * @return 目标实体
     */
    default <V> V asViewObject(Class<V> clazz) {
        try {
            // 完成获取字段属性，获得构造器创建实体，由源dto数据向vo数据进行转换
            Field[] voFields = clazz.getDeclaredFields();
            Constructor<V> voConstructor = clazz.getConstructor();
            V vo = voConstructor.newInstance();
            Arrays.asList(voFields).forEach(field -> convert(field, vo));
            return vo;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
                 IllegalAccessException e) {
            Logger log = LoggerFactory.getLogger(ViewObjectConvertible.class);
            log.error("在VO向DTO转换时出现了一些错误，", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    private void convert(Field field, Object target) {
        try {
            Field sourceField = this.getClass().getDeclaredField(field.getName());

            sourceField.setAccessible(true);
            field.setAccessible(true);
            field.set(target, sourceField.get(this));
        } catch (NoSuchFieldException e) {
            // 【关键改动】如果 Entity 里没这个字段，直接跳过，不抛出异常
            // 这样 VO 中多出的字段可以留给后续的 Consumer 回调手动处理
            return;
        }catch (IllegalAccessException e) {
            throw new RuntimeException("字段访问权限异常: " + field.getName(), e);
        }
    }
}
