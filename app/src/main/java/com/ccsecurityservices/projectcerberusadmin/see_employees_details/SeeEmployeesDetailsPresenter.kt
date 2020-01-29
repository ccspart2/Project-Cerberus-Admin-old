package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.content.Intent
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class SeeEmployeesDetailsPresenter(private val view: SeeEmployeesDetailsView) :
    SeeEmployeesDetailsContract.SeeEmployeesDetailsPresenter {

    private lateinit var currentEmployee: Employee

    private var mFireBaseStorage: FirebaseStorage = FirebaseStorage.getInstance()
    private lateinit var employeeProfilePhotoStorageReference: StorageReference

    private var mFireBaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var currentEmployeeReference: DatabaseReference

    private fun deleteProfilePic() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateUrlInEmployeeRecord(photoURL: String) {
        currentEmployeeReference = mFireBaseDatabase.reference
            .child("employees")
            .child(currentEmployee.id)
            .child("photoId")

        currentEmployeeReference.setValue(photoURL)
    }

    override fun retrieveEmployeeObject(EMP: Employee) {
        this.currentEmployee = EMP
        view.populateFields(currentEmployee)

        if (currentEmployee.photoId != "") {
            view.downloadPic(currentEmployee.photoId)
        }
    }

    override fun deleteEmployee() {

        //deleteProfilePic()

        currentEmployeeReference =
            mFireBaseDatabase.reference.child("employees").child(currentEmployee.id)
        currentEmployeeReference.removeValue().addOnSuccessListener(view) {
            view.deleteResult(true)
        }
    }

    override fun prepareForEdit(): Employee {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createIntentForProfilePic(): Intent {

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        employeeProfilePhotoStorageReference =
            mFireBaseStorage.reference.child("employees_profile_pictures")

        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        return intent
    }

    override fun retrieveProfilePic(data: Intent?) {

        val selectedImageUri = data!!.data

        //Get a reference to store file at employees_profile_pictures/<FILENAME>
        val photoRef =
            employeeProfilePhotoStorageReference.child(selectedImageUri!!.lastPathSegment!!)

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
                updateUrlInEmployeeRecord(task.result!!.toString())
                view.showProfilePicMsg(true)
            } else {
                view.showProfilePicMsg(false)
            }
        }
        view.updateProfilePicFromPicker(selectedImageUri)
    }
}