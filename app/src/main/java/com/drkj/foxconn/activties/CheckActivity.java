package com.drkj.foxconn.activties;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.adapter.CheckAdapter;
import com.drkj.foxconn.bean.EquipmentResultBean;
import com.drkj.foxconn.bean.RegionResultBean;
import com.drkj.foxconn.db.DbConstant;
import com.drkj.foxconn.db.DbController;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckActivity extends BaseActivity {

    EquipmentResultBean.DataBean bean;

    @BindView(R.id.list_equipment_attr)
    ListView attrList;
    @BindView(R.id.text_equipment_name)
    TextView equipmentNameText;
    @BindView(R.id.text_equipment_code)
    TextView equipmentCodeText;
    @BindView(R.id.text_equipment_location)
    TextView equipmentLocationText;
    @BindView(R.id.text_threshold)
    TextView equipmentThresholdText;

    private CheckAdapter mAdapter;

//    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String nfcCode = getIntent().getStringExtra("nfcCode");
        bean = DbController.getInstance().queryEquipmentByNfcCode(nfcCode);
        equipmentNameText.setText(bean.getName());
        equipmentCodeText.setText(bean.getCode());

        RegionResultBean.DataBean dataBean1 = DbController.getInstance().queryRegionInfoById(bean.getStoreyId());
        RegionResultBean.DataBean dataBean2 = DbController.getInstance().queryRegionInfoById(bean.getRoomId());
        String location = dataBean1.getName() + dataBean2.getName();

        equipmentLocationText.setText(location);
//        adapter = new MyAdapter(bean);
//        attrList.setAdapter(adapter);

        mAdapter = new CheckAdapter(this, bean);
        mAdapter.setOnContentChangedListener(new CheckAdapter.OnContentChangedListener() {
            @Override
            public void onRangeChanged(@NotNull String hint) {
                equipmentThresholdText.setText(hint);
            }
        });
        attrList.setAdapter(mAdapter);

    }

    @OnClick(R.id.image_save_check)
    void saveCheck() {
        DbController.getInstance().updateEquipmentAttribute(mAdapter.getBean());
        for (EquipmentResultBean.DataBean.EquipmentAttributeListBean attributeListBean : mAdapter.getBean().getEquipmentAttributeList()) {
//            DbController.getInstance().updateEquipmentAttribute(attributeListBean);
            DbController.getInstance().updateEquipmentCheck(mAdapter.getBean());
            finish();
        }
    }

    class MyAdapter extends BaseAdapter {

        EquipmentResultBean.DataBean bean;

        public MyAdapter(EquipmentResultBean.DataBean bean) {
            this.bean = bean;
        }

        @Override
        public int getCount() {
            return bean.getEquipmentAttributeList().size();
        }

        @Override
        public Object getItem(int position) {
            return bean.getEquipmentAttributeList().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyHolder holder;
            if (convertView == null) {
                holder = new MyHolder();
                convertView = LayoutInflater.from(CheckActivity.this).inflate(R.layout.item_equipment_attr, null);
                holder.attrNameText = convertView.findViewById(R.id.text_equipment_attr_name);
                holder.attrValueText = convertView.findViewById(R.id.text_equipment_attr_value);
                holder.attrHint = convertView.findViewById(R.id.image_equipment_attr_hint);
                convertView.setTag(holder);
            } else {
                holder = (MyHolder) convertView.getTag();
            }

            final double max = bean.getEquipmentAttributeList().get(position).getMax();
            final double min = bean.getEquipmentAttributeList().get(position).getMin();

            holder.attrNameText.setText(bean.getEquipmentAttributeList().get(position).getName());

            holder.attrValueText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (!TextUtils.isEmpty(charSequence)) {
                        try {
                            if (max <= 0 && Double.parseDouble(charSequence.toString()) > min) {
                                holder.attrHint.setBackgroundColor(Color.GREEN);
                            } else if (max >= 0) {
                                if (Double.parseDouble(charSequence.toString()) < min || Double.parseDouble(charSequence.toString()) > max) {
                                    holder.attrHint.setBackgroundColor(Color.RED);
                                } else {
                                    holder.attrHint.setBackgroundColor(Color.GREEN);
                                }
                            } else {
                                holder.attrHint.setBackgroundColor(Color.parseColor("#ffc000"));
                            }
                            bean.getEquipmentAttributeList().get(position).setValue(Double.parseDouble(charSequence.toString()));
                        } catch (Exception e) {

                        }
                    } else {
                        holder.attrHint.setBackgroundColor(Color.parseColor("#ffc000"));
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            holder.attrValueText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {


                    if (hasFocus) {
                        equipmentThresholdText.setText(min + "~" + max);
                    } else {
                        equipmentThresholdText.setText("");
                    }
                }
            });

            if (!TextUtils.isEmpty(bean.isCheck()) && bean.isCheck().equals("true")) {
                for (int i = 0; i < bean.getEquipmentAttributeList().size(); i++) {
                    holder.attrValueText.setText(String.valueOf(bean.getEquipmentAttributeList().get(i).getValue()));
                }
            }

            return convertView;
        }
    }

    class MyHolder {
        TextView attrNameText;
        EditText attrValueText;
        ImageView attrHint;
    }
}
