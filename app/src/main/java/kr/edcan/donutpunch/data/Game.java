package kr.edcan.donutpunch.data;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by KOHA_CLOUD on 16. 5. 14..
 */
public class Game {
    private String _id, writer, gameName, launch_date, average_score, content;
    private Date writeTime;
    private String[] tag;

    @SerializedName("picture_src")
    private List<SRC> src;

    public String get_id() {
        return _id;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public String getGameName() {
        return gameName;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getAverage_score() {
        return average_score;
    }

    public Date getWriteTime() {
        return writeTime;
    }

    public List<SRC> getFileList() {
        return src;
    }

    public String[] getTag() {
        return tag;
    }


}
