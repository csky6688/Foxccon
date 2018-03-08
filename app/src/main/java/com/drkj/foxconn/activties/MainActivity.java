package com.drkj.foxconn.activties;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drkj.foxconn.BaseActivity;
import com.drkj.foxconn.R;
import com.drkj.foxconn.fragments.DataSynchronizationFragment;
import com.drkj.foxconn.fragments.EquipmentFaultFragment;
import com.drkj.foxconn.fragments.FeedbackFragment;
import com.drkj.foxconn.fragments.OfflineCheckFragment;
import com.zltd.decoder.DecoderManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements DecoderManager.IDecoderStatusListener {

    public final int TAB_DATA_SYNC = 0;
    public final int TAB_OFFLINE_CHECK = 1;
    public final int TAB_FEEDBACK = 2;
    public final int TAB_FAULT = 3;

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    List<Fragment> fragments;
    private DataSynchronizationFragment dataSynchronizationFragment;
    private OfflineCheckFragment offlineCheckFragment;
    private FeedbackFragment feedbackFragment;
    private EquipmentFaultFragment equipmentFaultFragment;
    private FragmentPagerAdapter pagerAdapter;
    @BindView(R.id.image_data_synchronization)
    ImageView dataSynchronizationImage;

    @BindView(R.id.image_offline_check)
    ImageView offlineCheckImage;
    @BindView(R.id.image_scene_feedback)
    ImageView feedbackImage;
    @BindView(R.id.image_equipment_fault)
    ImageView equipmentFaultImage;

    @BindView(R.id.text_title)
    TextView titleText;
    //    private NfcAdapter mAdapter;
    private PendingIntent mPendingIntent;

    //    DecoderManager mDecoderMgr;
    protected boolean isOnResume = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();

//        mAdapter = NfcAdapter.getDefaultAdapter(this);
//        if (mAdapter == null) {
//            finish();
//            return;
//        }
        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
//        mNdefPushMessage = new NdefMessage(new NdefRecord[] { newTextRecord(
//                "Message from NFC Reader :-)", Locale.ENGLISH, true) });
//        mDecoderMgr = DecoderManager.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnResume = true;
//        if (mAdapter != null) {
//            if (!mAdapter.isEnabled()) {
////                showWirelessSettingsDialog();
//            }
//            mAdapter.enableForegroundDispatch(this, mPendingIntent, null, null);
//        }

        try {
//            mDecoderMgr.connectDecoderSRV();
        } catch (Exception e) {
            Toast.makeText(this, "该设备可能不是工程机", Toast.LENGTH_SHORT).show();
        }
//        mDecoderMgr.addDecoderStatusListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isOnResume = false;
//        if (mAdapter != null) {
//            mAdapter.disableForegroundDispatch(this);
//        }
//        mDecoderMgr.removeDecoderStatusListener(this);
//        mDecoderMgr.disconnectDecoderSRV();
    }

    private void initView() {
        dataSynchronizationFragment = new DataSynchronizationFragment();
        offlineCheckFragment = new OfflineCheckFragment();
        feedbackFragment = new FeedbackFragment();
        equipmentFaultFragment = new EquipmentFaultFragment();
        fragments = new ArrayList<>();
        fragments.add(dataSynchronizationFragment);
        fragments.add(offlineCheckFragment);
        fragments.add(feedbackFragment);
        fragments.add(equipmentFaultFragment);
        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                dataSynchronizationImage.setImageResource(R.drawable.ic_data_synchronization_unselected);
                offlineCheckImage.setImageResource(R.drawable.ic_offline_check_unselected);
                feedbackImage.setImageResource(R.drawable.ic_scene_feedback_unselected);
                equipmentFaultImage.setImageResource(R.drawable.ic_equipment_fault_unselected);
                switch (position) {
                    case TAB_DATA_SYNC:
                        dataSynchronizationImage.setImageResource(R.drawable.ic_data_synchronization_selected);
                        titleText.setText("数据同步");
                        break;
                    case TAB_OFFLINE_CHECK:
                        offlineCheckImage.setImageResource(R.drawable.ic_offline_check_selected);
                        titleText.setText("巡检作业");
                        pagerAdapter.getItem(position).onResume();//刷新数据
                        break;
                    case TAB_FEEDBACK:
                        feedbackImage.setImageResource(R.drawable.ic_scene_feedback_selected);
                        titleText.setText("现场反馈");
                        break;
                    case TAB_FAULT:
                        equipmentFaultImage.setImageResource(R.drawable.ic_equipment_fault_selected);
                        titleText.setText("设备故障");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(3);
    }

    @OnClick({R.id.image_data_synchronization, R.id.image_offline_check, R.id.image_scene_feedback, R.id.image_equipment_fault})
    void changePage(View view) {
        switch (view.getId()) {
            case R.id.image_data_synchronization:
                viewPager.setCurrentItem(TAB_DATA_SYNC);
                break;
            case R.id.image_offline_check:
                viewPager.setCurrentItem(TAB_OFFLINE_CHECK);
                break;
            case R.id.image_scene_feedback:
                viewPager.setCurrentItem(TAB_FEEDBACK);
                break;
            case R.id.image_equipment_fault:
                viewPager.setCurrentItem(TAB_FAULT);
                break;
            default:
                break;
        }
    }

    public void setCurrentPage(int page) {
        viewPager.setCurrentItem(page);
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        resolveIntent(intent);
    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage[] msgs;
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[0];
                byte[] id = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
                Tag tag = (Tag) intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
                byte[] payload = dumpTagData(tag).getBytes();
                Toast.makeText(getApplicationContext(), dumpTagData(tag), Toast.LENGTH_LONG).show();
//                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, id, payload);
//                NdefMessage msg = new NdefMessage(new NdefRecord[] { record });
//                msgs = new NdefMessage[] { msg };
//                mTags.add(tag);
            }
            // Setup the views
//            buildTagViews(msgs);
        }
    }

    private String dumpTagData(Tag tag) {
        StringBuilder sb = new StringBuilder();
        byte[] id = tag.getId();
//        sb.append("ID (hex): ").append(toHex(id)).append('\n');
//        sb.append("ID (reversed hex): ").append(toReversedHex(id)).append('\n');
//        sb.append("ID (dec): ").append(toDec(id)).append('\n');
//        sb.append("ID (reversed dec): ").append(toReversedDec(id)).append('\n');

        String prefix = "android.nfc.tech.";
        sb.append("Technologies: ");
        for (String tech : tag.getTechList()) {
            sb.append(tech.substring(prefix.length()));
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        for (String tech : tag.getTechList()) {
            if (tech.equals(MifareClassic.class.getName())) {
                sb.append('\n');
                String type = "Unknown";
                try {
                    MifareClassic mifareTag;
                    try {
                        mifareTag = MifareClassic.get(tag);
                    } catch (Exception e) {
                        // Fix for Sony Xperia Z3/Z5 phones
                        tag = cleanupTag(tag);
                        mifareTag = MifareClassic.get(tag);
                    }
                    switch (mifareTag.getType()) {
                        case MifareClassic.TYPE_CLASSIC:
                            type = "Classic";
                            break;
                        case MifareClassic.TYPE_PLUS:
                            type = "Plus";
                            break;
                        case MifareClassic.TYPE_PRO:
                            type = "Pro";
                            break;
                    }
                    sb.append("Mifare Classic type: ");
                    sb.append(type);
                    sb.append('\n');

                    sb.append("Mifare size: ");
                    sb.append(mifareTag.getSize() + " bytes");
                    sb.append('\n');

                    sb.append("Mifare sectors: ");
                    sb.append(mifareTag.getSectorCount());
                    sb.append('\n');

                    sb.append("Mifare blocks: ");
                    sb.append(mifareTag.getBlockCount());
                } catch (Exception e) {
                    sb.append("Mifare classic error: " + e.getMessage());
                }
            }

            if (tech.equals(MifareUltralight.class.getName())) {
                sb.append('\n');
                MifareUltralight mifareUlTag = MifareUltralight.get(tag);
                String type = "Unknown";
                switch (mifareUlTag.getType()) {
                    case MifareUltralight.TYPE_ULTRALIGHT:
                        type = "Ultralight";
                        break;
                    case MifareUltralight.TYPE_ULTRALIGHT_C:
                        type = "Ultralight C";
                        break;
                }
                sb.append("Mifare Ultralight type: ");
                sb.append(type);
            }
        }

        return sb.toString();
    }

    private Tag cleanupTag(Tag oTag) {
        if (oTag == null)
            return null;

        String[] sTechList = oTag.getTechList();

        Parcel oParcel = Parcel.obtain();
        oTag.writeToParcel(oParcel, 0);
        oParcel.setDataPosition(0);

        int len = oParcel.readInt();
        byte[] id = null;
        if (len >= 0) {
            id = new byte[len];
            oParcel.readByteArray(id);
        }
        int[] oTechList = new int[oParcel.readInt()];
        oParcel.readIntArray(oTechList);
        Bundle[] oTechExtras = oParcel.createTypedArray(Bundle.CREATOR);
        int serviceHandle = oParcel.readInt();
        int isMock = oParcel.readInt();
        IBinder tagService;
        if (isMock == 0) {
            tagService = oParcel.readStrongBinder();
        } else {
            tagService = null;
        }
        oParcel.recycle();

        int nfca_idx = -1;
        int mc_idx = -1;
        short oSak = 0;
        short nSak = 0;

        for (int idx = 0; idx < sTechList.length; idx++) {
            if (sTechList[idx].equals(NfcA.class.getName())) {
                if (nfca_idx == -1) {
                    nfca_idx = idx;
                    if (oTechExtras[idx] != null && oTechExtras[idx].containsKey("sak")) {
                        oSak = oTechExtras[idx].getShort("sak");
                        nSak = oSak;
                    }
                } else {
                    if (oTechExtras[idx] != null && oTechExtras[idx].containsKey("sak")) {
                        nSak = (short) (nSak | oTechExtras[idx].getShort("sak"));
                    }
                }
            } else if (sTechList[idx].equals(MifareClassic.class.getName())) {
                mc_idx = idx;
            }
        }

        boolean modified = false;

        if (oSak != nSak) {
            oTechExtras[nfca_idx].putShort("sak", nSak);
            modified = true;
        }

        if (nfca_idx != -1 && mc_idx != -1 && oTechExtras[mc_idx] == null) {
            oTechExtras[mc_idx] = oTechExtras[nfca_idx];
            modified = true;
        }

        if (!modified) {
            return oTag;
        }

        Parcel nParcel = Parcel.obtain();
        nParcel.writeInt(id.length);
        nParcel.writeByteArray(id);
        nParcel.writeInt(oTechList.length);
        nParcel.writeIntArray(oTechList);
        nParcel.writeTypedArray(oTechExtras, 0);
        nParcel.writeInt(serviceHandle);
        nParcel.writeInt(isMock);
        if (isMock == 0) {
            nParcel.writeStrongBinder(tagService);
        }
        nParcel.setDataPosition(0);

        Tag nTag = Tag.CREATOR.createFromParcel(nParcel);

        nParcel.recycle();

        return nTag;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_A:
                if (isOnResume) {
//                    mDecoderMgr.dispatchScanKeyEvent(event);
                }
                return true;
            default:
                return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_A:
                if (isOnResume) {
//                    mDecoderMgr.dispatchScanKeyEvent(event);
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }

    @Override
    public void onDecoderStatusChanage(int i) {

    }

    /**
     * 扫描结果回调
     *
     * @param s  返回结果
     * @param s1 扫描用时
     */
    @Override
    public void onDecoderResultChanage(final String s, final String s1) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, s + "," + s1, Toast.LENGTH_LONG).show();
            }
        });
    }
}
