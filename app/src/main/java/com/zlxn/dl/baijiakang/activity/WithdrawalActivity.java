package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jess.arms.utils.DataHelper;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.BaseActivity;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.CashierInputFilter;
import com.zlxn.dl.baijiakang.utils.EditTextUtils;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import butterknife.BindView;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/22 16:02
 */
public class WithdrawalActivity extends BaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.withdrawalTv)
    TextView withdrawalTv;
    @BindView(R.id.allWithdrawalTv)
    TextView allWithdrawalTv;
    @BindView(R.id.edtiMoney)
    EditText editMoney;
    @BindView(R.id.withdrawalBtn)
    Button withdrawalBtn;
    private String txMoney;
    private MyObserver<String> myObserver;

    @Override
    protected void initView() {
        setContentView(R.layout.act_withdrawal);
    }

    @Override
    protected void initData() {

     /*   InputFilter[] filters = {new CashierInputFilter()};
        editMoney.setFilters(filters);*/

        commonBar.setBackgroundResource(R.color.main_color);

        commonBar.getLeftImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        EditTextUtils.afterDotTwo(editMoney);

        txMoney = getIntent().getStringExtra("txMoney");

        withdrawalTv.setText("可提现金额为" + txMoney + "元");
    }

    @Override
    protected void setListen() {
        allWithdrawalTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editMoney.setText(txMoney);
            }
        });

        withdrawalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Double.parseDouble(editMoney.getText().toString()) > Double.parseDouble(txMoney)){
                    ToastUtils.show(WithdrawalActivity.this,"不能大于可提现金额");
                    return;
                }

                myObserver = new MyObserver<String>(WithdrawalActivity.this,true) {
                    @Override
                    public void onSuccess(String result) {
                        startActivity(new Intent(WithdrawalActivity.this,HomeActivity.class));
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(WithdrawalActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(WithdrawalActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(WithdrawalActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().withdrawalUrl(DataHelper.getIntergerSF(WithdrawalActivity.this,"userType") + "",editMoney.getText().toString(), DataHelper.getStringSF(WithdrawalActivity.this,"token"))
                        .compose(RxHelper.observableIO2Main(WithdrawalActivity.this))
                        .subscribe(myObserver);

            }
        });
    }


    public boolean isConformRules(EditText editText) {
        String result = editText.getText().toString().trim();
        if (TextUtils.isEmpty(result)) {
            return false;
        } else if (result.contains(".")) {

            if (result.startsWith(".") || result.endsWith(".")) {
                return false;
            } else if (result.startsWith("0")) {
                int indexZero = result.indexOf("0");
                int indexPoint = result.indexOf(".");
                if (indexPoint - indexZero != 1) {
                    return false;
                } else if (TextUtils.equals("0.", result)) {
                    return false;
                }
            } else if (result.split("\\.")[1].length() > 2) {
                Toast.makeText(this, "只能保留两位小数", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else if (!result.contains(".")) {
            if (result.startsWith("0")) {
                return false;
            }
        }
        return true;
    }


}
