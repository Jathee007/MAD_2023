package com.company.notes

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.company.notes.databinding.ActivityDeleteUserBinding
import com.company.notes.model.User
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class DeleteUser : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteUserBinding
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private lateinit var mUserDetails: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_user)
        binding = ActivityDeleteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dltbtn.setOnClickListener {
            val email: String = binding.dltemail.text.toString().trim()
            val password: String = binding.dltpassword.text.toString().trim()
            deleteuser(email, password);

        }



    }





    private fun deleteuser(email: String, password: String) {
        val user = FirebaseAuth.getInstance().currentUser

        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        val credential = EmailAuthProvider.getCredential(email, password)
        // Prompt the user to re-provide their sign-in credentials
        user?.reauthenticate(credential)?.addOnCompleteListener {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        startActivity(Intent(this@DeleteUser, LoginActivity::class.java))
                        Toast.makeText(
                            this@DeleteUser,
                            "Deleted User Successfully,",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }



}