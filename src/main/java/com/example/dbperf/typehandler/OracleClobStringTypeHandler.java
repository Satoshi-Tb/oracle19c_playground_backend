package com.example.dbperf.typehandler;

import java.io.StringReader;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import oracle.jdbc.OraclePreparedStatement;

/**
 * OracleのCLOB列へStringを明示的にCLOBとしてバインドするためのTypeHandler。
 * <p>
 * MyBatis標準のClobTypeHandlerはPreparedStatement#setCharacterStreamを使用する。
 * その場合、Oracle JDBCがSQL式中のバインド変数をLONGとして扱い、
 * CLOB連結時にORA-01461が発生するケースがある。
 * このクラスはOracle JDBCのCLOB用APIを優先して使用し、
 * LONGではなくCLOBとしてバインドさせる目的で作成した。
 */
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.CLOB)
public class OracleClobStringTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        // Oracle JDBCではCLOB専用APIを使い、SQL中のバインド変数がLONG扱いになることを避ける。
        if (ps.isWrapperFor(OraclePreparedStatement.class)) {
            ps.unwrap(OraclePreparedStatement.class).setStringForClob(i, parameter);
            return;
        }

        // OraclePreparedStatementとしてunwrapできない場合は、JDBC標準APIでCLOBとして渡す。
        ps.setClob(i, new StringReader(parameter), parameter.length());
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return toString(rs.getClob(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return toString(rs.getClob(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return toString(cs.getClob(columnIndex));
    }

    private String toString(Clob clob) throws SQLException {
        return clob == null ? null : clob.getSubString(1, (int) clob.length());
    }
}
