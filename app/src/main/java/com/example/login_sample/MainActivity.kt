package com.example.login_sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        login_button.setOnClickListener {
            login_now()
        }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    fun login_now() {
        if (email.text.toString().isEmpty()) {
            email.error = "Please enter your e-mail address here"
            email.requestFocus()
            return
        } else if (password.text.toString().isEmpty()) {
            password.error = "Please enter your password here"
            password.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(currenntUser: FirebaseUser?){

        if (currenntUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }
    }
}