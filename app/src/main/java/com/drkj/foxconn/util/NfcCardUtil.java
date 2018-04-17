package com.drkj.foxconn.util;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.drkj.foxconn.db.DbConstant;

import java.io.IOException;

/**
 * NFC卡片读取工具
 * Created by VeronicaRen on 2018/3/13 in Java
 */
public class NfcCardUtil {

    private AppCompatActivity mActivity;

    private NfcAdapter mNfcAdapter;

    private PendingIntent mPendingIntent;

    public NfcCardUtil(AppCompatActivity activity) {
        this.mActivity = activity;
        mNfcAdapter = NfcAdapter.getDefaultAdapter(mActivity);
        mPendingIntent = PendingIntent.getActivity(mActivity, 0,
                new Intent(mActivity, mActivity.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    public void startNfc() {
        if (mNfcAdapter != null)
            mNfcAdapter.enableForegroundDispatch(mActivity, mPendingIntent, null, null);
    }

    public void stopNfc() {
        if (mNfcAdapter != null)
            mNfcAdapter.disableForegroundDispatch(mActivity);
    }

    public String readData(Intent intent) {
        String action = intent.getAction();
        String data = "ooo";
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            data = readTag(tag);
        }
        return data;
    }

    public String formatNfcCode(String nfcData) {
        if (!TextUtils.isEmpty(nfcData) && !nfcData.contains(DbConstant.NFC_HEAD)) {
            nfcData = DbConstant.NFC_HEAD + nfcData.trim().substring(0, 6);
        }
        return nfcData;
    }

    private String readTag(Tag tag) {
        MifareClassic mfc = MifareClassic.get(tag);
        String finalData = null;
        try {
            mfc.connect();

            int sectorCount = mfc.getSectorCount();//扇区数

            boolean auth;

            int blockCount;
            int blockIndex;

            for (int i = 0; i < sectorCount; i++) {
                auth = mfc.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT);//逐个获取密码

                blockCount = mfc.getBlockCountInSector(i);
                blockIndex = mfc.sectorToBlock(i);

                if (auth && i == 0) {
                    for (int j = 0; j < blockCount; j++) {
                        if (j == 1) {
                            byte[] data = mfc.readBlock(blockIndex);
                            finalData = byteArrayToHexString(data);
                            Log.e("nfc", finalData);
                            break;
                        }
                        blockIndex++;
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            if (mfc != null) {
                try {
                    mfc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return finalData;
    }

    public String readPointData(Intent intent, int sector, int block) {//获取指定扇区、块的数据
        String action = intent.getAction();
        String data = "";
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            MifareClassic mfc = MifareClassic.get(tag);
            try {
                mfc.connect();

                int sectorCount = mfc.getSectorCount();//扇区数

                boolean auth;

                int blockCount;
                int blockIndex;

                for (int i = 0; i < sectorCount; i++) {
                    auth = mfc.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT);//逐个获取密码

                    blockCount = mfc.getBlockCountInSector(i);
                    blockIndex = mfc.sectorToBlock(i);

                    if (auth && i == sector) {
                        for (int j = 0; j < blockCount; j++) {
                            if (j == block) {
                                byte[] tempData = mfc.readBlock(blockIndex);
                                data = byteArrayToHexString(tempData);
                                Log.e("nfc", data);
                                break;
                            }
                            blockIndex++;
                        }
                    }
                }

            } catch (Exception e) {

            } finally {
                if (mfc != null) {
                    try {
                        mfc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return data;
    }

    public String readNfcCode(Intent intent) {
        String action = intent.getAction();
        String data = "";
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            MifareClassic mfc = MifareClassic.get(tag);
            try {
                mfc.connect();

                int sectorCount = mfc.getSectorCount();//扇区数

                boolean auth;

                int blockCount;
                int blockIndex;

                for (int i = 0; i < sectorCount; i++) {
                    auth = mfc.authenticateSectorWithKeyA(i, MifareClassic.KEY_DEFAULT);//逐个获取密码

                    blockCount = mfc.getBlockCountInSector(i);
                    blockIndex = mfc.sectorToBlock(i);

                    if (auth && i == 0) {
                        for (int j = 0; j < blockCount; j++) {
                            if (j == 1) {
                                byte[] tempData = mfc.readBlock(blockIndex);
                                data = byteArrayToHexString(tempData);
                                Log.e("nfc", data);
                                break;
                            }
                            blockIndex++;
                        }
                    }
                }

            } catch (Exception e) {

            } finally {
                if (mfc != null) {
                    try {
                        mfc.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(data)) {
            return DbConstant.NFC_HEAD + data.trim().substring(0, 6);
        } else {
            return null;
        }
    }

    private String byteArrayToHexString(byte[] bytesId) {   //Byte数组转换为16进制字符串
        int i, j, in;
        String[] hex = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"
        };
        StringBuilder output = new StringBuilder();

        for (j = 0; j < bytesId.length; ++j) {
            in = bytesId[j] & 0xff;
            i = (in >> 4) & 0x0f;
            output.append(hex[i]);
            i = in & 0x0f;
            output.append(hex[i]);
        }
        return output.toString();
    }
}
