package ru.example.customintentproect.intent

import android.app.Service
import android.content.ContextWrapper
import android.content.Intent
import android.os.*
import androidx.annotation.WorkerThread

abstract class CustomIntentService : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null
    private var name: String? = null

    inner class ServiceHandler(looper: Looper?) : Handler(looper!!) {

        fun serviceHandler(looper: Looper) {
            Handler(looper)
        }

        override fun handleMessage(message: Message) {
            onHandleIntent(message.obj as Intent)
            stopSelf(message.arg1)
        }
    }

    open fun IntentService(name: String) {
        ContextWrapper(null)
        this.name = name
    }

    override fun onCreate() {
        super.onCreate()
        val thread = HandlerThread("CustomInternalService" + name + "]")
        thread.start()

        serviceLooper = thread.looper
        serviceHandler = ServiceHandler(serviceLooper)

    }

    override fun onStart(intent: Intent?, startId: Int) {
        var message = serviceHandler?.obtainMessage()
        message?.apply {
            arg1 = startId
            obj = intent
        }
        if (message != null) {
            serviceHandler?.sendMessage(message)
        }
    }

    override fun onDestroy() {
        serviceLooper?.quit()
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @WorkerThread
    protected abstract fun onHandleIntent(intent: Intent)

}