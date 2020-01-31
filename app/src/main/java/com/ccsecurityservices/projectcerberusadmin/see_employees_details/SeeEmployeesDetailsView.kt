package com.ccsecurityservices.projectcerberusadmin.see_employees_details

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ccsecurityservices.projectcerberusadmin.data_items.Employee
import com.ccsecurityservices.projectcerberusadmin.R
import com.ccsecurityservices.projectcerberusadmin.edit_employee.EditEmployeeView
import kotlinx.android.synthetic.main.see_employees_details.*
import java.io.Serializable


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
            testingDialog()
        }

        see_employee_details_editEmployeeBTN.setOnClickListener {
            presenter.prepareForEdit()
        }
    }

    private fun testingDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erase Employee")
        builder.setMessage("Are you sure you want to erase this employee from the system?")
        builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
            presenter.deleteEmployee()
        }
        builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
        builder.show()
    }

    override fun populateFields(EMP: Employee) {
        see_employee_details_name_text_view.text =
            getString(R.string.see_generics_details_name_text_placeholder).plus(" ${EMP.firstName} ${EMP.lastName}")
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

    override fun showToastMessages(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun showLoading(state: Boolean) {
        if (state) {
            see_employee_details_loading_widget.visibility = View.VISIBLE
        } else {
            see_employee_details_loading_widget.visibility = View.GONE
        }
    }

    override fun navToEditEmployee(EMP: Employee) {
        val navIntent = Intent(this, EditEmployeeView::class.java)
        navIntent.putExtra("employee_edit", EMP as Serializable)
        startActivity(navIntent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            presenter.retrieveProfilePic(data)
        }
    }
}
