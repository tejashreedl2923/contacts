
# Contacts Viewer - Android Content Provider

## Overview

Contacts Viewer is an Android application developed using Kotlin and XML in Android Studio. The application demonstrates data sharing using Android's built-in Contacts Content Provider. It retrieves contact display names from the device's contacts database and displays them in a clean, user-friendly interface.

The application includes a search feature, a refresh option, and a custom contact list with initials and rounded cards. For privacy, the interface displays contact names only and does not retrieve or display phone numbers.

## Aim

To implement data sharing in Android using the Contacts Content Provider and understand how to access contact information securely through ContentResolver and ContactsContract.

## Objectives

- To understand the concept of Android Content Providers.
- To access contact data using ContactsContract.
- To retrieve contact display names using ContentResolver.
- To implement runtime permission handling for reading contacts.
- To display contacts using a custom ListView adapter.
- To implement searching and refreshing of contacts.
- To design a user-friendly interface using XML layouts and drawable resources.

## Technologies Used

- **Programming Language:** Kotlin
- **User Interface:** XML
- **Development Environment:** Android Studio
- **Platform:** Android
- **Data Access:** ContentResolver
- **Content Provider:** ContactsContract
- **Permission:** READ_CONTACTS
- **UI Components:** TextView, EditText, Button, ListView, LinearLayout
- **Custom Components:** BaseAdapter and XML drawable resources

## Features

### 1. View Contacts

Retrieves contact display names from the Android Contacts Content Provider and displays them in a list.

### 2. Privacy-Friendly Display

Displays only contact names and initials. Phone numbers are not retrieved or shown by the application.

### 3. Search Contacts

Allows users to search for contacts by name. The list updates as the user types.

### 4. Refresh Contacts

Provides a button to reload the contacts from the device's contacts database.

### 5. Runtime Permission Handling

Requests permission to read contacts before accessing contact information. If permission is denied, the application displays an appropriate message.

### 6. Custom Contact Cards

Displays each contact in a rounded white card with a lavender initials circle, dark text, and subtle borders.

### 7. Contact Count

Displays the number of contact entries currently shown, updating when the search query changes.

## Application Workflow

1. Launch the Contacts Viewer application.
2. The application checks whether contact-reading permission has been granted.
3. If permission has not been granted, Android displays a permission request.
4. After permission is granted, the application queries the Contacts Content Provider.
5. Contact display names are retrieved using ContentResolver.
6. The names and their initials are displayed in a custom list.
7. Users can search for contacts by name.
8. Users can tap Refresh Contacts to reload the list.

## Concepts Used

### 1. Content Provider

A Content Provider is an Android component that manages and exposes structured data to other applications through a standard interface.

The Contacts Content Provider allows applications to access contact information when the required permissions are granted.

### 2. ContactsContract

ContactsContract provides constants and APIs for accessing contact-related data.

The application uses:

`ContactsContract.Contacts.CONTENT_URI`

This URI identifies the contacts data that the application queries.

### 3. ContentResolver

ContentResolver acts as the interface between the application and a Content Provider. It executes queries and returns the requested data through a Cursor.

Example:

```kotlin
contentResolver.query(
    ContactsContract.Contacts.CONTENT_URI,
    arrayOf(ContactsContract.Contacts.DISPLAY_NAME),
    null,
    null,
    "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
)
```

### 4. Runtime Permission

The application declares the READ_CONTACTS permission in AndroidManifest.xml and requests it at runtime before accessing contacts.

```xml
<uses-permission android:name="android.permission.READ_CONTACTS" />
```

### 5. Cursor

A Cursor provides access to the rows returned by a database query. The application iterates through the Cursor to retrieve contact display names.

### 6. ListView and BaseAdapter

ListView displays the collection of contacts, while BaseAdapter supplies the custom XML layout and contact data for each list item.

## Project Structure

```text
ContactsProviderDemo/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/
│           │       └── example/
│           │           └── contacts/
│           │               └── MainActivity.kt
│           ├── res/
│           │   ├── drawable/
│           │   │   ├── contact_avatar_background.xml
│           │   │   └── contact_card_background.xml
│           │   ├── layout/
│           │   │   ├── activity_main.xml
│           │   │   └── item_contact.xml
│           │   └── values/
│           └── AndroidManifest.xml
├── README.md
└── build.gradle.kts
```

## Installation and Execution

1. Install Android Studio.
2. Clone or download the repository.
3. Open the project in Android Studio.
4. Allow Gradle to sync and resolve dependencies.
5. Connect an Android phone or start an emulator.
6. Ensure the device has contacts available for testing.
7. Run the application.
8. Grant contact-reading permission when prompted.
9. View, search, and refresh the contact list.

## Testing

| Test Case | Expected Result | Status |
|---|---|---|
| Launch the application | Contacts Viewer screen is displayed | Pass |
| Grant contact permission | Contact names are loaded | Pass |
| Deny contact permission | Permission message is displayed | Pass |
| View contact list | Contact names and initials appear in cards | Pass |
| View contact details | Phone numbers are not displayed | Pass |
| Search by name | Matching contacts are displayed | Pass |
| Search with no match | Empty contact list is displayed | Pass |
| Refresh contacts | Contact list is queried again | Pass |

## Screenshots

Add screenshots of the actual application in the following locations.

### Home Screen

<img width="1366" height="720" alt="Screenshot 2026-10-23 193332" src="https://github.com/user-attachments/assets/447630d5-5f15-4a32-b9a0-028c17307991" />


### Permission Request

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/94f5d27b-e09e-476a-9525-bd7475f4ebfb" />


### Contact List

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/c92f8d13-c097-4920-aeba-41096066cadf" />


### Search Contacts

<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/0cd727ef-1101-4bcb-b208-552bc5548322" />
<img width="718" height="1598" alt="image" src="https://github.com/user-attachments/assets/8a56e9a1-74d6-40ff-b266-fb5f62eca045" />


## Learning Outcomes

- Understood the purpose and working of Android Content Providers.
- Learned to access contact information using ContactsContract.
- Implemented ContentResolver queries.
- Understood runtime permission handling.
- Learned to retrieve data using Cursor.
- Implemented a custom ListView using BaseAdapter.
- Designed a contact list using XML and drawable resources.
- Implemented searching and refreshing functionality.

## Future Enhancements

- Add contact sorting options.
- Display contact profile images with appropriate permissions.
- Add a contact details screen with explicit user interaction.
- Improve accessibility and support different screen sizes.
- Add an option to select specific contacts.
- Improve empty-state messages and loading indicators.

## Conclusion

The Contacts Viewer application demonstrates data sharing in Android using the Contacts Content Provider. It uses ContentResolver and ContactsContract to retrieve contact display names after obtaining the necessary runtime permission.

The application also demonstrates Cursor-based data retrieval, custom ListView adapters, search functionality, and XML-based interface design. By displaying names without retrieving phone numbers, the application follows a privacy-conscious approach while meeting the objectives of the experiment.
