package com.example.applligent.nagoriengineering.view.chat

import android.app.Activity.RESULT_OK
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import com.example.applligent.nagoriengineering.R
import com.example.applligent.nagoriengineering.model.Upload
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.squareup.picasso.Picasso


class Frag2 : Fragment() {
    private val PICK_IMAGE_REQUEST = 1

    private var mButtonChooseImage: ImageView? = null
    private var mButtonUpload: ImageView? = null
    private var mTextViewShowUploads: TextView? = null
    private var mEditTextFileName: EditText? = null
    private var mImageView: ImageView? = null
    private var mProgressBar: ProgressBar? = null
    private var mImageUri: Uri? = null

    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag2_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads")

        mButtonChooseImage = view.findViewById(R.id.button_choose_image) as ImageView
        mButtonUpload = view.findViewById(R.id.button_upload) as ImageView
        mTextViewShowUploads = view.findViewById(R.id.text_view_show_uploads) as TextView
        mEditTextFileName = view.findViewById(R.id.edit_text_file_name) as EditText
        mImageView = view.findViewById(R.id.image_view) as ImageView
        mProgressBar = view.findViewById(R.id.progress_bar) as ProgressBar




        mButtonChooseImage!!.setOnClickListener {
            openFileChooser()
        }


        mButtonUpload!!.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress) {
            } else {
                uploadFile()
            }
        }

        mTextViewShowUploads!!.setOnClickListener {
            Toast.makeText(context, "najish here", Toast.LENGTH_SHORT).show()
            val myintent = Intent(activity, ShowImages::class.java)
            startActivity(myintent)
        }


    }


    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.data != null) {
            mImageUri = data.data

            Picasso.with(context).load(mImageUri).into(mImageView)
        }
    }


    private fun uploadFile() {
        if (mImageUri != null) {
            val fileReference = mStorageRef!!.child(System.currentTimeMillis().toString()
                    + "." + getFileExtension(mImageUri!!))

            mUploadTask = fileReference.putFile(mImageUri!!)
                    .addOnSuccessListener { taskSnapshot ->
                        val handler = Handler()
                        handler.postDelayed(Runnable { mProgressBar!!.progress = 0 }, 500)

                        Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG).show()

                        taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                            val upload = Upload(mEditTextFileName!!.text.toString().trim(),
                                    it.toString())
                            val uploadId = mDatabaseRef!!.push().key
                            mDatabaseRef!!.child(uploadId!!).setValue(upload)

                        }

                    }
                    .addOnFailureListener { e -> Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show() }
                    .addOnProgressListener { taskSnapshot ->
                        val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        mProgressBar!!.progress = progress.toInt()
                    }
        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show()
        }
    }

    fun getFileExtension(uri: Uri): String {
        var cR: ContentResolver = context!!.contentResolver
        var mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

}
