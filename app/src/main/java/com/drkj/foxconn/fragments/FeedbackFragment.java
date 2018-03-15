package com.drkj.foxconn.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.R;
import com.drkj.foxconn.activties.FeedbackActivity;
import com.drkj.foxconn.adapter.FeedbackAdapter;
import com.drkj.foxconn.bean.FeedbackBean;
import com.drkj.foxconn.db.DbController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;

public class FeedbackFragment extends Fragment {
    @BindView(R.id.listview_feedback)
    ListView listView;

    FeedbackAdapter mAdapter;

    List<FeedbackBean> feedbackBeanList;

    private AlertDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.image_create_feedback)
    void createFeedback() {
        startActivity(new Intent(getContext(), FeedbackActivity.class));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        MyAdapter adapter = new MyAdapter();
//        listView.setAdapter(adapter);
        feedbackBeanList = DbController.getInstance().queryAllFeedback();
        mAdapter = new FeedbackAdapter(getActivity(), feedbackBeanList);
        listView.setAdapter(mAdapter);
    }

    @OnItemClick(R.id.listview_feedback)
    void itemClick(int position) {
        Intent intent = new Intent(getActivity(), FeedbackActivity.class);
        intent.putExtra("feedbackId", feedbackBeanList.get(position).getId());
        startActivity(intent);
    }

    @OnItemLongClick(R.id.listview_feedback)
    boolean itemLongClick(final int position) {
        dialog = new AlertDialog.Builder(getActivity())
                .setTitle("警告")
                .setMessage("是否删除第" + (position + 1) + "条数据")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DbController.getInstance().deleteFeedback(feedbackBeanList.get(position));
                        feedbackBeanList = DbController.getInstance().queryAllFeedback();
                        mAdapter = new FeedbackAdapter(getActivity(), feedbackBeanList);
                        listView.setAdapter(mAdapter);
                        dialogInterface.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .create();
        dialog.show();
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
//        Toast.makeText(getActivity(), "onStart->Feedback", Toast.LENGTH_SHORT).show();
        feedbackBeanList = DbController.getInstance().queryAllFeedback();
        mAdapter = new FeedbackAdapter(getActivity(), feedbackBeanList);
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
//        Toast.makeText(getActivity(), "onResume->Feedback", Toast.LENGTH_SHORT).show();
        feedbackBeanList = DbController.getInstance().queryAllFeedback();
        mAdapter = new FeedbackAdapter(getActivity(), feedbackBeanList);
        listView.setAdapter(mAdapter);
    }

//    class MyAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return 10;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            MyViewHolder holder = null;
//            if (convertView == null) {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_feedback_list, null);
//                holder = new MyViewHolder();
//                holder.equipmentLocationText = convertView.findViewById(R.id.equipment_falut_tv_location);
//                holder.equipmentNameText = convertView.findViewById(R.id.equipment_fault_tv_name);
//                holder.layout = convertView.findViewById(R.id.equipment_fault_layout_item);
//                convertView.setTag(holder);
//            } else {
//                holder = (MyViewHolder) convertView.getTag();
//            }
//            switch (position % 3) {
//                case 0:
//                    holder.layout.setBackgroundResource(R.drawable.ic_orange_bg);
//                    break;
//                case 1:
//                    holder.layout.setBackgroundResource(R.drawable.ic_green_bg);
//                    break;
//                case 2:
//                    holder.layout.setBackgroundResource(R.drawable.ic_blue_bg);
//                    break;
//            }
//            return convertView;
//        }
//    }
//
//    class MyViewHolder {
//        public TextView equipmentLocationText;
//        public TextView equipmentNameText;
//        public LinearLayout layout;
//    }
}
