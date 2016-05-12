package kr.edcan.donutpunch.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.data.CommonData;

/**
 * Created by KOHA_CLOUD on 16. 5. 13..
 */
public class CommonListViewAdapter extends ArrayAdapter<CommonData> {
    private LayoutInflater inflater;

    public CommonListViewAdapter(Context c, ArrayList<CommonData> arrayList) {
        super(c, 0, arrayList);
        inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view == null) {
            view = inflater.inflate(R.layout.common_listview_content, null);
        } else view = convertView;

        //
        return view;
    }
}
