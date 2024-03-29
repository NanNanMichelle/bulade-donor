package com.bulade.donor.framework.mybatis.typehandler;

import com.bulade.donor.common.annotation.EnumValue;
import com.bulade.donor.common.enums.IEnum;
import com.bulade.donor.common.exception.BusinessException;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaClass;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.invoker.Invoker;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义枚举属性转换器
 */
public class MybatisEnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private static final Map<String, String> TABLE_METHOD_OF_ENUM_TYPES = new ConcurrentHashMap<>();

    private static final ReflectorFactory REFLECTOR_FACTORY = new DefaultReflectorFactory();

    private final Class<E> enumClassType;

    private final Class<?> propertyType;

    private final Invoker getInvoker;

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_MAP = new IdentityHashMap<>(8);

    private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_TO_WRAPPER_MAP = new IdentityHashMap<>(8);

    static {
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Boolean.class, boolean.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Byte.class, byte.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Character.class, char.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Double.class, double.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Float.class, float.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Integer.class, int.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Long.class, long.class);
        PRIMITIVE_WRAPPER_TYPE_MAP.put(Short.class, short.class);
        for (Map.Entry<Class<?>, Class<?>> entry : PRIMITIVE_WRAPPER_TYPE_MAP.entrySet()) {
            PRIMITIVE_TYPE_TO_WRAPPER_MAP.put(entry.getValue(), entry.getKey());
        }
    }

    public MybatisEnumTypeHandler(Class<E> enumClassType) {
        if (enumClassType == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.enumClassType = enumClassType;
        MetaClass metaClass = MetaClass.forClass(enumClassType, REFLECTOR_FACTORY);
        String name = "value";
        if (!IEnum.class.isAssignableFrom(enumClassType)) {
            name = findEnumValueFieldName(this.enumClassType)
                .orElseThrow(() -> new IllegalArgumentException(
                    String.format("Could not find @EnumValue in Class: %s.", this.enumClassType.getName()))
                );
        }
        this.propertyType = resolvePrimitiveIfNecessary(metaClass.getGetterType(name));
        this.getInvoker = metaClass.getGetInvoker(name);
    }

    private static Class<?> resolvePrimitiveIfNecessary(Class<?> clazz) {
        return (clazz.isPrimitive() && clazz != void.class ? PRIMITIVE_TYPE_TO_WRAPPER_MAP.get(clazz) : clazz);
    }

    /**
     * 查找标记标记EnumValue字段
     *
     * @param clazz class
     * @return EnumValue字段
     * @since 3.3.1
     */
    public static Optional<String> findEnumValueFieldName(Class<?> clazz) {
        if (clazz != null && clazz.isEnum()) {
            String className = clazz.getName();
            return Optional.ofNullable(TABLE_METHOD_OF_ENUM_TYPES.computeIfAbsent(className, key -> {
                Optional<Field> fieldOptional = findEnumValueAnnotationField(clazz);
                return fieldOptional.map(Field::getName).orElse(null);
            }));
        }
        return Optional.empty();
    }

    private static Optional<Field> findEnumValueAnnotationField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.isAnnotationPresent(EnumValue.class))
            .findFirst();
    }

    /**
     * 判断是否为MP枚举处理
     *
     * @param clazz class
     * @return 是否为MP枚举处理
     * @since 3.3.1
     */
    public static boolean isMpEnums(Class<?> clazz) {
        return clazz != null
            && clazz.isEnum()
            && (IEnum.class.isAssignableFrom(clazz) || findEnumValueFieldName(clazz).isPresent());
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType)
        throws SQLException {
        if (jdbcType == null) {
            ps.setObject(i, this.getValue(parameter));
        } else {
            // see r3589
            ps.setObject(i, this.getValue(parameter), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object value = rs.getObject(columnName, this.propertyType);
        if (null == value || rs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object value = rs.getObject(columnIndex, this.propertyType);
        if (null == value || rs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object value = cs.getObject(columnIndex, this.propertyType);
        if (null == value || cs.wasNull()) {
            return null;
        }
        return this.valueOf(value);
    }

    private E valueOf(Object value) {
        E[] es = this.enumClassType.getEnumConstants();
        return Arrays.stream(es).filter(e -> equalsValue(value, getValue(e))).findAny().orElse(null);
    }

    /**
     * 值比较
     *
     * @param sourceValue 数据库字段值
     * @param targetValue 当前枚举属性值
     * @return 是否匹配
     * @since 3.3.0
     */
    protected boolean equalsValue(Object sourceValue, Object targetValue) {
        String sValue = String.valueOf(sourceValue).trim();
        String tValue = String.valueOf(targetValue).trim();
        if (sourceValue instanceof Number && targetValue instanceof Number
            && new BigDecimal(sValue).compareTo(new BigDecimal(tValue)) == 0) {
            return true;
        }
        return Objects.equals(sValue, tValue);
    }

    private Object getValue(Object object) {
        try {
            return this.getInvoker.invoke(object, new Object[0]);
        } catch (ReflectiveOperationException e) {
            throw new BusinessException("获取value失败", e);
        }
    }
}
