package com.diaoyn.generator.querys;

import com.diaoyn.generator.IDbQuery;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author diaoyn
 * @ClassName AbstractDbQuery
 * @Date 2024/9/6 10:27
 */
public abstract class AbstractDbQuery implements IDbQuery {


    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }

    @Override
    public String[] fieldCustom() {
        return null;
    }
}
