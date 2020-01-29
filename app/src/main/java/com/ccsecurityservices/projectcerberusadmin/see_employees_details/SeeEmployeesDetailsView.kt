package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.Data_Items.Employee
import com.ccsecurityservices.projectcerberusadmin.R
import kotlinx.android.synthetic.main.see_employees_details.*


class SeeEmployeesDetailsView : AppCompatActivity(),
    SeeEmployeesDetailsContract.SeeEmployeesDetailsView {

    private lateinit var presenter: SeeEmployeesDetailsPresenter

    private lateinit var imageView: ImageView


    companion object {
        const val RC_PHOTO_PICKER = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_employees_details)

        imageView = findViewById(R.id.see_employee_details_profile_pic)

        presenter = SeeEmployeesDetailsPresenter(this)
        presenter.retrieveEmployeeObject(intent.extras!!.get("employee_details") as Employee)

        see_employee_details_profile_pic.setOnClickListener {
            val intent = presenter.createIntentForProfilePic()
            startActivityForResult(
                Intent.createChooser(intent, "Complete action using"),
                RC_PHOTO_PICKER
            )
        }

        see_employee_details_eraseEmployeeBTN.setOnClickListener {
            presenter.deleteEmployee()
        }
    }

    override fun populateFields(EMP: Employee) {
        see_employee_details_name_text_view.text =
            getString(R.string.see_employee_details_name_text_placeholder).plus(" ${EMP.firstName} ${EMP.lastName}")
        see_employee_details_phone_text_view.text =
            getString(R.string.see_employee_details_phone_text_placeholder).plus(" ${EMP.phone}")
        see_employee_details_email_text_view.text =
            getString(R.string.see_employee_details_email_text_placeholder).plus(" ${EMP.email}")

        if (EMP.adminRights) {
            see_employee_details_admin_text_view.text =
                getString(R.string.see_employee_details_admin_text_placeholder).plus(" Administrator")
        } else {
            see_employee_details_admin_text_view.text =
                getString(R.string.see_employee_details_admin_text_placeholder).plus(" Employee")
        }

        if (EMP.hasApp) {
            see_employee_details_hasApp_text_view.text =
                getString(R.string.see_employee_details_hasApp_text_placeholder).plus(" YES")
        } else {
            see_employee_details_hasApp_text_view.text =
                getString(R.string.see_employee_details_hasApp_text_placeholder).plus(" NO")
        }
    }

    override fun deleteResult(result: Boolean) {
        if (result) {
            finish()
        }
    }

    override fun updateProfilePicFromPicker(uri: Uri?) {
        see_employee_details_profile_pic.setImageURI(uri)
    }

    override fun downloadPic(imageUrl: String) {
        Glide.with(this).load(imageUrl).into(imageView);
    }

    override fun showProfilePicMsg(msgCode: Boolean) {
        if (msgCode) {
            Toast.makeText(this, "The Profile Picture was Successfully Uploaded", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(
                this,
                "An Error occurred trying to upload. Please try later.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            presenter.retrieveProfilePic(data)
        }
    }
}
