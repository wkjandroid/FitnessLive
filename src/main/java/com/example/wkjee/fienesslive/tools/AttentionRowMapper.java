package com.example.wkjee.fienesslive.tools;

import com.example.wkjee.fienesslive.manager.domain.Attention;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wkj_pc on 2017/8/20.
 */
class AttentionRowMapperpublic implements RowMapper {
    /**attnetion表的 sql的行映射*/
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Attention attention=new Attention();
        attention.setGzid(rs.getInt("gz_id"));
        attention.setGzaccount(rs.getString("f_account"));
        attention.setGznickname(rs.getString("f_nickname"));
        attention.setGzphonenumber(rs.getString("f_phonenum"));
        attention.setGzamatar(rs.getString("f_amatar"));
        return attention;
    }
}
