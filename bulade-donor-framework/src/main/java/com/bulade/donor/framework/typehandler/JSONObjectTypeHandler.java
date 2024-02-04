package com.bulade.donor.framework.typehandler;

import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yl.meng
 * @date 2023/6/14
 */
@Component
public class JSONObjectTypeHandler extends BaseTypeHandler<JSONObject> {

    @Override
    public void setNonNullParameter(
        PreparedStatement ps,
        int i,
        JSONObject parameter,
        JdbcType jdbcType
    ) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.toJSONString());
        } else {
            ps.setObject(i, parameter.toJSONString(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : JSONObject.parseObject(s);
    }

    @Override
    public JSONObject getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : JSONObject.parseObject(s);
    }

    @Override
    public JSONObject getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : JSONObject.parseObject(s);
    }
}
