package com.example.shopping

import android.util.Log
import de.robv.android.xposed.IXposedHookZygoteInit


class EdxposedTester : IXposedHookZygoteInit {


    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        Log.i("edxposed", "initZygote")
    }
}