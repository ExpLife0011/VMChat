package net.melove.demo.chat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;

import net.melove.demo.chat.R;
import net.melove.demo.chat.application.MLEasemobHelper;

/**
 * Created by lz on 2015/12/7.
 */
public class MLSplashActivity extends MLBaseActivity {

    // 开屏页持续时间
    private int mTime = 3000;
    // 动画持续时间
    private int mDurationTime = 1500;

    // 开屏页显示的图片控件
    private View mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initView();

    }

    /**
     * 初始化当前界面的控件
     */
    private void initView() {
        mImageView = findViewById(R.id.ml_img_splash);
        AlphaAnimation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(mDurationTime);
        mImageView.startAnimation(animation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                if (MLEasemobHelper.getInstance().isLogined()) {
                    // 获取当前系统时间毫秒数
                    long start = System.currentTimeMillis();
                    // 加载群组到内存
                    EMGroupManager.getInstance().loadAllGroups();
                    // 加载本地会话到内存
                    EMChatManager.getInstance().loadAllConversations();
                    // 获取加载回话使用的时间差 毫秒表示
                    long costTime = System.currentTimeMillis() - start;
                    if (mTime - costTime > 0) {
                        try {
                            Thread.sleep(mTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 进入主页面
                    startActivity(new Intent(mActivity, MLMainActivity.class));
                } else {
                    try {
                        // 初始化数据
//                        initData();
                        // 睡眠3000毫秒
                        Thread.sleep(mTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 跳转到登录界面
                    startActivity(new Intent(mActivity, MLSigninActivity.class));
                }
                finish();
            }
        }).start();
    }
}
