package com.example.himalaya;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.himalaya.adapters.DetailListAdapter;
import com.example.himalaya.base.BaseActivity;
import com.example.himalaya.interfaces.IAlbumDetailViewCallback;
import com.example.himalaya.presenters.AlbumDetailPresenter;
import com.example.himalaya.presenters.PlayerPresenter;
import com.example.himalaya.utils.LogUtil;
import com.example.himalaya.views.ImageBlur;
import com.example.himalaya.views.RoundRectImageView;
import com.example.himalaya.views.UILoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ximalaya.ting.android.opensdk.model.album.Album;
import com.ximalaya.ting.android.opensdk.model.track.Track;

import net.lucode.hackware.magicindicator.buildins.UIUtil;

import java.util.List;

/**
 * Description :
 * Time : 2020/7/24 16:10
 * Author : zengkun
 */
public class DetailActivity extends BaseActivity implements IAlbumDetailViewCallback, UILoader.OnRetryClickListener, DetailListAdapter.ItemClickListener {

    private static final String TAG = "DetailActivity";
    private RoundRectImageView mSmallCover;
    private ImageView mLargeCover;
    private TextView mAlbumTitle;
    private TextView mAlbumAuthor;
    private AlbumDetailPresenter mAlbumDetailPresenter;
    private int mCurrentPage = 1;
    private RecyclerView mDetailList;
    private DetailListAdapter mDetailListAdapter;
    private FrameLayout mDetailListContainer;
    private UILoader mUiLoader;
    private long mCurrentId = -1;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        //设置顶部状态栏透明，就是要把下面的顶上去
        getWindow().setStatusBarColor(Color.TRANSPARENT);
        initView();
        mAlbumDetailPresenter = AlbumDetailPresenter.getInstance();
        mAlbumDetailPresenter.registerViewCallback(this);
    }

    private void initView() {
        mDetailListContainer = findViewById(R.id.detail_list_container);
        if (mUiLoader == null) {
            mUiLoader = new UILoader(this) {
                @Override
                protected View getSuccessView(ViewGroup container) {
                    return createSuccessView(container);
                }
            };
            mDetailListContainer.removeAllViews();
            mDetailListContainer.addView(mUiLoader);
            mUiLoader.setOnRetryClickListener(this);
        }
        mLargeCover = this.findViewById(R.id.iv_large_cover);
        mSmallCover = findViewById(R.id.iv_small_cover);
        mAlbumTitle = findViewById(R.id.tv_title);
        mAlbumAuthor = findViewById(R.id.tv_author);


    }

    private View createSuccessView(ViewGroup container) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_detail_list, container, false);
        mDetailList = view.findViewById(R.id.album_detail_list);
        //设置布局管理器
        LinearLayoutManager llm = new LinearLayoutManager(this);
        mDetailList.setLayoutManager(llm);
        //设置适配器
        mDetailListAdapter = new DetailListAdapter();
        mDetailListAdapter.setItemClickListener(this);
        mDetailList.setAdapter(mDetailListAdapter);
        //设置item的上下间距
        mDetailList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = UIUtil.dip2px(view.getContext(), 2);
                outRect.bottom = UIUtil.dip2px(view.getContext(), 2);
                outRect.left = UIUtil.dip2px(view.getContext(), 2);
                outRect.right = UIUtil.dip2px(view.getContext(), 2);
            }
        });

        return view;
    }

    @Override
    public void onDetailListLoaded(List<Track> tracks) {
        //判断数据结果，根据结果显示ui
        if (tracks.size() == 0) {
            if (mUiLoader != null) {
                mUiLoader.updateStatus(UILoader.UIStatus.EMPTY);
            }
        }
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.SUCCESS);
        }
        //更新或设置
        mDetailListAdapter.setData(tracks);
    }

    @Override
    public void onNetworkError(int errorCode, String errorMessage) {
        //网络错误时，ui的处理
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.NETWORK_ERROR);
        }
    }

    @Override
    public void onAlbumLoaded(Album album) {
        //获取专辑的详情内容
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int)album.getId(), mCurrentPage);
        }
        mCurrentId = album.getId();
        //拿数据，显示loading
        if (mUiLoader != null) {
            mUiLoader.updateStatus(UILoader.UIStatus.LOADING);
        }
        if (mAlbumTitle != null) {
            mAlbumTitle.setText(album.getAlbumTitle());
        }
        if (mAlbumAuthor != null) {
            mAlbumAuthor.setText(album.getAnnouncer().getNickname());
        }
        //做毛玻璃效果
        if (mLargeCover != null) {
            Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover, new Callback() {
                @Override
                public void onSuccess() {
                    Drawable drawable = mLargeCover.getDrawable();
                    if (drawable != null) {
                        ImageBlur.makeBlur(mLargeCover,DetailActivity.this);
                    }
                }
                @Override
                public void onError() {
                    LogUtil.d(TAG, "onError");
                }
            });
//            Picasso.with(this).load(album.getCoverUrlLarge()).into(mLargeCover);
//            //这里并不能保证图片加载成功后才执行下一句代码的
//            ImageBlur.makeBlur(mLargeCover,this);
        }
        if (mSmallCover != null) {
            Picasso.with(this).load(album.getCoverUrlSmall()).into(mSmallCover);
        }
    }

    @Override
    public void onRetryClick() {
        if (mAlbumDetailPresenter != null) {
            mAlbumDetailPresenter.getAlbumDetail((int) mCurrentId, mCurrentPage);
        }
    }

    @Override
    public void onItemClick(List<Track> detailData, int position) {
        //设置播放器的数据
        PlayerPresenter playerPresenter = PlayerPresenter.getPlayerPresenter();
        playerPresenter.setPlayList(detailData, position);
        //跳转到播放器界面
        Intent intent = new Intent(this, PlayerActivity.class);
        startActivity(intent);
    }
}