package com.example.dbperf.typehandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.ibatis.type.ClobTypeHandler;
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
 * このクラスはClobTypeHandlerを拡張し、読み取り処理とOracle JDBC以外の書き込み処理は
 * 標準実装を利用しつつ、Oracle JDBCの場合だけCLOB用APIを優先して使用する。
 * これにより、LONGではなくCLOBとしてバインドさせる目的で作成した。
 */
@MappedTypes(String.class)
@MappedJdbcTypes(JdbcType.CLOB)
public class OracleClobStringTypeHandler extends ClobTypeHandler {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        // Oracle JDBCではCLOB専用APIを使い、SQL中のバインド変数がLONG扱いになることを避ける。
        if (ps.isWrapperFor(OraclePreparedStatement.class)) {
            ps.unwrap(OraclePreparedStatement.class).setStringForClob(i, parameter);
            return;
        }

        // OraclePreparedStatementとしてunwrapできない場合は、MyBatis標準のCLOB処理に委譲する。
        super.setNonNullParameter(ps, i, parameter, jdbcType);
    }
}
