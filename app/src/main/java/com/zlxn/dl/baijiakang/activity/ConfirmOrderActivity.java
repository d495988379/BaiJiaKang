package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.adapter.ConfirmAdapter;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.bean.AddOrderBean;
import com.zlxn.dl.baijiakang.bean.AddressBean;
import com.zlxn.dl.baijiakang.bean.ShopCarBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.OnPasswordInputFinish;
import com.zlxn.dl.baijiakang.view.PopEnterPassword;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/11/12 16:19
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.addressName)
    TextView addressName;
    @BindView(R.id.addressPhone)
    TextView addressPhone;
    @BindView(R.id.addressAddress)
    TextView addressAddress;
    @BindView(R.id.confirmRv)
    RecyclerView confirmRv;
    @BindView(R.id.confirmBtn)
    Button confirmBtn;
    @BindView(R.id.confirmMoney)
    TextView confirmMoney;
    @BindView(R.id.confirmAddressEmpty)
    RelativeLayout confirmAddressEmpty;
    @BindView(R.id.confirmAddressLin)
    LinearLayout confirmAddressLin;
    @BindView(R.id.payTypeRel1)
    RelativeLayout payTypeRel1;
    @BindView(R.id.payTypeRel2)
    RelativeLayout payTypeRel2;
    @BindView(R.id.payTypeRel3)
    RelativeLayout payTypeRel3;
    @BindView(R.id.payTypeRel4)
    RelativeLayout payTypeRel4;
    @BindView(R.id.payTypeImg1)
    ImageView payTypeImg1;
    @BindView(R.id.payTypeImg2)
    ImageView payTypeImg2;
    @BindView(R.id.payTypeImg3)
    ImageView payTypeImg3;
    @BindView(R.id.payTypeImg4)
    ImageView payTypeImg4;
    @BindView(R.id.confirmQuan)
    TextView confirmQuan;
    @BindView(R.id.confirmLin)
    LinearLayout confirmLin;
    private MyObserver<AddressBean> myObserver;
    private MyObserver<AddOrderBean> myAddObserver;
    private int addressId;
    private List<String> ids;
    private List<String> ggIds;
    private String idStr;
    private String ggIdStr;
    private String money;
    private String quan;
    private String type;
    private int number;
    private String payType = "";

    @Override
    protected void initView() {
        setContentView(R.layout.act_confirm_order);
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

        money = getIntent().getStringExtra("money");
        quan = getIntent().getStringExtra("quan");
        confirmMoney.setText("实付款：¥" + money);
        confirmQuan.setText("券总额：¥" + quan);
        type = getIntent().getStringExtra("type");

        if (DataHelper.getIntergerSF(ConfirmOrderActivity.this, "userType") == 1) {
            payTypeRel4.setVisibility(View.VISIBLE);
        } else {
            payTypeRel4.setVisibility(View.GONE);
        }

        myObserver = new MyObserver<AddressBean>(ConfirmOrderActivity.this, true) {
            @Override
            public void onSuccess(AddressBean result) {
                if (result.getAddressList() != null && result.getAddressList().size() > 0) {
                    for (int i = 0; i < result.getAddressList().size(); i++) {
                        if (result.getAddressList().get(i).getConfirm() == 1) {
                            confirmAddressEmpty.setVisibility(View.GONE);
                            confirmAddressLin.setVisibility(View.VISIBLE);
                            addressId = result.getAddressList().get(i).getId();
                            addressName.setText(result.getAddressList().get(i).getUserName());
                            addressPhone.setText(result.getAddressList().get(i).getPhone());
                            addressAddress.setText(result.getAddressList().get(i).getArea() + result.getAddressList().get(i).getDetailArea());
                            return;
                        } else {
                            confirmAddressEmpty.setVisibility(View.VISIBLE);
                            confirmAddressLin.setVisibility(View.GONE);
                        }
                    }
                } else {
                    confirmAddressEmpty.setVisibility(View.VISIBLE);
                    confirmAddressLin.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(ConfirmOrderActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(ConfirmOrderActivity.this, errorMsg);
                    }
                }

            }
        };

        RetrofitUtils.getApiUrl().addressList(DataHelper.getIntergerSF(ConfirmOrderActivity.this, "userType") + "", DataHelper.getStringSF(ConfirmOrderActivity.this, "token"))
                .compose(RxHelper.observableIO2Main(this))
                .subscribe(myObserver);

        List<ShopCarBean.DataBean> list = new Gson().fromJson(getIntent().getStringExtra("data"), new TypeToken<List<ShopCarBean.DataBean>>() {
        }.getType());


        confirmRv.setLayoutManager(new LinearLayoutManager(this));
        ConfirmAdapter adapter = new ConfirmAdapter(this, R.layout.item_confirm, list);
        confirmRv.setAdapter(adapter);


        ids = new ArrayList<>();
        ggIds = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            ids.add(list.get(i).getId() + "");
            if (list.get(i).getStageId() != null) {
                ggIds.add(list.get(i).getStageId());
            }
        }

        ggIdStr = ArmsUtils.listToString(ggIds, ",");
        idStr = ArmsUtils.listToString(ids, ",");

        if (type.equals("2")) {//直接下单
            number = list.get(0).getNumber();
        } else {//购物车下单
            number = 0;
        }
    }

    @Override
    protected void setListen() {
        confirmAddressLin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ConfirmOrderActivity.this, AddressListActivity.class)
                        .putExtra("location", "order"), 1);
            }
        });

        confirmAddressEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(ConfirmOrderActivity.this, AddressListActivity.class)
                        .putExtra("location", "order"), 1);
            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payType.equals("")) {
                    ToastUtils.show(ConfirmOrderActivity.this, "请选择支付方式");
                    return;
                }

                if (payType.equals("3")) {
                    if (!DataHelper.getBooleanSF(ConfirmOrderActivity.this, "isPayPwd")) {
                        ToastUtils.show(ConfirmOrderActivity.this, "请设置支付密码");
                        return;
                    }
                }

                myAddObserver = new MyObserver<AddOrderBean>(ConfirmOrderActivity.this, true) {
                    @Override
                    public void onSuccess(AddOrderBean result) {
                        if (payType.equals("1")) {
                            ToastUtils.show(ConfirmOrderActivity.this, "下单成功");
                            Intent intent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else if (payType.equals("2")) {
                            ToastUtils.show(ConfirmOrderActivity.this, "下单成功");
                            Intent intent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else if (payType.equals("3")) {
                            final PopEnterPassword popEnterPassword = new PopEnterPassword(ConfirmOrderActivity.this, "支付金额", Double.parseDouble(money));
                            popEnterPassword.getPwdView().setOnFinishInput(new OnPasswordInputFinish() {
                                @Override
                                public void inputFinish(String password) {
                                    RetrofitUtils.getApiUrl().payUrl(DataHelper.getIntergerSF(ConfirmOrderActivity.this, "userType") + "",
                                            password, payType, result.getData() + "",
                                            DataHelper.getStringSF(ConfirmOrderActivity.this, "token"))
                                            .compose(RxHelper.observableIO2Main(ConfirmOrderActivity.this))
                                            .subscribe(new MyObserver<String>(ConfirmOrderActivity.this, true) {
                                                @Override
                                                public void onSuccess(String result) {
                                                    ToastUtils.show(ConfirmOrderActivity.this, "下单成功");
                                                    Intent intent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                                                    startActivity(intent);
                                                }

                                                @Override
                                                public void onFailure(Throwable e, String errorCode, String errorMsg) {
                                                    if (e != null) {
                                                        ToastUtils.show(ConfirmOrderActivity.this, errorCode);
                                                    } else {
                                                        if (errorCode.equals("401")) {
                                                            Intent intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                        } else if (errorCode.equals("423")){
                                                            popEnterPassword.getPwdView().allCancel();
                                                            ToastUtils.show(ConfirmOrderActivity.this, "支付密码错误，请重新输入");
                                                        }else {
                                                            ToastUtils.show(ConfirmOrderActivity.this, errorMsg);
                                                        }
                                                    }
                                                }
                                            });
                                }
                            });

                            popEnterPassword.getPwdView().getImgCancel().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    popEnterPassword.dismiss();
                                    ToastUtils.show(ConfirmOrderActivity.this, "订单未支付");
                                    Intent intent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                                    startActivity(intent);

                                }
                            });


                            popEnterPassword.showAtLocation(confirmLin,
                                    Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

                        } else if (payType.equals("4")) {
                            ToastUtils.show(ConfirmOrderActivity.this, "下单成功");
                            Intent intent = new Intent(ConfirmOrderActivity.this, HomeActivity.class);
                            startActivity(intent);
                        }


                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(ConfirmOrderActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(ConfirmOrderActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(ConfirmOrderActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().addOrder(DataHelper.getIntergerSF(ConfirmOrderActivity.this, "userType") + "", payType, number + "", ggIdStr, type, addressId + "", money, idStr, quan, DataHelper.getStringSF(ConfirmOrderActivity.this, "token"))
                        .compose(RxHelper.observableIO2Main(ConfirmOrderActivity.this))
                        .subscribe(myAddObserver);


            }
        });


        payTypeRel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = "1";
                payTypeImg1.setImageResource(R.mipmap.ic_pay_select_img);
                payTypeImg2.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg3.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg4.setImageResource(R.mipmap.ic_pay_unselect_img);
            }
        });

        payTypeRel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = "2";
                payTypeImg1.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg2.setImageResource(R.mipmap.ic_pay_select_img);
                payTypeImg3.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg4.setImageResource(R.mipmap.ic_pay_unselect_img);
            }
        });

        payTypeRel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = "3";
                payTypeImg1.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg2.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg3.setImageResource(R.mipmap.ic_pay_select_img);
                payTypeImg4.setImageResource(R.mipmap.ic_pay_unselect_img);
            }
        });

        payTypeRel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payType = "4";
                payTypeImg1.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg2.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg3.setImageResource(R.mipmap.ic_pay_unselect_img);
                payTypeImg4.setImageResource(R.mipmap.ic_pay_select_img);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                addressName.setText(data.getStringExtra("userName"));
                addressPhone.setText(data.getStringExtra("phone"));
                addressAddress.setText(data.getStringExtra("address"));
                addressId = data.getIntExtra("addressId", -1);
                confirmAddressEmpty.setVisibility(View.GONE);
                confirmAddressLin.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myObserver.cancleRequest();
        if (myAddObserver != null) {
            myAddObserver.cancleRequest();
        }
    }

}
