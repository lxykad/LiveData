package com.lxy.livedata.ui;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxy.livedata.R;
import com.lxy.livedata.SkilBean;
import com.lxy.livedata.ui.entity.SkilEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author a
 * @date 2018/1/25
 */

public class SkilAdapter extends BaseQuickAdapter<SkilEntity, BaseViewHolder> {

    private List<SkilEntity> mList;

    public SkilAdapter(int layoutResId, @Nullable List<SkilEntity> data) {
        super(layoutResId, data);
        mList = new ArrayList<>();
        mList = data;
    }

    @Override
    protected void convert(BaseViewHolder holder, SkilEntity bean) {
        holder.setText(R.id.tv_des, bean.desc);
    }
}
