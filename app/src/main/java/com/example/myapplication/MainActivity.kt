package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var customText:TextView


    val db = Firebase.firestore


    var a = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        val docRef = db.collection("counter").document("userA")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("MYTAG", "DocumentSnapshot data: ${document.data?.keys}")

                    a =  (document.data?.get("Value") as Long).toInt()
                    customText.text = "Counter is: "+a;
                } else {
                    Log.d("MYTAG", "No such document")
                    a = 0
                }
            }
            .addOnFailureListener { exception ->
                Log.d("MYTAG", "get failed with ", exception)
                a = 0
            }


        customText = findViewById(R.id.textView9)
        var clickButton:Button = findViewById(R.id.button)
        customText.text = "Counter is: "+a;


        clickButton.setOnClickListener(this)

    }

    override fun onClick(p0: View?) {
        a++;
        val data = hashMapOf( "Value" to a);
        db.collection("counter").document("userA").set(data).addOnSuccessListener { Log.d("MYAPP", "DocumentSnapshot successfully written!") }
        customText.text = "Counter is: "+a;
    }
}