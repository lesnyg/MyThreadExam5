package com.lesnyg.mythreadexam5;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements CountDownFragment.OnCountDownFragmentClick {
    private TextView mCountText;
    private CountDownFragment mCountDownFragment;
    private CountDownTask mCountDownTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCountText = findViewById(R.id.view_text_count);

        mCountDownFragment = new CountDownFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frag_countDown, mCountDownFragment)
                .commit();
    }

    @Override
    public void onStartButtonClicked() {
        mCountDownTask = new CountDownTask();
        mCountDownTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onInitButtonClicked() {
        mCountDownTask.cancel(true);
        mCountText.setText("0");
        mCountDownFragment.setCount(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCountDownEvent(CountDownEvent event) {
            mCountText.setText(event.count+"");
            mCountDownFragment.setCount(event.count);

    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public static class CountDownEvent {
        int count;
    }
    static class CountDownTask extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 1; i <= 10; i++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            CountDownEvent event = new CountDownEvent();
            event.count = values[0];
            EventBus.getDefault().post(event);


        }
    }
}
