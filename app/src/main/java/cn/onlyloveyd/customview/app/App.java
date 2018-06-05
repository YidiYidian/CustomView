package cn.onlyloveyd.customview.app;

import android.app.Application;

import me.ele.uetool.UETool;

/**
 * Class Description
 *
 * @author yidong (yidong@gz.csg.cn)
 * @date 2018/5/25 16:59
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UETool.showUETMenu();
    }
}
