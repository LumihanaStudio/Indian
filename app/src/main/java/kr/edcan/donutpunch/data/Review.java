package kr.edcan.donutpunch.data;

import java.util.Date;

/**
 * Created by KOHA_CLOUD on 16. 5. 14..
 */
public class Review {
    /*
    * "_id": "AVDHLhdOKdV2xAW",
        "content": "Free To Play라고는 믿을 수 없는 퀄리티. 하지만 과금이 없으면 힘듬.",
        "review_score": 5,
        "review_game": "Warframe",
        "writeTime": "2016-05-13T14:14:45.293Z",
        "writer": "NaturalFish",
        "__v": 0
    * */
    private String _id;
    private String content, review_score, review_game, writer;
    private Date writeTime;

    public String get_id() {
        return _id;
    }

    public String getContent() {
        return content;
    }

    public String getReview_score() {
        return review_score;
    }

    public String getReview_game() {
        return review_game;
    }

    public String getWriter() {
        return writer;
    }

    public Date getWriteTime() {
        return writeTime;
    }
}
