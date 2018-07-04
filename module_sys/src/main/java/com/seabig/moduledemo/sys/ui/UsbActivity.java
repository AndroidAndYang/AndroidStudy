package com.seabig.moduledemo.sys.ui;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbConstants;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.seabig.moduledemo.ble.R;
import com.seabig.moduledemo.common.util.LogUtils;
import com.seabig.moduledemo.common.util.StringUtils;
import com.seabig.moduledemo.sys.listener.IAudioListen;
import com.seabig.moduledemo.sys.util.MediaRecorderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * author： YJZ
 * date:  2018/6/20
 * des: usb转串口
 */

public class UsbActivity extends AppCompatActivity implements View.OnClickListener, IAudioListen, SeekBar.OnSeekBarChangeListener {

    private UsbManager usbManager;
    private UsbSerialPort serialPort;
    private TextView textView;
    private TextView seekBarValue;
    private List<UsbSerialDriver> drivers;
    private MediaRecorderUtil mediaRecorderUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sys_activity_usb);

        initView();

        initUsbSerial();
    }

    private void initUsbSerial() {

        // 1.查找设备
        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);

        drivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);

        if (drivers.size() <= 0) {
            Toast.makeText(this, "无串口设备", Toast.LENGTH_SHORT).show();
            return;
        }

        UsbDevice device = drivers.get(0).getDevice();

        if (usbManager.hasPermission(device)) {
            permissionAllow(device);
        } else {
            Log.e("TAG", "没有权限");
            UsbPermissionActionReceiver mUsbPermissionActionReceiver = new UsbPermissionActionReceiver();
            Intent intent = new Intent(ACTION_USB_PERMISSION);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
            IntentFilter intentFilter = new IntentFilter(ACTION_USB_PERMISSION);
            registerReceiver(mUsbPermissionActionReceiver, intentFilter);
            usbManager.requestPermission(device, pendingIntent);
        }

    }

    private void permissionAllow(UsbDevice device) {

        List<UsbSerialPort> result = new ArrayList<>();

        for (final UsbSerialDriver driver : drivers) {
            final List<UsbSerialPort> ports = driver.getPorts();
            result.addAll(ports);
        }

        UsbDeviceConnection usbDeviceConnection = usbManager.openDevice(device);

        try {
            serialPort = result.get(0);
            serialPort.open(usbDeviceConnection);
            serialPort.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        UsbInterface anInterface = device.getInterface(0);

        if (anInterface == null) {
            Toast.makeText(this, "初始化失败", Toast.LENGTH_SHORT).show();
            return;
        }

        // 判断端口号
        for (int i = 0; i < anInterface.getEndpointCount(); i++) {
            UsbEndpoint endpoint = anInterface.getEndpoint(i);
            if (endpoint.getType() == UsbConstants.USB_ENDPOINT_XFER_BULK) {
                if (endpoint.getDirection() == UsbConstants.USB_DIR_IN) {
                    // 输入端口
                    // UsbEndpoint usbEndpointIn = endpoint;
                    LogUtils.e("输入端口");
                } else if (endpoint.getDirection() == UsbConstants.USB_DIR_OUT) {
                    // 输出端口
                    // UsbEndpoint usbEndpointOut = endpoint;
                    LogUtils.e("输出端口");
                }
            }
        }
    }

    private static final String ACTION_USB_PERMISSION = "android.hardware.usb.action.USB_DEVICE_ATTACHED";

    @Override
    public void top() {
        topTurn();
    }

    @Override
    public void pause() {
        pauseTurn();
    }

    @Override
    public void update(final double volume) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(StringUtils.buffer("分贝：", String.valueOf(volume)));
            }
        });
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        MediaRecorderUtil.MAX_VOLUME = progress;
        seekBarValue.setText(String.format(Locale.CHINA, "阀值：%s", progress));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private class UsbPermissionActionReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    UsbDevice usbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        // user choose YES for your previously popup window asking for grant perssion for this usb device
                        if (null != usbDevice) {
                            permissionAllow(usbDevice);
                        }
                    } else {
                        //user choose NO for your previously popup window asking for grant perssion for this usb device
                        Toast.makeText(context, String.valueOf("Permission denied for device" + usbDevice), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    private void initView() {
        findViewById(R.id.study_code).setOnClickListener(this);
        findViewById(R.id.top).setOnClickListener(this);
        findViewById(R.id.pause).setOnClickListener(this);
        findViewById(R.id.bottom).setOnClickListener(this);
        findViewById(R.id.start_audio).setOnClickListener(this);
        findViewById(R.id.stop_audio).setOnClickListener(this);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBarValue = (TextView) findViewById(R.id.seek_bar_value);
        seekBar.setOnSeekBarChangeListener(this);
        textView = (TextView) findViewById(R.id.value);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.study_code) {
            studyCode();

        } else if (i == R.id.top) {
            topTurn();

        } else if (i == R.id.pause) {
            pauseTurn();

        } else if (i == R.id.bottom) {
            bottomTurn();

        } else if (i == R.id.start_audio) {
//                AudioRecordDemo demo = new AudioRecordDemo();
//                demo.setAudioRecordListener(this);
//                demo.getNoiseLevel();

            mediaRecorderUtil = new MediaRecorderUtil();
            mediaRecorderUtil.setListen(this);
            if (MediaRecorderUtil.isGetVoiceRun) {
                Toast.makeText(UsbActivity.this, "已经在监听了", Toast.LENGTH_SHORT).show();
                return;
            }
            MediaRecorderUtil.isGetVoiceRun = true;
            mediaRecorderUtil.startRecord();


        } else if (i == R.id.stop_audio) {//  AudioRecordDemo.isGetVoiceRun = false;
            if (!MediaRecorderUtil.isGetVoiceRun) {
                Toast.makeText(UsbActivity.this, "请先打开监听", Toast.LENGTH_SHORT).show();
                return;
            }
            mediaRecorderUtil.stop();

        }
    }


    private void bottomTurn() {
        try {
            String[] bottomStrArr = "0X55 0X4C 0X42 0X00 0X00 0X00 0X00 0X01 0X00 0X00 0X00 0X00 0X00 0X00 0X00 0X01 0X0C 0X00 0XF1".replace("X", "x").split(" ");
            sendToUsb(bottomStrArr);
        } catch (Exception e) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void pauseTurn() {
        try {
            String[] pauseStrArr = "0X55 0X4C 0X42 0X00 0X00 0X00 0X00 0X01 0X00 0X00 0X00 0X00 0X00 0X00 0X00 0X01 0X0D 0X00 0XF2".replace("X", "x").split(" ");
            sendToUsb(pauseStrArr);
        } catch (Exception e) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void topTurn() {
        try {
            String[] topStrArr = "0X55 0X4C 0X42 0X00 0X00 0X00 0X00 0X01 0X00 0X00 0X00 0X00 0X00 0X00 0X00 0X01 0X0E 0X00 0XF3".replace("X", "x").split(" ");
            sendToUsb(topStrArr);
        } catch (Exception e) {
            Toast.makeText(this, "失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void studyCode() {
        try {
            String[] studyCodeStrArr = "0X55 0X4C 0X42 0X00 0X00 0X00 0X00 0X01 0X00 0X00 0X00 0X00 0X00 0X00 0X00 0X01 0X0A 0X00 0XEF".replace("X", "x").split(" ");
            sendToUsb(studyCodeStrArr);
        } catch (Exception e) {
            Toast.makeText(this, "学码失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void sendToUsb(String[] hexString) throws Exception {
        byte[] bytes = new byte[hexString.length];
        for (int i = 0; i < hexString.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexString[i].substring(2), 16);
        }
        serialPort.write(bytes, bytes.length);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // AudioRecordUtil.isGetVoiceRun = false;
        mediaRecorderUtil.stop();
    }
}
