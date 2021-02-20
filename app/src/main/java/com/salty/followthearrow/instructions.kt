package com.salty.followthearrow

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.widget.Button

class instructions : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructions2)

        val goBack = findViewById<Button>(R.id.back);

        goBack.setOnClickListener {
            finish();
        }

        // Enables Always-on
        setAmbientEnabled()
    }
}