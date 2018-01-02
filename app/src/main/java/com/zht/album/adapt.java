package com.zht.album;

import com.bm.library.PhotoView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by masterzht on 2017/12/15.
 */

public class adapt extends BaseQuickAdapter<Person, BaseViewHolder> {


    PhotoView iv;

    public adapt(int layoutResId, List<Person> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, Person item) {




        Picasso.with(mContext).load(item.getUrl()).into((PhotoView)helper.getView(R.id.iv));
        /*helper.setText(R.id.tv, "第"+item.getPage()+"页");*/


    }




}