package com.example.projekakhir.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.projekakhir.utils.TokenPreferences.PreferencesKeys.ROLE_USER
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tokenDataStore")

class TokenPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private object PreferencesKeys {
        val TOKEN = stringPreferencesKey("token")
        val NPM_USER = stringPreferencesKey("npm_user")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val LAST_NAME = stringPreferencesKey("last_name")
        val EMAIL_NAME = stringPreferencesKey("email_name")
        val ROLE_USER = stringPreferencesKey("role_user")
    }

    //save,get dan delet role user
//    suspend fun saveRole(saveRole:String){
//        dataStore.edit { preference ->
//            preference[PreferencesKeys.ROLE_USER] = saveRole
//        }
//    }
//    suspend fun getRole():String?{
//        val preferences = dataStore.data.first()
//        return preferences[PreferencesKeys.ROLE_USER]
//    }
//    suspend fun deleteRole(){
//        dataStore.edit { preference ->
//            preference.remove(PreferencesKeys.ROLE_USER)
//        }
//    }

    suspend fun getRole():String?{
        val preferences = dataStore.data.firstOrNull()
        return preferences?.get(ROLE_USER)
    }
    suspend fun saveRole(role:String){
        dataStore.edit { preferences ->
            preferences[ROLE_USER] = role
        }
    }
    suspend fun deleteRole(){
        dataStore.edit { preference ->
            preference.remove(PreferencesKeys.ROLE_USER)
        }
    }


    //email
    suspend fun getEmail():String?{
        val preferences = dataStore.data.firstOrNull()
        return preferences?.get(PreferencesKeys.EMAIL_NAME)
    }
    suspend fun saveEmail(email:String){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.EMAIL_NAME] = email
        }
    }
    suspend fun deleteEmail(){
        dataStore.edit { preference ->
            preference.remove(PreferencesKeys.EMAIL_NAME)
        }
    }

    //save,get dan delete LAST NAME
    suspend fun saveLastName(saveLastName:String){
        dataStore.edit { preference ->
            preference[PreferencesKeys.LAST_NAME] = saveLastName
        }
    }

    suspend fun getLastName():String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.LAST_NAME]
    }


    suspend fun deleteLastName(){
        dataStore.edit { preference ->
            preference.remove(PreferencesKeys.LAST_NAME)
        }
    }

    //save, get dan delete FIRST NAME
    suspend fun saveFirstName(saveFirstName:String){
        dataStore.edit { preference ->
            preference[PreferencesKeys.FIRST_NAME] = saveFirstName
        }
    }

    suspend fun getFirstName():String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.FIRST_NAME]
    }

    suspend fun deleteFirstName(){
        dataStore.edit { preference ->
            preference.remove(PreferencesKeys.FIRST_NAME)
        }
    }

    suspend fun saveNpmUser(userNpm: String) {
        dataStore.edit { prefereces ->
            prefereces[PreferencesKeys.NPM_USER] = userNpm
        }
    }
    suspend fun getNpmUser(): String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.NPM_USER]
    }
    suspend fun deleteNpmUser() {
        dataStore.edit { preference ->
            preference.remove(PreferencesKeys.NPM_USER)
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.TOKEN] = "Bearer $token"
        }
    }

    suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[PreferencesKeys.TOKEN]
    }

    suspend fun deleteToken() {
        dataStore.edit { preferences ->
            preferences.remove(PreferencesKeys.TOKEN)
        }

    }


    companion object {

        fun getTokenSynchronously(dataStore: DataStore<Preferences>): String? {
            return runBlocking {
                TokenPreferences(dataStore).getToken()
            }
        }

        @Volatile
        private var INSTANCE: TokenPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): TokenPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = TokenPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}