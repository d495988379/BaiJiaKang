package com.zlxn.dl.baijiakang.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Outline;

import android.os.Build;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.jess.arms.utils.DataHelper;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.stx.xhb.androidx.XBanner;
import com.zlxn.dl.baijiakang.R;
import com.zlxn.dl.baijiakang.activity.JyDetailsActivity;
import com.zlxn.dl.baijiakang.activity.LoginActivity;
import com.zlxn.dl.baijiakang.adapter.JyAdapter;
import com.zlxn.dl.baijiakang.base.BaseAdapter;
import com.zlxn.dl.baijiakang.base.BaseFragment;
import com.zlxn.dl.baijiakang.bean.JyListBean;
import com.zlxn.dl.baijiakang.http.MyObserver;
import com.zlxn.dl.baijiakang.http.RetrofitUtils;
import com.zlxn.dl.baijiakang.http.RxHelper;
import com.zlxn.dl.baijiakang.utils.DimensionConvert;
import com.zlxn.dl.baijiakang.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * @author DL
 * @name BaiJiaKang
 * @class describe
 * @time 2020/10/13 17:50
 */
public class JyFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.homeStateBar)
    View homeStateBar;
    @BindView(R.id.homeBanner)
    XBanner homeBanner;
    @BindView(R.id.jyRv)
    RecyclerView jyRv;
    @BindView(R.id.jyTypeTv)
    TextView jyTypeTv;
    @BindView(R.id.jyDistance)
    TextView jyDistance;
    @BindView(R.id.jyMoney)
    TextView jyMoney;
    @BindView(R.id.jyType)
    LinearLayout jyType;
    @BindView(R.id.swipeToLoadLayout)
    SwipeToLoadLayout swipeToLoadLayout;
    private List<String> typePv = new ArrayList<>();
    private OptionsPickerView pv;
    private String type = "OIL_2";
    private String locationType = "1";
    private int page = 1;
    private double lat;
    private double lon;
    //private LocationManager locationManager;
    private MyObserver<JyListBean> myObserver;
    private JyAdapter adapter;
    private List<JyListBean.DataBean> data;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();


    @Override
    protected int getContentViewLayout() {
        return R.layout.frg_jy;
    }

    @Override
    protected void initView() {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) homeStateBar.getLayoutParams();
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        lp.height = getStatusBar();
        homeStateBar.setLayoutParams(lp);

        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);


        option.setCoorType("GCJ02");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(5000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setNeedNewVersionRgc(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true

        mLocationClient.setLocOption(option);
        mLocationClient.start();


        swipeToLoadLayout.setTargetView(jyRv);
        swipeToLoadLayout.setLoadMoreFooterView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_classic_footer, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnLoadMoreListener(this);
        swipeToLoadLayout.setRefreshHeaderView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_twitter_header, swipeToLoadLayout, false));
        swipeToLoadLayout.setOnRefreshListener(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            homeBanner.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 20);
                }
            });
            homeBanner.setClipToOutline(true);
        }

        jyRv.setLayoutManager(new LinearLayoutManager(getActivity()));


        pv = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                page = 1;
                jyTypeTv.setText(typePv.get(options1));
                if (typePv.get(options1).equals("90")) {
                    type = "OIL_1";
                } else if (typePv.get(options1).equals("92#")) {
                    type = "OIL_2";
                } else if (typePv.get(options1).equals("95#")) {
                    type = "OIL_3";
                } else if (typePv.get(options1).equals("98#")) {
                    type = "OIL_4";
                } else if (typePv.get(options1).equals("101#")) {
                    type = "OIL_5";
                } else if (typePv.get(options1).equals("-40#")) {
                    type = "OIL_6";
                } else if (typePv.get(options1).equals("-35#")) {
                    type = "OIL_7";
                } else if (typePv.get(options1).equals("-30#")) {
                    type = "OIL_8";
                } else if (typePv.get(options1).equals("-20#")) {
                    type = "OIL_9";
                } else if (typePv.get(options1).equals("-10#")) {
                    type = "OIL_10";
                } else if (typePv.get(options1).equals("国四0#")) {
                    type = "OIL_11";
                } else if (typePv.get(options1).equals("0#")) {
                    type = "OIL_12";
                } else if (typePv.get(options1).equals("CNG")) {
                    type = "OIL_13";
                } else if (typePv.get(options1).equals("LNG")) {
                    type = "OIL_14";
                }

                myObserver = new MyObserver<JyListBean>(getActivity(), true) {
                    @Override
                    public void onSuccess(JyListBean result) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            data = result.getData();
                            adapter = new JyAdapter(getActivity(), R.layout.item_jy, result.getData(), jyTypeTv.getText().toString());
                            jyRv.setAdapter(adapter);

                            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    new XPopup.Builder(getContext())
                                            .autoOpenSoftInput(false)
                                            .maxHeight(DimensionConvert.dip2px(getActivity(), 300))
                                            .maxWidth(DimensionConvert.dip2px(getActivity(), 280))
                                            .asCustom(new CustomPopup(getActivity(), result.getData().get(position).getGasid(), result.getData().get(position).getGaschannel(), result.getData().get(position)))
                                            .show();
                                }
                            });
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
                            } else if (errorCode.equals("402")) {
                                data = new ArrayList<>();
                                adapter = new JyAdapter(getActivity(), R.layout.item_jy, data, jyTypeTv.getText().toString());
                                jyRv.setAdapter(adapter);
                            } else {
                                ToastUtils.show(getActivity(), errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().jyList(lat + "", lon + "", type, page + "", "10", locationType, DataHelper.getStringSF(getActivity(), "token"))
                        .compose(RxHelper.observableIO2Main(getActivity()))
                        .subscribe(myObserver);

            }
        })

                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setSubCalSize(15)
                .setLineSpacingMultiplier(1.8F)
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTextColorCenter(getResources().getColor(R.color.main_color))//设置选中项的颜色
                .setSubmitColor(getResources().getColor(R.color.main_color))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.main_color))//取消按钮文字颜色
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pv.setPicker(typePv);


        jyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pv.show();
            }
        });



        jyDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationType = "1";
                page = 1;

                myObserver = new MyObserver<JyListBean>(getActivity(), true) {
                    @Override
                    public void onSuccess(JyListBean result) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            data = result.getData();
                            adapter = new JyAdapter(getActivity(), R.layout.item_jy, result.getData(), jyTypeTv.getText().toString());
                            jyRv.setAdapter(adapter);

                            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    new XPopup.Builder(getContext())
                                            .autoOpenSoftInput(false)
                                            .maxHeight(DimensionConvert.dip2px(getActivity(), 300))
                                            .maxWidth(DimensionConvert.dip2px(getActivity(), 280))
                                            .asCustom(new CustomPopup(getActivity(), result.getData().get(position).getGasid(), result.getData().get(position).getGaschannel(), result.getData().get(position)))
                                            .show();
                                }
                            });
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
                            } else if (errorCode.equals("402")) {
                                data = new ArrayList<>();
                                adapter = new JyAdapter(getActivity(), R.layout.item_jy, data, jyTypeTv.getText().toString());
                                jyRv.setAdapter(adapter);
                            } else {
                                ToastUtils.show(getActivity(), errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().jyList(lat + "", lon + "", type, page + "", "10", locationType, DataHelper.getStringSF(getActivity(), "token"))
                        .compose(RxHelper.observableIO2Main(getActivity()))
                        .subscribe(myObserver);


            }
        });

        jyMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationType = "2";
                page = 1;

                myObserver = new MyObserver<JyListBean>(getActivity(), true) {
                    @Override
                    public void onSuccess(JyListBean result) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            data = result.getData();
                            adapter = new JyAdapter(getActivity(), R.layout.item_jy, result.getData(), jyTypeTv.getText().toString());
                            jyRv.setAdapter(adapter);

                            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    new XPopup.Builder(getContext())
                                            .autoOpenSoftInput(false)
                                            .maxHeight(DimensionConvert.dip2px(getActivity(), 300))
                                            .maxWidth(DimensionConvert.dip2px(getActivity(), 280))
                                            .asCustom(new CustomPopup(getActivity(), result.getData().get(position).getGasid(), result.getData().get(position).getGaschannel(), result.getData().get(position)))
                                            .show();
                                }
                            });
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
                            } else if (errorCode.equals("402")) {
                                data = new ArrayList<>();
                                adapter = new JyAdapter(getActivity(), R.layout.item_jy, data, jyTypeTv.getText().toString());
                                jyRv.setAdapter(adapter);
                            } else {
                                ToastUtils.show(getActivity(), errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().jyList(lat + "", lon + "", type, page + "", "10", locationType, DataHelper.getStringSF(getActivity(), "token"))
                        .compose(RxHelper.observableIO2Main(getActivity()))
                        .subscribe(myObserver);
            }
        });

    }

    @Override
    protected void initData() {

        typePv.add("90");
        typePv.add("92#");
        typePv.add("95#");
        typePv.add("98#");
        typePv.add("101#");
        typePv.add("-40#");
        typePv.add("-35#");
        typePv.add("-30#");
        typePv.add("-20#");
        typePv.add("-10#");
        typePv.add("国四0#");
        typePv.add("0#");
        typePv.add("CNG");
        typePv.add("LNG");
        //  getLocation();


    }


    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            lat = location.getLatitude();    //获取纬度信息
            lon = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

            if (errorCode == 61 || errorCode == 161){
                mLocationClient.stop();
                myObserver = new MyObserver<JyListBean>(getActivity(), true) {
                    @Override
                    public void onSuccess(JyListBean result) {
                        if (result.getData() != null && result.getData().size() > 0) {
                            data = result.getData();
                            adapter = new JyAdapter(getActivity(), R.layout.item_jy, result.getData(), jyTypeTv.getText().toString());
                            jyRv.setAdapter(adapter);

                            adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    new XPopup.Builder(getContext())
                                            .autoOpenSoftInput(false)
                                            .maxHeight(DimensionConvert.dip2px(getActivity(), 300))
                                            .maxWidth(DimensionConvert.dip2px(getActivity(), 280))
                                            .asCustom(new CustomPopup(getActivity(), result.getData().get(position).getGasid(), result.getData().get(position).getGaschannel(), result.getData().get(position)))
                                            .show();
                                }
                            });
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
                            } else if (errorCode.equals("402")) {
                                data = new ArrayList<>();
                                adapter = new JyAdapter(getActivity(), R.layout.item_jy, data, jyTypeTv.getText().toString());
                                jyRv.setAdapter(adapter);
                            } else {
                                ToastUtils.show(getActivity(), errorMsg);
                            }
                        }
                    }
                };

                RetrofitUtils.getApiUrl().jyList(lat + "", lon + "", type, page + "", "10", locationType, DataHelper.getStringSF(getActivity(), "token"))
                        .compose(RxHelper.observableIO2Main(getActivity()))
                        .subscribe(myObserver);
            }else {
                ToastUtils.show(getActivity(),"定位错误码：" + errorCode);
            }


        }


    }
   /* private void getLocation() {
        //1.获取系统LocationManager服务
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);//低精度，中精度高精度获取不到location。
        criteria.setAltitudeRequired(false);//不要求海拔
        criteria.setBearingRequired(false);//不要求方位
        criteria.setCostAllowed(true);//允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);//低功耗

        String locationProvider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(null, "onCreate: 没有权限 ");
            return;
        }

        //2.获取GPS最近的定位信息
        Location location = locationManager.getLastKnownLocation(locationProvider);

        //3.将location里面的位置信息展示在edittext中
        updateLocation(location);
        //4.设置没两秒获取一次GPS的定位信息

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, locationListener);

    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };*/


  /*  private void updateLocation(Location location) {
        if (location != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("实时的位置信息：\n经度：");
            sb.append(location.getLongitude());
            sb.append("\n纬度：");
            sb.append(location.getLatitude());
            sb.append("\n高度：");
            sb.append(location.getAltitude());
            sb.append("\n速度：");
            sb.append(location.getSpeed());
            sb.append("\n方向：");
            sb.append(location.getBearing());
            sb.append("\n精度：");
            sb.append(location.getAccuracy());
            Log.e("location_content", sb.toString());
            lat = location.getLatitude();
            lon = location.getLongitude();
            if (locationManager != null) {
                locationManager.removeUpdates(locationListener);
            }
        }
    }
*/

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
    public void onLoadMore() {
        page = page + 1;

        myObserver = new MyObserver<JyListBean>(getActivity(), false) {
            @Override
            public void onSuccess(JyListBean result) {
                swipeToLoadLayout.setLoadingMore(false);
                if (result.getData() != null && result.getData().size() > 0) {
                    loadRecyclerView(result.getData());
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setLoadingMore(false);
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

        RetrofitUtils.getApiUrl().jyList(lat + "", lon + "", type, page + "", "10", locationType, DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myObserver);
    }

    private void loadRecyclerView(List<JyListBean.DataBean> data) {
        int position = adapter.getItemCount() - 1;
        adapter.loadMoreData(data);
    }


    @Override
    public void onRefresh() {
        page = 1;

        myObserver = new MyObserver<JyListBean>(getActivity(), false) {
            @Override
            public void onSuccess(JyListBean result) {
                swipeToLoadLayout.setRefreshing(false);
                if (result.getData() != null && result.getData().size() > 0) {
                    data = result.getData();
                    adapter = new JyAdapter(getActivity(), R.layout.item_jy, result.getData(), jyTypeTv.getText().toString());
                    jyRv.setAdapter(adapter);

                    adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            new XPopup.Builder(getContext())
                                    .autoOpenSoftInput(false)
                                    .maxHeight(DimensionConvert.dip2px(getActivity(), 300))
                                    .maxWidth(DimensionConvert.dip2px(getActivity(), 280))
                                    .asCustom(new CustomPopup(getActivity(), result.getData().get(position).getGasid(), result.getData().get(position).getGaschannel(), result.getData().get(position)))
                                    .show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Throwable e, String errorCode, String errorMsg) {
                swipeToLoadLayout.setRefreshing(false);
                if (e != null) {
                    ToastUtils.show(getActivity(), errorCode);
                } else {
                    if (errorCode.equals("401")) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else if (errorCode.equals("402")) {
                        data = new ArrayList<>();
                        adapter = new JyAdapter(getActivity(), R.layout.item_jy, data, jyTypeTv.getText().toString());
                        jyRv.setAdapter(adapter);
                    } else {
                        ToastUtils.show(getActivity(), errorMsg);
                    }
                }
            }
        };


        RetrofitUtils.getApiUrl().jyList(lat + "", lon + "", type, page + "", "10", locationType, DataHelper.getStringSF(getActivity(), "token"))
                .compose(RxHelper.observableIO2Main(getActivity()))
                .subscribe(myObserver);


    }

    public class CustomPopup extends CenterPopupView {

        private String id;
        private String gaschannel;
        private JyListBean.DataBean bean;

        public CustomPopup(@NonNull Context context, String id, String gaschannel, JyListBean.DataBean bean) {
            super(context);
            this.id = id;
            this.gaschannel = gaschannel;
            this.bean = bean;
        }

        @Override
        protected int getImplLayoutId() {
            return R.layout.custom_center_popup;
        }

        @Override
        protected void onCreate() {
            super.onCreate();
            TextView tv1 = findViewById(R.id.popupTv1);
            TextView tv2 = findViewById(R.id.popupTv2);
            TextView nameTv = findViewById(R.id.popupNameTv);

            nameTv.setText("当前位置：" + bean.getGasName());
            tv1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });

            tv2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    if (gaschannel.equals("CZB")) {
                        startActivity(new Intent(getActivity(), JyDetailsActivity.class)
                                .putExtra("id", id)
                                .putExtra("data", new Gson().toJson(bean)));
                    }

                }
            });
        }

        protected void onShow() {
            super.onShow();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null){
            mLocationClient.stop();
            mLocationClient = null;
        }
      /*  if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }*/
        if (myObserver != null) {
            myObserver.cancleRequest();
        }
    }
}
