package com.zht.album.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zht.album.R;

/*import com.zht.album.R;*/

/*import com.zht.album.R;*/

/**
 * Created by masterzht on 2017/12/5.
 */

public class HomeExamFragment extends Fragment {
    private View viewCourse;
    /*private Button button;
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewCourse = inflater.inflate(R.layout.ss, container, false);
        return viewCourse;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tt=(TextView)viewCourse.findViewById(R.id.tt);
        tt.setText("3");}

    /*@Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        button = (Button) view.findViewById(R.id.btn_pick_photo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//先不要实现这个library，有点小问题
                //startActivity(new Intent(getContext(), com.github.skykai.ui.PhotoPickerActivity.class));
            }
        });



    }*/


}


