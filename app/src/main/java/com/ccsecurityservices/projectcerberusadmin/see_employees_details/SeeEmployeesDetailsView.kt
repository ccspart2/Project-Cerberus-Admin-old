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
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var adapter: SeeEmployeesDetailsAdapter

    companion object {
        const val RC_PHOTO_PICKER = 2
    }

    private fun eraseWarningDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erase Employee")
        builder.setMessage("Are you sure you want to erase this employee from the system?")
        builder.setPositiveButton("Yes") { _: DialogInterface?, _: Int ->
            presenter.prepareForDelete()
        }
        builder.setNegativeButton("No") { _: DialogInterface?, _: Int -> }
        builder.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.see_employees_details)

        imageView = findViewById(R.id.see_employee_details_profile_pic)

        presenter = SeeEmployeesDetailsPresenter(this)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        see_employee_details_recycler_view.layoutManager = layoutManager

        adapter = SeeEmployeesDetailsAdapter(presenter)
        see_employee_details_recycler_view.adapter = adapter

        presenter.retrieveEmployeeObject(intent)

        see_employee_details_eraseEmployeeBTN.setOnClickListener {
            eraseWarningDialog()
        }

        see_employee_details_editEmployeeBTN.setOnClickListener {
            presenter.prepareForEdit()
        }

        see_employee_details_profile_pic.setOnClickListener {
            startActivityForResult(
                Intent.createChooser(
                    presenter.createIntentForProfilePic(),
                    "Complete action using"
                ),
                RC_PHOTO_PICKER
            )
        }
    }

    override fun populateFields(EMP: Employee) {
        see_employee_details_name_label.text =
            getString(R.string.see_generics_details_name_text_placeholder).plus(" ${EMP.firstName} ${EMP.lastName}")
        see_employee_details_phone_label.text =
            getString(R.string.see_employee_details_phone_text_placeholder).plus(" ${EMP.phone}")
        see_employee_details_email_label.text =
            getString(R.string.see_employee_details_email_text_placeholder).plus(" ${EMP.email}")

        if (EMP.adminRights) {
            see_employee_details_admin_label.text =
                getString(R.string.see_employee_details_admin_text_placeholder).plus(" Administrator")
            see_employee_details_eraseEmployeeBTN.visibility = View.GONE
            see_employee_details_editEmployeeBTN.visibility = View.GONE
            see_employee_details_recycler_view.visibility = View.GONE
            see_employee_details_hasApp_label.visibility = View.GONE
            see_employee_details_profile_pic.setOnClickListener(null)
        } else {
            see_employee_details_admin_label.text =
                getString(R.string.see_employee_details_admin_text_placeholder).plus(" Employee")
        }

        if (EMP.hasApp) {
            see_employee_details_hasApp_label.text =
                getString(R.string.see_employee_details_hasApp_text_placeholder).plus(" YES")
        } else {
            see_employee_details_hasApp_label.text =
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
        showLoading(true)
        Glide.with(this).load(imageUrl).into(imageView)
        showLoading(false)
    }

    override fun showToastMessages(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun showLoading(state: Boolean) {
        if (state) {
            see_employee_details_loading_widget.visibility = View.VISIBLE
        } else {
            see_employee_details_loading_widget.visibility = View.INVISIBLE
        }
    }

    override fun navToEditEmployee(EMP: Employee) {
        val navIntent = Intent(this, EditEmployeeView::class.java)
        navIntent.putExtra("employee_edit", EMP as Serializable)
        startActivity(navIntent)
        finish()
    }

    override fun displayWarningDialog(title: String, desc: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(desc)
        builder.setNeutralButton("OK") { _, _ -> }
        builder.show()
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            presenter.retrieveProfilePic(data)
        }
    }
}
