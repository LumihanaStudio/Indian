package kr.edcan.donutpunch.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import kr.edcan.donutpunch.R;
import kr.edcan.donutpunch.activity.GameInfoViewActivity;
import kr.edcan.donutpunch.utils.ImageSingleTon;

public class PagerAdapterClass extends PagerAdapter {
    private LayoutInflater mInflater;
    ArrayList<String> titleArr, contentArr, imageAddrArr;

    Context c;

    public PagerAdapterClass(Context c, ArrayList<String> title, ArrayList<String> content, ArrayList<String> imageLoadAddress) {
        super();
        mInflater = LayoutInflater.from(c);
        this.c = c;

        this.titleArr = title;
        this.contentArr = content;
        this.imageAddrArr = imageLoadAddress;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object instantiateItem(View pager, int position) {
        View v = mInflater.inflate(R.layout.main_banner, null);
        TextView title = (TextView) v.findViewById(R.id.main_banner_title);
        TextView content = (TextView) v.findViewById(R.id.main_banner_content);
        title.setText(titleArr.get(position));
        content.setText(contentArr.get(position));
        NetworkImageView imageview;
        imageview = (NetworkImageView)v.findViewById(R.id.game_image_view);
        ImageLoader loader;
        loader = ImageSingleTon.getInstance(c).getImageLoader();
        imageview.setImageUrl("http://iwin247.net/view/"+imageAddrArr.get(position), loader);
        ((ViewPager) pager).addView(v, 0);
        return v;
    }

    @Override
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager) pager).removeView((View) view);
    }

    @Override
    public boolean isViewFromObject(View pager, Object obj) {
        return pager == obj;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }

    @Override
    public void finishUpdate(View arg0) {
    }
}

