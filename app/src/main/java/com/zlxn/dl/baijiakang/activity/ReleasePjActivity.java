package com.zlxn.dl.baijiakang.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.DataHelper;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.TResult;
import com.nuogao.titlebar.widget.CommonTitleBar;
import com.willy.ratingbar.BaseRatingBar;
import com.willy.ratingbar.ScaleRatingBar;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.base.TakeBaseActivity;
import com.zlxn.dl.baijiakang.bean.OrderListBean;
import com.zlxn.dl.baijiakang.bean.ShopCarBean;
import com.zlxn.dl.baijiakang.bean.UploadPicBean;
import com.zlxn.dl.baijiakang.http.Constans;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.ToastUtils;
import com.zlxn.dl.baijiakang.view.BaseBottomView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/23 13:51
 */
public class ReleasePjActivity extends TakeBaseActivity {

    @BindView(R.id.commonBar)
    CommonTitleBar commonBar;
    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar simpleRatingBar;
    @BindView(R.id.fbImg)
    ImageView fbImg;
    @BindView(R.id.pjImg1)
    ImageView pjImg1;
    @BindView(R.id.pjImg2)
    ImageView pjImg2;
    @BindView(R.id.pjImg3)
    ImageView pjImg3;
    @BindView(R.id.pjBtn)
    Button pjBtn;
    @BindView(R.id.pjEdit)
    EditText pjEdit;
    private List<String> data = new ArrayList<>();

    private TakePhoto takePhoto;
    private CropOptions cropOptions;
    private BaseBottomView bottomView;
    private TextView blackTv;
    private Button b1, b2, b3;
    private Uri imageUri;  //图片保存路径
    private String path = "";
    private MyObserver<UploadPicBean> myImgObserver;
    private int count = 0;
    private String orderId;
    private List<String> id = new ArrayList<>();
    private String ids;
    private MyObserver<String> myObserver;

    @Override
    protected void initView() {
        setContentView(R.layout.act_release_pj);
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

        ////获取TakePhoto实例
        takePhoto = getTakePhoto();
        //设置裁剪参数
        cropOptions = new CropOptions.Builder().setOutputX(800).setOutputY(800)
                .setWithOwnCrop(false).create();
        /*//设置压缩参数
        compressConfig =new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create();
        takePhoto.onEnableCompress(compressConfig,true);  //设置为需要压缩*/

        bottomView = new BaseBottomView(this, R.layout.act_photo);
        blackTv = ((TextView) bottomView.findViewById(R.id.headChangePopupBlackId));
        b1 = (Button) bottomView.findViewById(R.id.takePhotoBtnId);
        b2 = (Button) bottomView.findViewById(R.id.pickPhtotBtnId);
        b3 = (Button) bottomView.findViewById(R.id.cancel_btnId);
        bottomView.setCancelable(true);

        orderId = getIntent().getStringExtra("id");
        List<OrderListBean.DataBean.OrderDetailBean> list = new Gson().fromJson(getIntent().getStringExtra("details"), new TypeToken<List<OrderListBean.DataBean.OrderDetailBean>>() {
        }.getType());

        id = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            id.add(list.get(i).getProductId() + "");
        }

        ids = ArmsUtils.listToString(id, ",");
    }


    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        if (bottomView.isShowing()) {
            bottomView.dismiss();
        }
        path = result.getImage().getOriginalPath();

        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("file", file.getName(), requestFile);
        MultipartBody body = builder.build();

        myImgObserver = new MyObserver<UploadPicBean>(ReleasePjActivity.this, true) {
            @Override
            public void onSuccess(UploadPicBean result) {
                data.add(result.getImgUrl());
                if (data.size() == 3) {
                    fbImg.setVisibility(View.GONE);
                    pjImg1.setVisibility(View.VISIBLE);
                    pjImg2.setVisibility(View.VISIBLE);
                    pjImg3.setVisibility(View.VISIBLE);
                    Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                    Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(1)).into(pjImg2);
                    Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(2)).into(pjImg3);
                } else if (data.size() == 2) {
                    fbImg.setVisibility(View.VISIBLE);
                    pjImg1.setVisibility(View.VISIBLE);
                    pjImg2.setVisibility(View.VISIBLE);
                    pjImg3.setVisibility(View.GONE);
                    Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                    Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(1)).into(pjImg2);
                } else if (data.size() == 1) {
                    fbImg.setVisibility(View.VISIBLE);
                    pjImg1.setVisibility(View.VISIBLE);
                    pjImg2.setVisibility(View.GONE);
                    pjImg3.setVisibility(View.GONE);
                    Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                if (e != null) {
                    ToastUtils.show(ReleasePjActivity.this, errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(ReleasePjActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
                        ToastUtils.show(ReleasePjActivity.this, errorMsg);
                    }
                }
            }
        };


        RetrofitUtils.getApiUrl().uploadPic(body)
                .compose(RxHelper.observableIO2Main(ReleasePjActivity.this))
                .subscribe(myImgObserver);

    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }


    //获得照片的输出保存Uri
    private Uri getImageCropUri() {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".png");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        return Uri.fromFile(file);
    }


    @Override
    protected void setListen() {

        simpleRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar baseRatingBar, float v) {
                count = (int) v;
            }
        });

        fbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomView.show();
            }
        });


        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomView.dismiss();
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = getImageCropUri();
                //从相册中选取图片并裁剪
                takePhoto.onPickFromGalleryWithCrop(imageUri, cropOptions);
                //从相册中选取不裁剪
                //takePhoto.onPickFromGallery();
            }
        });


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageUri = getImageCropUri();
                //拍照并裁剪
                takePhoto.onPickFromCaptureWithCrop(imageUri, cropOptions);
                //仅仅拍照不裁剪
                //takePhoto.onPickFromCapture(imageUri);
            }
        });


        blackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomView.dismiss();
            }
        });

        pjImg1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                data.remove(0);
                fbImg.setVisibility(View.VISIBLE);

                if (data != null && data.size() > 0) {
                    if (data.size() == 2) {
                        fbImg.setVisibility(View.VISIBLE);
                        pjImg1.setVisibility(View.VISIBLE);
                        pjImg2.setVisibility(View.VISIBLE);
                        pjImg3.setVisibility(View.GONE);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(1)).into(pjImg2);
                    } else if (data.size() == 1) {
                        fbImg.setVisibility(View.VISIBLE);
                        pjImg1.setVisibility(View.VISIBLE);
                        pjImg2.setVisibility(View.GONE);
                        pjImg3.setVisibility(View.GONE);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                    }
                } else {
                    pjImg1.setVisibility(View.GONE);
                    pjImg2.setVisibility(View.GONE);
                    pjImg3.setVisibility(View.GONE);
                    fbImg.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        pjImg2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                data.remove(0);
                fbImg.setVisibility(View.VISIBLE);

                if (data != null && data.size() > 0) {
                    if (data.size() == 2) {
                        fbImg.setVisibility(View.VISIBLE);
                        pjImg1.setVisibility(View.VISIBLE);
                        pjImg2.setVisibility(View.VISIBLE);
                        pjImg3.setVisibility(View.GONE);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(1)).into(pjImg2);
                    } else if (data.size() == 1) {
                        fbImg.setVisibility(View.VISIBLE);
                        pjImg1.setVisibility(View.VISIBLE);
                        pjImg2.setVisibility(View.GONE);
                        pjImg3.setVisibility(View.GONE);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                    }
                } else {
                    pjImg1.setVisibility(View.GONE);
                    pjImg2.setVisibility(View.GONE);
                    pjImg3.setVisibility(View.GONE);
                    fbImg.setVisibility(View.VISIBLE);
                }

                return true;
            }
        });

        pjImg3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                data.remove(0);
                fbImg.setVisibility(View.VISIBLE);

                if (data != null && data.size() > 0) {
                    if (data.size() == 2) {
                        fbImg.setVisibility(View.VISIBLE);
                        pjImg1.setVisibility(View.VISIBLE);
                        pjImg2.setVisibility(View.VISIBLE);
                        pjImg3.setVisibility(View.GONE);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(1)).into(pjImg2);
                    } else if (data.size() == 1) {
                        fbImg.setVisibility(View.VISIBLE);
                        pjImg1.setVisibility(View.VISIBLE);
                        pjImg2.setVisibility(View.GONE);
                        pjImg3.setVisibility(View.GONE);
                        Glide.with(ReleasePjActivity.this).load(Constans.PicUrl + data.get(0)).into(pjImg1);
                    }
                } else {
                    pjImg1.setVisibility(View.GONE);
                    pjImg2.setVisibility(View.GONE);
                    pjImg3.setVisibility(View.GONE);
                    fbImg.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

        pjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.size() == 0) {
                    ToastUtils.show(ReleasePjActivity.this, "请上传评价图片");
                    return;
                }
                if (pjEdit.getText().toString().length() == 0) {
                    ToastUtils.show(ReleasePjActivity.this, "请输入您的评价");
                    return;
                }
                String picList = ArmsUtils.listToString(data, ",");

                myObserver = new MyObserver<String>(ReleasePjActivity.this,true) {
                    @Override
                    public void onSuccess(String result) {
                        if (getIntent().getStringExtra("location").equals("orderList")){
                            ToastUtils.show(ReleasePjActivity.this,"评价成功");
                            DataHelper.setBooleanSF(ReleasePjActivity.this,"isSet",true);
                            finish();
                        }else {
                            ToastUtils.show(ReleasePjActivity.this,"评价成功");
                            DataHelper.setBooleanSF(ReleasePjActivity.this,"isSet",true);
                            Intent intent = new Intent();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable e, String errorCode, String errorMsg) {
                        if (e != null) {
                            ToastUtils.show(ReleasePjActivity.this, errorCode);
                        } else {
                            if (errorCode.equals("401")) {
                                Intent intent = new Intent(ReleasePjActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            } else {
                                ToastUtils.show(ReleasePjActivity.this, errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().addEvaluation(DataHelper.getIntergerSF(ReleasePjActivity.this, "userType") + "",pjEdit.getText().toString(),picList,ids,orderId,count+ "", DataHelper.getStringSF(ReleasePjActivity.this,"token"))
                        .compose(RxHelper.observableIO2Main(ReleasePjActivity.this))
                        .subscribe(myObserver);

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myImgObserver != null){
            myImgObserver.cancleRequest();
        }
        if (myObserver != null){
            myObserver.cancleRequest();
        }
    }
}
