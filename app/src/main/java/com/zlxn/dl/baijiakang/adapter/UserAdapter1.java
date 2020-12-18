package com.zlxn.dl.baijiakang.adapter;

import android.content.Context;

import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseViewHolder;
import com.zlxn.dl.baijiakang.base.SimpleAdapter;
import com.zlxn.dl.baijiakang.bean.DlsListBean;
import com.zlxn.dl.baijiakang.bean.UserListBean;
import com.zlxn.dl.baijiakang.utils.DateUtil;

import java.util.List;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/15 15:19
 */
public class UserAdapter1 extends SimpleAdapter<UserListBean> {

    public UserAdapter1(Context context, int layoutResId, List<UserListBean> datas) {
        super(context, layoutResId, datas);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, UserListBean item) {
        viewHolder.getTextView(R.id.itemUserName).setText(item.getUserName() != null ? item.getUserName() : "");
        viewHolder.getTextView(R.id.itemAddress).setText(DateUtil.getDateToString(item.getLastLoginTime(), "yyyy-MM-dd HH:mm:ss"));
    }
}
