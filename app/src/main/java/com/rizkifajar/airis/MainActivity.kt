package com.rizkifajar.airis

import androidx.appcompat.app.AppCompatActivity
//import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.rizkifajar.airis.manager.MQTTmanager
import com.rizkifajar.airis.protocols.UIUpdater
import org.eclipse.paho.android.service.MqttAndroidClient
import android.view.View
import com.rizkifajar.airis.manager.MQTTConnectionParams
//import kotlinx.android.synthetic.main.activity_main.*


abstract class MainActivity : AppCompatActivity(), UIUpdater {

    var mqttManager: MQTTmanager? = null

//    val temperatur = findViewById<TextView>(R.id.TempValue)
//    val kelembaban = findViewById<TextView>(R.id.lembabValue)
//    val cahaya = findViewById<TextView>(R.id.cahayaValue)
//    val udara = findViewById<TextView>(R.id.udaraValue)
//    val voc = findViewById<TextView>(R.id.vocValue)
//    val monoksida = findViewById<TextView>(R.id.monoksidaValue)
//    val gas = findViewById<TextView>(R.id.gasValue)
//    val asap = findViewById<TextView>(R.id.asapValue)

    override fun resetUIWithConnection(status: Boolean) {
        ipAddressField.isEnabled = !status
        topicField.isEnabled      = !status
        messageField.isEnabled    = status
        connectBtn.isEnabled      = !status
        sendBtn.isEnabled         = status

        // Update the status label.
        if (status){
            updateStatusViewWith("Connected")
        }else{
            updateStatusViewWith("Disconnected")
        }
    }

    override fun update(message: String) {

        var text = messageHistoryView.text.toString()
        //var newText = text.toString() + "\n" + message +  "\n"
        messageHistoryView.setText(newText)
        messageHistoryView.setSelection(messageHistoryView.text.length)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun connect(view: View){

        if (!(ipAddressField.text.isNullOrEmpty() && topicField.text.isNullOrEmpty())) {
            var host = "tcp://" + ipAddressField.text.toString() + ":1883"
            var topic = topicField.text.toString()
            var connectionParams = MQTTConnectionParams("MQTTSample",host,topic,"","")
            mqttManager = MQTTmanager(connectionParams,applicationContext,this)
            mqttManager?.connect()
        }else{
            updateStatusViewWith("Please enter all valid fields")
        }

    }

    fun sendMessage(view: View){

        mqttManager?.publish(messageField.text.toString())

        messageField.setText("")
    }


}