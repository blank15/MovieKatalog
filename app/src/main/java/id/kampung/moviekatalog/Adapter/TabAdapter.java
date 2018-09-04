package id.kampung.moviekatalog.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import id.kampung.moviekatalog.View.Fragment.NowPlaying.PlayingFragment;
import id.kampung.moviekatalog.View.Fragment.NowPlaying.UpComingFragment;

public class TabAdapter extends FragmentPagerAdapter {
    
    private String title[] =new String[2];
    Context context;
    public TabAdapter(FragmentManager fm,String title[]) {
        super(fm);
        this.context = context;
        this.title = title;
       
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new PlayingFragment();
        switch (position){
            case 0:
                fragment = new PlayingFragment();
                break;
            case 1:
                fragment = new UpComingFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return title.length;
    }
    @Override
    public CharSequence getPageTitle(
            int position) {
        return title[position];
    }

}
