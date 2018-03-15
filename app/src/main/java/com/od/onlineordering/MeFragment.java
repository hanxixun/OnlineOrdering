package com.od.onlineordering;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.List;

/**
 * HomeFragment
 *
 * @author: hanxixun
 * @time: 2016/8/20 19:07
 */
public class MeFragment extends Fragment implements View.OnClickListener{
    private ImageButton mMe_login_button;
    private LinearLayout mTabMeLogin;
    private List<Fragment> mFragment;
    private LinearLayout mLayout1_view2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     //   return inflater.inflate(R.layout.tab04,container,false);
        View view = inflater.inflate(R.layout.tab04, null);

      //  mMe_login_button= (ImageButton) view.findViewById(R.id.me_login);
        mLayout1_view2 = (LinearLayout) view.findViewById(R.id.me_layout1_view2);
        mLayout1_view2.setOnClickListener(this);
        //mMe_login_button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(),me_layout1_view2.class);
        startActivity(intent);
    }
}
