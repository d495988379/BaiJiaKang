package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.bumptech.glide.Glide;
import com.jess.arms.utils.DataHelper;
import com.lihang.ShadowLayout;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.AgentMxAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.BasicJcBean;
import com.zlxn.dl.baijiakang.bean.BasicListBean;
import com.zlxn.dl.baijiakang.bean.MyUserBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.MyScrollView;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/16 13:43
 */
public class AgentManageActivity extends BaseActivity implements OnLoadMoreListener {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.agentImg1)
    ImageView agentImg1;
    @BindView(R.id.agentImg2)
    ImageView agentImg2;
    @BindView(R.id.agentRv1)
    RecyclerView agentRv1;
    @BindView(R.id.agentLin)
    LinearLayout agentLin;
    @BindView(R.id.agentRel)
    LinearLayout agentRel;
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.agentSl1)
    ShadowLayout agentSl1;
    @BindView(R.id.agentSl2)
    ShadowLayout agentSl2;
    @BindView(R.id.agentSl3)
    ShadowLayout agentSl3;
    @BindView(R.id.agentSl4)
    ShadowLayout agentSl4;
    @BindView(R.id.agentTv1)
    TextView agentTv1;
    @BindView(R.id.agentTv2)
    TextView agentTv2;
    @BindView(R.id.agentTv3)
    TextView agentTv3;
    @BindView(R.id.agentTv4)
    TextView agentTv4;
    @BindView(R.id.agentJlMoney)
    TextView agentJlMoney;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    @BindView(R.id.agentZjMoney)
    TextView agentZjMoney;
    @BindView(R.id.agentTdMoney)
    TextView agentTdMoney;
    @BindView(R.id.itemAgentImg1)
    ImageView itemAgentImg1;
    @BindView(R.id.itemAgentImg2)
    ImageView itemAgentImg2;
    @BindView(R.id.itemAgentImg3)
    ImageView itemAgentImg3;
    @BindView(R.id.itemAgentImg4)
    ImageView itemAgentImg4;
    private MyObserver<MyUserBean> myObserver;
    private MyObserver<BasicJcBean> myBasicJcObserver;
    private MyObserver<BasicListBean> myBasicListObserver;
    private int page = 1;
    private AgentMxAdapter adapter1;
    private int type = 0;

    @Override
    protected void initView() {
        setContentView(R.layout.act_agent);
    }

    @Override
    protected void initData() {

        commonBar.setBackgroundResource(R.color.main_color);

        commonBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        commonBar.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgentManageActivity.this, VipQyActivity.class));
            }
        });


        swipeToLoadLayout.setTargetView(scrollView);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(this).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        agentRv1.setLayoutManager(new LinearLayoutManager(this));

        if (DataHelper.getIntergerSF(AgentManageActivity.this, "userType") == 1) {//代理商
            agentSl3.setVisibility(View.GONE);
            agentSl4.setVisibility(View.GONE);
            Glide.with(this).load(R.mipmap.ic_jin_card5).into(itemAgentImg1);
            Glide.with(this).load(R.mipmap.ic_jin_card6).into(itemAgentImg2);
        } else {//用户
            agentSl3.setVisibility(View.VISIBLE);
            agentSl4.setVisibility(View.VISIBLE);
            Glide.with(this).load(R.mipmap.ic_jin_card1).into(itemAgentImg1);
            Glide.with(this).load(R.mipmap.ic_jin_card2).into(itemAgentImg2);
        }

        myObserver = new MyObserver<MyUserBean>(AgentManageActivity.this, true) {
            @Override
            public void onSuccess(MyUserBean result) {
                if (DataHelper.getIntergerSF(AgentManageActivity.this, "userType") == 1) {
                    agentTv1.setText(result.getParent() + "人");
                    agentTv2.setText(result.getChild() + "人");
                } else {
                    agentTv1.setText(result.getGold() + "人");
                    agentTv2.setText(result.getSilver() + "人");
                    agentTv3.setText(result.getPuka() + "人");
                    agentTv4.setText(result.getFans() + "人");
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(AgentManageActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(AgentManageActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(AgentManageActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().myUser(DataHelper.getIntergerSF(AgentManageActivity.this, "userType") + "", DataHelper.getStringSF(this, "token"))
                .compose(RxHelper.observableIO2Main(AgentManageActivity.this))
                .subscribe(myObserver);


        myBasicJcObserver = new MyObserver<BasicJcBean>(AgentManageActivity.this, true) {
            @Override
            public void onSuccess(BasicJcBean result) {
                if (result.getAll() != null) {
                    agentJlMoney.setText(result.getAll() + "元");
                }
                if (result.getDirect() != null) {
                    agentZjMoney.setText(result.getDirect() + "元");
                }
                if (result.getUndirect() != null) {
                    agentTdMoney.setText(result.getUndirect() + "元");
                }


            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(AgentManageActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(AgentManageActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(AgentManageActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().basicJc(DataHelper.getIntergerSF(AgentManageActivity.this, "userType") + "", DataHelper.getStringSF(this, "token"))
                .compose(RxHelper.observableIO2Main(AgentManageActivity.this))
                .subscribe(myBasicJcObserver);


        myBasicListObserver = new MyObserver<BasicListBean>(AgentManageActivity.this, true) {
            @Override
            public void onSuccess(BasicListBean result) {
                if (result.getRows() != null && result.getRows().size() > 0)
                    adapter1 = new AgentMxAdapter(AgentManageActivity.this, R.layout.item_agent_mx, result.getRows());
                agentRv1.setAdapter(adapter1);
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(AgentManageActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(AgentManageActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(AgentManageActivity.this, errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().basicList(DataHelper.getIntergerSF(AgentManageActivity.this, "userType") + "", page + "", "10", DataHelper.getStringSF(this, "token"))
                .compose(RxHelper.observableIO2Main(AgentManageActivity.this))
                .subscribe(myBasicListObserver);


    }

    @Override
    protected void setListen() {
        agentImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agentLin.setVisibility(View.GONE);
                agentRel.setVisibility(View.VISIBLE);
                type = 0;
            }
        });

        agentImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agentRel.setVisibility(View.GONE);
                agentLin.setVisibility(View.VISIBLE);
                type = 1;
            }
        });

        agentSl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataHelper.getIntergerSF(AgentManageActivity.this, "userType") == 1) {
                    startActivity(new Intent(AgentManageActivity.this, UserListActivity.class)
                            .putExtra("location", "dls")
                            .putExtra("type", "1"));
                } else {
                    startActivity(new Intent(AgentManageActivity.this, UserListActivity.class)
                            .putExtra("location", "user")
                            .putExtra("type", "1"));
                }

            }
        });

        agentSl2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataHelper.getIntergerSF(AgentManageActivity.this, "userType") == 1) {
                    startActivity(new Intent(AgentManageActivity.this, UserListActivity.class)
                            .putExtra("location", "dls")
                            .putExtra("type", "2"));
                } else {
                    startActivity(new Intent(AgentManageActivity.this, UserListActivity.class)
                            .putExtra("location", "user")
                            .putExtra("type", "2"));
                }

            }
        });

        agentSl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgentManageActivity.this, UserListActivity.class)
                        .putExtra("location", "user")
                        .putExtra("type", "3"));
            }
        });

        agentSl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgentManageActivity.this, UserListActivity.class)
                        .putExtra("location", "user")
                        .putExtra("type", "4"));
            }
        });

        commonBar.getRightTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AgentManageActivity.this, BannerWebActivity.class)
                        .putExtra("url","http://lu.10010.wiki/Shopping/User/AppLogin?enterpriseId=20022403796496&m=1"));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
        if (myBasicJcObserver != null) {
            myBasicJcObserver.cancleRequest();
        }
        if (myBasicListObserver != null) {
            myBasicListObserver.cancleRequest();
        }
    }


    @Override
    public void onLoadMore() {
        if (type == 0) {
            swipeToLoadLayout.setLoadingMore(false);
        } else {
            page = page + 1;

            myBasicListObserver = new MyObserver<BasicListBean>(AgentManageActivity.this, false) {
                @Override
                public void onSuccess(BasicListBean result) {
                    swipeToLoadLayout.setLoadingMore(false);
                    if (result.getRows() != null && result.getRows().size() > 0) {
                        loadRecyclerView(result.getRows());
                    }

                }

                @Override
                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                    swipeToLoadLayout.setLoadingMore(false);
                    if (e != null) {
                        ToastUtils.show(AgentManageActivity.this, errorCode);
                    } else {
                        if (errorCode.equals("401")) {
                            Intent intent = new Intent(AgentManageActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            ToastUtils.show(AgentManageActivity.this, errorMsg);
                        }
                    }
                }
            };

            RetrofitUtils.getApiUrl().basicList(DataHelper.getIntergerSF(AgentManageActivity.this, "userType") + "", page + "", "10", DataHelper.getStringSF(this, "token"))
                    .compose(RxHelper.observableIO2Main(AgentManageActivity.this))
                    .subscribe(myBasicListObserver);
        }


    }

    private void loadRecyclerView(List<BasicListBean.RowsBean> rows) {
        int position = adapter1.getItemCount() - 1;
        adapter1.loadMoreData(rows);
    }

}
