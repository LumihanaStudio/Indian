package kr.edcan.donutpunch.data;

/**
 * Created by KOHA_CLOUD on 16. 5. 13..
 */
public class CommonData {
    private String title;
    private String content;
    public CommonData(String title, String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
