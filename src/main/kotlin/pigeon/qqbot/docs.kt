package pigeon.qqbot

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import java.io.File
import java.io.InputStreamReader
import java.util.*

class SheetUtil {
    private val appName = "pigeonbot"
    private val jsonFactory: JacksonFactory = JacksonFactory.getDefaultInstance()
    private val tokensPath = "tokens"
    private val sheetID = "1lzvvlmd7wBaFlhEi8qhqn3sbeqcUQSki4txlPB4NCxk"
    private val scopes = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY)
    private val credentialsPath = "src/main/resources/credentials.json"

    private fun getCredentials(HTTP_TRANSPORT: NetHttpTransport): Credential? {
        // Load client secrets.
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(File(credentialsPath).inputStream()))

        // Build flow and trigger user authorization request.
        val flow = GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(FileDataStoreFactory(File(tokensPath)))
                .setAccessType("offline")
                .build()
        val receiver = LocalServerReceiver.Builder().setPort(8888).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    private val httpTransport = GoogleNetHttpTransport.newTrustedTransport()
    private val service = Sheets.Builder(httpTransport, jsonFactory, getCredentials(httpTransport))
            .setApplicationName(appName)
            .build()
    private val sheet = service.spreadsheets().get(sheetID).execute()

    fun getAbsentList() {
        val range="时间安排!A4:O100"
        val values= sheet[range]
        print(values)
    }
}

fun main() {
    val sheetUtil = SheetUtil()
    sheetUtil.getAbsentList()
}