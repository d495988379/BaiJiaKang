package com.zlxn.dl.baijiakang.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.utils.DataHelper;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.AddressListActivity;
import com.zlxn.dl.baijiakang.activity.AgentManageActivity;
import com.zlxn.dl.baijiakang.activity.InvitationCodeActivity;
import com.zlxn.dl.baijiakang.activity.JyOrderActivity;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.activity.MineBalanceActivity;
import com.zlxn.dl.baijiakang.activity.OrderActivity;
import com.zlxn.dl.baijiakang.activity.RechargeActivity;
import com.zlxn.dl.baijiakang.activity.SettingActivity;
import com.zlxn.dl.baijiakang.base.BaseFragment;
import com.zlxn.dl.baijiakang.bean.LqJfBean;
import com.zlxn.dl.baijiakang.http.Constans;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.DateUtil;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/13 17:50
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.mineStateBar)
    View mineStateBar;
    @BindView(R.id.rechargeImg)
    ImageView rechargeImg;
    @BindView(R.id.mineModule4)
    TextView mineModule4;
    @BindView(R.id.bottomRel3)
    RelativeLayout bottomRel3;
    @BindView(R.id.bottomRel7)
    RelativeLayout bottomRel7;
    @BindView(R.id.bottomRel6)
    RelativeLayout bottomRel6;
    @BindView(R.id.bottomRel5)
    RelativeLayout bottomRel5;
    @BindView(R.id.bottomRel8)
    RelativeLayout bottomRel8;
    @BindView(R.id.mineModule2)
    TextView mineModule2;
    @BindView(R.id.mineHead)
    CircleImageView mineHead;
    @BindView(R.id.mineName)
    TextView mineName;
    @BindView(R.id.minePhone)
    TextView minePhone;
    @BindView(R.id.mineLq)
    TextView mineLq;
    @BindView(R.id.mineJf)
    TextView mineJf;
    @BindView(R.id.mineSf)
    ImageView mineSf;
    @BindView(R.id.mineModule1)
    TextView mineModule1;
    @BindView(R.id.yxqQuanTime)
    TextView yxqQuanTime;
    @BindView(R.id.yxqJfTime)
    TextView yxqJfTime;
    @BindView(R.id.timeView1)
    View timeView1;
    @BindView(R.id.timeView2)
    View timeView2;
    private MyObserver<LqJfBean> myObserver;
    private String balance = "0";
    private String makeMoney = "0";

    @Override
    protected int getContentViewLayout() {
        return R.layout.frg_mine;
    }

    @Override
    protected void initView() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mineStateBar.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = getStatusBar();
        mineStateBar.setLayoutParams(lp);

        minePhone.setText(DataHelper.getStringSF(getActivity(), "userPhone"));
        if (!DataHelper.getStringSF(getActivity(), "userName").equals("")) {
            mineName.setText(DataHelper.getStringSF(getActivity(), "userName"));
        }
        if (!DataHelper.getStringSF(getActivity(), "userHead").equals("")) {
            Glide.with(getActivity()).load(Constans.PicUrl + DataHelper.getStringSF(getActivity(), "userHead")).into(mineHead);
        } else {
            Glide.with(getActivity()).load(R.mipmap.ic_default_head).into(mineHead);
        }

        if (DataHelper.getIntergerSF(getActivity(), "userType") == 1) {//代理商
            if (DataHelper.getIntergerSF(getActivity(), "level") == 1 || DataHelper.getIntergerSF(getActivity(), "level") == 2 || DataHelper.getIntergerSF(getActivity(), "level") == 3 || DataHelper.getIntergerSF(getActivity(), "level") == 5) {  //市级代理1235
                Glide.with(getActivity()).load(R.mipmap.ic_mine_type_img3).into(mineSf);
            } else if (DataHelper.getIntergerSF(getActivity(), "level") == 4 || DataHelper.getIntergerSF(getActivity(), "level") == 6 || DataHelper.getIntergerSF(getActivity(), "level") == 7 || DataHelper.getIntergerSF(getActivity(), "level") == 8) {//区级代理4678
                Glide.with(getActivity()).load(R.mipmap.ic_mine_type_img4).into(mineSf);
            }
        } else {//用户
            if (DataHelper.getIntergerSF(getActivity(), "level") == 1) {
                Glide.with(getActivity()).load(R.mipmap.ic_mine_type_img2).into(mineSf);
            } else if (DataHelper.getIntergerSF(getActivity(), "level") == 2) {
                Glide.with(getActivity()).load(R.mipmap.ic_mine_type_img1).into(mineSf);
            } else if (DataHelper.getIntergerSF(getActivity(), "level") == 3) {
                Glide.with(getActivity()).load(R.mipmap.ic_mine_type_img5).into(mineSf);
            } else if (DataHelper.getIntergerSF(getActivity(), "level") == 4) {
                Glide.with(getActivity()).load(R.mipmap.ic_state_img).into(mineSf);
            }
        }


        if (DataHelper.getIntergerSF(getActivity(), "userType") == 1) {//代理商
            mineModule4.setVisibility(View.VISIBLE);
        } else {
            mineModule4.setVisibility(View.GONE);
        }

        rechargeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), RechargeActivity.class));
            }
        });

        mineModule4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AgentManageActivity.class));
            }
        });

        mineModule2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), OrderActivity.class));
            }
        });

        mineModule1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), JyOrderActivity.class));
            }
        });


        bottomRel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InvitationCodeActivity.class));
            }
        });

        bottomRel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataHelper.setBooleanSF(getActivity(), "isLogin", false);
                Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //  (Double.parseDouble(balance) + Double.parseDouble(makeMoney)) + "")
        bottomRel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (DataHelper.getIntergerSF(getActivity(), "userType") == 1) {//1代理商 2商户
                    startActivity(new Intent(getActivity(), MineBalanceActivity.class)
                            .putExtra("balance", balance)
                            .putExtra("makeMoney", makeMoney));
                } else {
                    startActivity(new Intent(getActivity(), MineBalanceActivity.class)
                            .putExtra("balance", balance)
                            .putExtra("makeMoney", "0"));
                }

            }
        });

        bottomRel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        bottomRel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddressListActivity.class).putExtra("location", "mine"));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        myObserver = new MyObserver<LqJfBean>(getActivity(), true) {
            @Override
            public void onSuccess(LqJfBean result) {
                if (result != null) {
                    if (result.getData() != null) {
                        mineLq.setText("礼券:" + result.getData().getTicket());
                        mineJf.setText("积分:" + result.getData().getPoints());

                        if (result.getData().getDateAfterRecharging() != null) {
                            timeView1.setVisibility(View.VISIBLE);
                            timeView2.setVisibility(View.VISIBLE);
                            yxqJfTime.setVisibility(View.VISIBLE);
                            yxqQuanTime.setVisibility(View.VISIBLE);
                            yxqQuanTime.setText("有效期:" + DateUtil.getDateToString(Long.parseLong(result.getData().getDateAfterRecharging()), "yyyy-MM-dd"));
                            yxqJfTime.setText("有效期:" + DateUtil.getDateToString(Long.parseLong(result.getData().getDateAfterRecharging()), "yyyy-MM-dd"));
                        } else {
                            yxqJfTime.setVisibility(View.VISIBLE);
                            yxqQuanTime.setVisibility(View.VISIBLE);
                            timeView1.setVisibility(View.VISIBLE);
                            timeView2.setVisibility(View.VISIBLE);
                            yxqQuanTime.setText("有效期:");
                            yxqJfTime.setText("有效期:");
                        }

                        if (result.getData().getBalance() != null) {
                            balance = result.getData().getBalance();
                        }
                        if (result.getData().getMakeMoney() != null) {
                            makeMoney = result.getData().getMakeMoney();
                        }

                    }
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(getActivity(), errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(getActivity(), errorMsg);
                    }
                }
            }
        };

        RetrofitUtils.getApiUrl().searchLqJf(DataHelper.getIntergerSF(getActivity(), "userType") + "", DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myObserver);
    }

    @Override
    protected void initData() {

    }


    public int getStatusBar() {
        /**
         * 获取状态栏高度
         * */
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
    }
}
