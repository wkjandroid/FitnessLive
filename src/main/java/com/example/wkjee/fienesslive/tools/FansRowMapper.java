package com.example.wkjee.fienesslive.tools;

import com.example.wkjee.fienesslive.manager.domain.Fans;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wkj_pc on 2017/8/16.
 */
public class FansRowMapper implements RowMapper {
    /**fans表的 sql的行映射*/
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Fans fans=new Fans();
        fans.setFid(rs.getInt("f_id"));
        fans.setFaccount(rs.getString("f_account"));
        fans.setFnickname(rs.getString("f_nickname"));
        fans.setFphonenumber(rs.getString("f_phonenum"));
        fans.setFamatar(rs.getString("f_amatar"));
        return fans;
    }
}
