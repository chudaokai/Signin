package com.chudk.signin.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.DialogPreference;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

/**
 * Created by chudaokai on 2019/2/1.
 */
public class ControlUtil {
    public static boolean setButtonEanble(Button btn, boolean enabled){
        btn.setEnabled(enabled);
        return true;
    }

    public static boolean showDialog(Context context, String text){
        AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                .setTitle("提示信息")
                .setMessage(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
        alertDialog2.show();
        return true;
    }

}
