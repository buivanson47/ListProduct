package hust.soict.it2.sonbv.productdiscovery.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import hust.soict.it2.sonbv.productdiscovery.fragment.CompareFragment;
import hust.soict.it2.sonbv.productdiscovery.fragment.DescriptionFragment;
import hust.soict.it2.sonbv.productdiscovery.fragment.ParameterFragment;

public class InformationDetailPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public InformationDetailPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new DescriptionFragment();
            case 1: return new ParameterFragment();
            case 2: return new CompareFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
