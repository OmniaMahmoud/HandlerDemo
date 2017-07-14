package omnia.handlerdemo;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Thread.sleep;

/**
 * Created by Lenovo-pc on 14/07/2017.
 */

public class CounterFragment extends Fragment {
    View v;
    int counter=0;
    boolean flag=true;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.counter_fragment,container,false);

        final Button up=(Button)v.findViewById(R.id.up);
        final Button down=(Button)v.findViewById(R.id.down);
        final Button stop=(Button)v.findViewById(R.id.stop);
        final TextView counterText=(TextView)v.findViewById(R.id.counter);
        final Handler h=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Log.e("new message","new message:");
                if(msg.what==1){
                    counterText.setText(""+counter);
                }
            }
        };

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up.setClickable(false);
                down.setClickable(true);
                stop.setClickable(true);
                flag=true;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(flag) {
                                sleep(500);
                                counter++;
                                h.sendEmptyMessage(1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                up.setClickable(true);
                down.setClickable(false);
                stop.setClickable(true);
                flag=false;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while(!flag) {
                                sleep(500);
                                counter--;
                                h.sendEmptyMessage(1);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setClickable(false);
                flag=!flag;
            }
        });
        return v;
    }
}
