package com.ccsecurityservices.projectcerberusadmin.sign_in

import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SignInPresenter(private val view: SignInView) : SignInContract.SignInPresenter {

    private lateinit var auth: FirebaseAuth
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private var validAdmin = false

    override fun checkIfSignedIn() {
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            view.navToHomePage()
        } else {
            view.startSignInFlow()
        }
    }

    override fun checkIfAdmin(currentUser: FirebaseUser) {
        val adminsReference = mFireBaseDatabase.reference.child("employees/admins")

        val adminsListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("SignInPresenter", p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.forEach {
                    val admin = it.getValue(Employee::class.java)!!
                    if (admin.email == currentUser.email) {
                        validAdmin = true
                        if (!admin.hasApp || currentUser.uid == admin.authId) {
                            updateEmployeeRecord(admin, currentUser)
                        }
                    }
                }
                if (validAdmin) {
                    view.navToHomePage()
                } else {
                    view.invalidUserDialog()
                }
            }
        }
        adminsReference.addListenerForSingleValueEvent(adminsListener)
        view.displayLoading(true)
    }

    private fun updateEmployeeRecord(admin: Employee, currentUser: FirebaseUser) {
        admin.hasApp = true
        admin.authId = currentUser.uid
        val updateReference = mFireBaseDatabase.reference.child("employees/admins/${admin.id}")
        updateReference.setValue(admin)
    }
}
