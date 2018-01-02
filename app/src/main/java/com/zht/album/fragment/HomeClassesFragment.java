package com.zht.album.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zht.album.MyOkhttp;
import com.zht.album.Person;
import com.zht.album.R;
import com.zht.album.adapt;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by masterzht on 2017/12/5.
 */

public class HomeClassesFragment extends Fragment {

    private View viewCourse;
    private static RecyclerView recyclerview;
    private GridLayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem;
    private int page=1;
    private ItemTouchHelper itemTouchHelper;
    private List<Person> meizis;
    private adapt mAdapter;
    private CoordinatorLayout coordinatorLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewCourse = inflater.inflate(R.layout.sw,container, false);
        return viewCourse;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.grid_swipe_refresh);
        mLayoutManager=new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerview = (RecyclerView) view.findViewById(R.id.grid_recycler);
        recyclerview.setLayoutManager(mLayoutManager);

        swipeRefreshLayout.setProgressViewOffset(false, 0,  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));

        new GetData().execute("http://gank.io/api/data/福利/10/1");

        //下拉刷新的实现
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //定时器，3秒后执行
                /*new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
        ...
                        adapter.addRefreshBeans(temp);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);*/
                page=1;
                new GetData().execute("http://gank.io/api/data/福利/10/1");
            }
        });

        itemTouchHelper=new ItemTouchHelper(new ItemTouchHelper.Callback() {

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int dragFlags=0;
                if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager ||recyclerView.getLayoutManager() instanceof GridLayoutManager){
                    dragFlags=ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
                }
                return makeMovementFlags(dragFlags,0);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int from=viewHolder.getAdapterPosition();
                int to=target.getAdapterPosition();
                Person moveItem=meizis.get(from);
                meizis.remove(from);
                meizis.add(to,moveItem);
                mAdapter.notifyItemMoved(from,to);
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }
        });


        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //0：当前屏幕停止滚动；1时：屏幕在滚动 且 用户仍在触碰或手指还在屏幕上；2时：随用户的操作，屏幕上产生的惯性滑动；
                // 滑动状态停止并且剩余少于两个item时，自动加载下一页
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem +2>=mLayoutManager.getItemCount()) {

                    //swipeRefreshLayout.setRefreshing(true);
                    new GetData().execute("http://gank.io/api/data/福利/10/"+(++page));
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

             lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();

            }
        });

    }




    private class GetData extends AsyncTask<String, Integer, String> {

        View mParent;
        View mBg;
        PhotoView mPhotoView;
        Info mInfo;



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //设置swipeRefreshLayout为刷新状态，展开刷新动画
            /*swipeRefreshLayout.setRefreshing(true);*/


        }

        @Override
        protected String doInBackground(String... params) {

            return MyOkhttp.get(params[0]);
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!TextUtils.isEmpty(result)){

                JSONObject jsonObject;
                Gson gson=new Gson();
                String jsonData=null;

                try {
                    jsonObject = new JSONObject(result);
                    jsonData = jsonObject.getString("results");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(meizis==null||meizis.size()==0){
                    meizis= gson.fromJson(jsonData, new TypeToken<List<Person>>() {}.getType());
                }else{
                    List<Person> more= gson.fromJson(jsonData, new TypeToken<List<Person>>() {}.getType());
                    meizis.addAll(more);
                }


                /*recyclerview.setAdapter(mAdapter=new adapt(R.layout.fragment1item,meizis));
                mAdapter.notifyDataSetChanged();*/
                if(mAdapter==null){
                recyclerview.setAdapter(mAdapter=new adapt(R.layout.fragment1item,meizis));
                itemTouchHelper.attachToRecyclerView(recyclerview);
                }else{
                    mAdapter.notifyDataSetChanged();
                }

            }

            swipeRefreshLayout.setRefreshing(false);
        }
    }

}
