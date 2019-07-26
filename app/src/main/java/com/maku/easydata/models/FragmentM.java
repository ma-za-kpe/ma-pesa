package com.maku.easydata.models;

import androidx.fragment.app.Fragment;

public class FragmentM {
    private Fragment fragment;
    private String tag;

    public FragmentM(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public FragmentM() {
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
