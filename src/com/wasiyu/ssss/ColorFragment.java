package com.wasiyu.ssss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ColorFragment extends Fragment {
	
	private int mColorRes = -1;
	
	public ColorFragment() { 
		this(R.color.white);
	}
	
	public ColorFragment(int colorRes) {
		mColorRes = colorRes;
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (savedInstanceState != null)
			mColorRes = savedInstanceState.getInt("mColorRes");
		int color = getResources().getColor(mColorRes);
        View inflate = inflater.inflate(R.layout.test, container, false);
        ScrollView root = (ScrollView) inflate.findViewById(R.id.scroll);
//        DrawView root = (DrawView) inflate.findViewById(R.id.drawView);
//        final DrawView drawView = new DrawView(getActivity());
//        final DrawView drawView = (DrawView) root.findViewById(R.id.drawView);
//        drawView.invalidate();
//        root.addView(root);
//        root.setBackgroundColor(color);

		return root;
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", mColorRes);
	}
	
}
