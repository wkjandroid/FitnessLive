package com.example.wkjee.fienesslive.tools;

import com.example.wkjee.fienesslive.manager.domain.LiveTheme;
import com.example.wkjee.fienesslive.manager.domain.UploadVideo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by wkj on 2017/9/12.
 */
@Repository
public class UploadVideoMapper  implements RowMapper {
    /** 用户直播风格的sql映射*/
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        UploadVideo video=new UploadVideo();
        video.setUid(rs.getInt("uid"));
        video.setThumbnailurl(rs.getString("uv_thumbnailurl"));
        video.setTitle(rs.getString("uv_title"));
        video.setUploadtime(rs.getString("uv_uploadtime"));
        video.setVideourl(rs.getString("uv_videourl"));
        video.setVid(rs.getInt("uv_id"));
        return video;
    }
}