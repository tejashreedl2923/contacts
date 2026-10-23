package com.example.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import android.provider.ContactsContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var listContacts: ListView
    private lateinit var tvContactCount: TextView
    private lateinit var etSearch: EditText

    private val contactNames = mutableListOf<String>()
    private val filteredNames = mutableListOf<String>()

    private lateinit var contactAdapter: ContactAdapter

    private val permissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                loadContacts()
            } else {
                Toast.makeText(
                    this,
                    "Permission is required to read contacts",
                    Toast.LENGTH_LONG
                ).show()
                tvContactCount.text = "Contacts permission denied"
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listContacts = findViewById(R.id.listContacts)
        tvContactCount = findViewById(R.id.tvContactCount)
        etSearch = findViewById(R.id.etSearch)

        contactAdapter = ContactAdapter()
        listContacts.adapter = contactAdapter

        findViewById<Button>(R.id.btnRefresh).setOnClickListener {
            checkPermissionAndLoad()
        }

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                filterContacts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        checkPermissionAndLoad()
    }

    private fun checkPermissionAndLoad() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            loadContacts()
        } else {
            permissionLauncher.launch(
                Manifest.permission.READ_CONTACTS
            )
        }
    }

    private fun loadContacts() {
        contactNames.clear()

        val projection = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME
        )

        try {
            contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                "${ContactsContract.Contacts.DISPLAY_NAME} IS NOT NULL",
                null,
                "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
            )?.use { cursor ->

                val nameColumn = cursor.getColumnIndexOrThrow(
                    ContactsContract.Contacts.DISPLAY_NAME
                )

                while (cursor.moveToNext()) {
                    val name = cursor.getString(nameColumn)

                    if (!name.isNullOrBlank()) {
                        contactNames.add(name)
                    }
                }
            }

            filterContacts(etSearch.text.toString())

            if (contactNames.isEmpty()) {
                Toast.makeText(
                    this,
                    "No contacts found",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } catch (e: SecurityException) {
            Toast.makeText(
                this,
                "Permission to read contacts was denied",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Unable to load contacts",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun filterContacts(query: String) {
        filteredNames.clear()

        filteredNames.addAll(
            contactNames.filter { name ->
                name.contains(query.trim(), ignoreCase = true)
            }
        )

        if (::contactAdapter.isInitialized) {
            contactAdapter.notifyDataSetChanged()
        }

        tvContactCount.text =
            "Your contacts · ${filteredNames.size}"
    }

    private inner class ContactAdapter : BaseAdapter() {

        override fun getCount(): Int = filteredNames.size

        override fun getItem(position: Int): String =
            filteredNames[position]

        override fun getItemId(position: Int): Long =
            position.toLong()

        override fun getView(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {

            val view = convertView ?: LayoutInflater.from(this@MainActivity)
                .inflate(R.layout.item_contact, parent, false)

            val name = getItem(position)

            val nameView = view.findViewById<TextView>(
                R.id.tvContactName
            )

            val initialView = view.findViewById<TextView>(
                R.id.tvContactInitial
            )

            nameView.text = name

            initialView.text = name.trim()
                .split("\\s+".toRegex())
                .filter { it.isNotEmpty() }
                .take(2)
                .mapNotNull { it.firstOrNull() }
                .joinToString("")
                .uppercase()

            return view
        }
    }
}
