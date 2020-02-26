package com.ccsecurityservices.projectcerberusadmin.home_page

import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomePagePresenter(private val view: HomePageContract.HomePageView) :
    HomePageContract.HomePagePresenter {

    private lateinit var auth: FirebaseAuth
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()

    private lateinit var currentEmployee: Employee


    override fun retrieveUserInfo() {
        auth = FirebaseAuth.getInstance()

        val adminsReference = mFireBaseDatabase.reference.child("employees/admins")

        val adminsListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.w("SignInPresenter", p0.message)
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                currentEmployee = dataSnapshot.children.find {
                    it.getValue(Employee::class.java)?.email == auth.currentUser!!.email
                }?.getValue(Employee::class.java)!!
                view.populateUser(
                    "${currentEmployee.firstName} ${currentEmployee.lastName}",
                    currentEmployee.photoId
                )
            }
        }
        adminsReference.addListenerForSingleValueEvent(adminsListener)
    }

    override fun logOut() {
        view.navToSignIn()
    }
}