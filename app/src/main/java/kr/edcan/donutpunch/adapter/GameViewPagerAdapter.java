package kr.edcan.donutpunch.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import kr.edcan.donutpunch.R;

public class GameViewPagerAdapter extends PagerAdapter {
    private LayoutInflater mInflater;
    ArrayList<String> imageAddrArr;

    public GameViewPagerAdapter(Context c, ArrayList<String> imageLoadAddress) {
        super();
        mInflater = LayoutInflater.from(c);
        this.imageAddrArr = imageLoadAddress;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object instantiateItem(View pager, int position) {
        View v = mInflater.inflate(R.layout.main_banner, null);
//        ImageView bg = (ImageView)v.findViewById()
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

