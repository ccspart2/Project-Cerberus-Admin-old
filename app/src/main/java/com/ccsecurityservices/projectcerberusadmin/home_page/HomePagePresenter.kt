package com.ccsecurityservices.projectcerberusadmin.home_page

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class HomePagePresenter(private val view: HomePageView) :
    HomePageContract.HomePagePresenter {

    private lateinit var auth: FirebaseAuth
    private var mFireBaseDatabase = FirebaseDatabase.getInstance()
    private var mFireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()

    private lateinit var currentEmployee: Employee


    override fun retrieveEmployeeFromAuth() {
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

    override fun createIntentForProfilePic(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        return intent
    }

    override fun updateProfilePicInStorage(data: Intent?) {
        view.displayLoadingWidget(true)
        val selectedImageUri = data!!.data!!

        //Get a reference to store file at employees_profile_pictures/<FILENAME>
        val photoRef = mFireBaseStorage.reference
            .child("employees_profile_pictures")
            .child("${this.currentEmployee.firstName}.${this.currentEmployee.lastName}")

        val uploadTask = photoRef.putFile(selectedImageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            photoRef.downloadUrl
        }.addOnCompleteListener(view) { task ->
            if (task.isSuccessful) {
                updateUrlInEmployeeRecord(task.result!!.toString(), selectedImageUri)
            } else if (!task.isSuccessful) {
                view.displayLoadingWidget(false)
                view.displayToastMessage("Image was not updated successfully. Please try again later. ")
            }
        }
    }

    private fun updateUrlInEmployeeRecord(
        photoURL: String,
        selectedImageUri: Uri
    ) {
        val currentEmployeeReference = mFireBaseDatabase.reference
            .child("employees/admins")
            .child(currentEmployee.id)
            .child("photoId")

        currentEmployeeReference.setValue(photoURL).addOnCompleteListener(view) { task ->
            if (task.isSuccessful) {
                view.displayLoadingWidget(false)
                view.updateImageView(selectedImageUri)
                view.displayToastMessage("Image Successfully Updated")
            }
        }
    }
}