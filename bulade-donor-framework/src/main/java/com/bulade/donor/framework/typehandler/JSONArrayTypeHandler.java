package com.bulade.donor.framework.typehandler;

import com.alibaba.fastjson2.JSONArray;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author yl.meng
 * @date 2023/6/9
 */
@Component
public class JSONArrayTypeHandler extends BaseTypeHandler<JSONArray> {

    @Override
    public void setNonNullParameter(
        PreparedStatement ps,
        int i,
        JSONArray parameter,
        JdbcType jdbcType
    ) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, parameter.toJSONString());
        } else {
            ps.setObject(i, parameter.toJSONString(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String s = rs.getString(columnName);
        return s == null ? null : JSONArray.parseArray(s);
    }

    @Override
    public JSONArray getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String s = rs.getString(columnIndex);
        return s == null ? null : JSONArray.parseArray(s);
    }

    @Override
    public JSONArray getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String s = cs.getString(columnIndex);
        return s == null ? null : JSONArray.parseArray(s);
    }
}
