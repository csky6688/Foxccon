package com.drkj.foxconn.fragments;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.EquipmentFaultActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;


public class EquipmentFaultFragment extends Fragment {
    @BindView(R.id.listview_fault)
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_equipment_fault, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.image_create_feedback)
    void createFeedback() {
        startActivity(new Intent(getContext(), EquipmentFaultActivity.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }

    @OnItemClick(R.id.listview_fault)
    void itemClick(int postion) {
        startActivity(new Intent(getActivity(), EquipmentFaultActivity.class));
    }

    @OnItemLongClick(R.id.listview_fault)
    boolean itemLongClick(int postion) {
        final Dialog dialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        LinearLayout root = (LinearLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.dialog_list_item_control, null);
        dialog.setContentView(root);
        dialog.findViewById(R.id.text_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.text_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        dialogWindow.setAttributes(lp);
        dialog.show();
        return true;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_equipment_fault_list, null);
                holder = new MyViewHolder();
                holder.equipmentLocationText = convertView.findViewById(R.id.text_equipment_location);
                holder.equipmentNameText = convertView.findViewById(R.id.text_equipment_name);
                holder.layout = convertView.findViewById(R.id.layout_item);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }
            switch (position % 3) {
                case 0:
                    holder.layout.setBackgroundResource(R.drawable.ic_orange_bg);
                    break;
                case 1:
                    holder.layout.setBackgroundResource(R.drawable.ic_green_bg);
                    break;
                case 2:
                    holder.layout.setBackgroundResource(R.drawable.ic_blue_bg);
                    break;
            }
            return convertView;
        }
    }

    class MyViewHolder {
        public TextView equipmentLocationText;
        public TextView equipmentNameText;
        public LinearLayout layout;
    }
}
