export const lang = {
    en: {
        login: "Login",
        register: "Register",
        logout: "Logout",
        delete: "Delete",
        username: "Username",
        password: "Password",
        repeat_password: "Repeat password",
        name: "Name",
        secret: "Secret",
        add: "Add",
        new_password: "New password",
        repeat_new_password: "Repeat new password",
        user: "User",
        new_username: "New username",
        delete_user: "Delete user",
        key: "Key",
        decrypt: "Decrypt",
        change_username: "Change username",
        change_password: "Change password",
        change_key: "Change key",
        new_key: "New key",
        repeat_new_key: "Repeat new key",
        offline_mode: "Offline mode",
        disable_offline_mode: "Disable offline mode",
        add_secret: "Add secret",
        account: "Account",
        close: "Close",
        ok: "Ok",
        confirm: "Confirm",
        error: "Error",
        confirmation: "Confirmation",
        invalid_key: "Invalid key",
        empty_username_password: "Empty username or (encryption) password",
        username_changed: "Username successfully changed",
        password_changed: "Password successfully changed",
        key_changed: "Key successfully changed",
        empty_key: "Empty key",
        decryption_failed: "Could not decrypt secure storage",
        empty_storage: "Empty storage (offline mode unavailable)",
        delete_user_qm: "Delete user?",
        delete_secret_qm: "Delete secret \"$name\"?",
        api_error_cs: "API error: ",
        language: "Language",
        logout: "Logout",
        cancel: "Cancel",
        key_incorrect: "Key incorrect",
        passwords_no_match: "Passwords do not match",
        name_exists: "Name for secret already exists",
        name_secret_empty: "Name or secret empty",
        name_empty: "Name empty",
        name_nonexistent: "Name does not exist",
        unauthenticated: "Incorrect login credentials",
        invalid_secret: "Invalid secret",
        help: "Help",
        help_qa: {
            download: {
                q: "Download",
                a: "You can use an Etopa instance directly in your <a href=\"../\">browser</a> or in the <a href=\"https://play.google.com/store/apps/details?id=de.ltheinrich.etopa\">Android app</a>"
            },
            what_is: {
                q: "What is Etopa?",
                a: "Etopa is a time-based one-time password authenticator.<br>It was built with simplicity, data privacy and security in mind.<br>The Etopa web application or app generates 6-digit one-time login tokens for websites and services that support 2-factor-authentication (2FA)."
            },
            usage: {
                q: "How does it work?",
                a: "First you need to create an account (registration) with an unique username, a password and key that will be used for encrypting your data.<br>Second you enable 2FA for the service or website you want to use Etopa with.<br>Copy the given secret (e.g. JBSWY3DPEHPK3PXP) in combination with a chosen name into the add secret section on the bottom.<br>Once you've added the secret, you'll be shown a different token every 30 seconds in the list.<br>Now you can use that token to login or verify your 2FA on the other website or service."
            },
            questions: {
                q: "Any questions?",
                a: "If you still have any questions or you've found a bug, please contact the developer via email <a href=\"mailto:lennart@ltheinrich.de\">lennart@ltheinrich.de</a> or create an <a href=\"https://ltheinrich.de/etopa/issues\">Issue on GitHub</a>."
            }
        },
        rename: "Rename",
        rename_secret_qm: "Rename secret \"$name\"?",
        new_name_for: "New name for \"$name\"",
        sure_to_delete: "Are you really sure to delete \"$name\"?",
        delete_timeout: "5s timeout to delete",
        edit: "Edit",
        edit_secret: "Edit secret",
        no_action_selected: "No action selected",
        repeat_secret_name: "Repeat secret name \"$name\"",
        repeat_name_incorrect: "Repeated name is incorrect",
        sure_to_rename_to: "Are you really sure to rename \"$name\" to \"$new_name\"?"
    },
    de: {
        login: "Anmelden",
        register: "Registrieren",
        logout: "Abmelden",
        delete: "Löschen",
        username: "Benutzername",
        password: "Passwort",
        repeat_password: "Passwort wiederholen",
        name: "Name",
        secret: "Geheimnis",
        add: "Hinzufügen",
        new_password: "Neues Passwort",
        repeat_new_password: "Neues Passwort wiederholen",
        new_username: "Neuer Benutzername",
        delete_user: "Benutzer löschen",
        key: "Schlüssel",
        decrypt: "Entschlüsseln",
        change_username: "Benutzernamen ändern",
        change_password: "Passwort ändern",
        change_key: "Schlüssel ändern",
        new_key: "Neuer Schlüssel",
        repeat_new_key: "Neuen Schlüssel wiederholen",
        offline_mode: "Offline-Modus",
        disable_offline_mode: "Offline-Modus deaktivieren",
        add_secret: "Geheimnis hinzufügen",
        account: "Account",
        close: "Schließen",
        ok: "Ok",
        confirm: "Bestätigen",
        error: "Fehler",
        confirmation: "Bestätigung",
        invalid_key: "Ungültiger Schlüssel",
        empty_username_password: "Leerer Benutzername oder Passwort",
        username_changed: "Benutzername erfolgreich geändert",
        password_changed: "Passwort erfolgreich geändert",
        key_changed: "Schlüssel erfolgreich geändert",
        empty_key: "Leerer Schlüssel",
        decryption_failed: "Sicherer Speicher konnte nicht entschlüsselt werden",
        empty_storage: "Leerer Speicher (Offline-Modus nicht verfügbar)",
        delete_user_qm: "Benutzer wirklich löschen?",
        delete_secret_qm: "Geheimnis \"$name\" löschen?",
        api_error_cs: "Server/API Fehler: ",
        language: "Sprache",
        logout: "Abmelden",
        cancel: "Abbrechen",
        key_incorrect: "Schlüssel ungültig",
        passwords_no_match: "Passwörter stimmen nicht überein",
        name_exists: "Name des Geheimnisses existiert bereit",
        name_secret_empty: "Leerer Name oder Geheimnis",
        name_empty: "Leerer Name",
        name_nonexistent: "Name existiert nicht",
        unauthenticated: "Ungültige Anmeldedaten",
        invalid_secret: "Ungültiges Geheimnis",
        help: "Hilfe",
        help_qa: {
            download: {
                q: "Herunterladen",
                a: "Du kannst Etopa direkt im <a href=\"../\">Browser</a> oder in der <a href=\"https://play.google.com/store/apps/details?id=de.ltheinrich.etopa\">Android-App</a> nutzen"
            },
            what_is: {
                q: "Was ist Etopa?",
                a: "Etopa ist eine Anwendung zur zeitbasierten Einmal-Passwort Authentifizierung.<br>Es wurde mit Blick auf Einfachheit, Datenschutz und Sicherheit entwickelt.<br>Die Etopa Web-Anwendung oder Android-App generiert 6-stellige Einmal-Passwörter (Token) zur Anmeldung bei Webseiten und Diensten, die 2-Faktor-Authentifizierung (2FA) unterstützen."
            },
            usage: {
                q: "Wie funktioniert Etopa?",
                a: "Zuerst musst du dir ein Benutzerkonto (Account) mit einem einmaligen Benutzernamen, einem Passwort und einem Schlüssel erstellen.<br>Der gewählte Schlüssel wird zur Verschlüsselung der Daten verwendet.<br>Anschließend aktivierst du 2FA beim Dienst oder Webseite, die du mit Etopa nutzen möchtest.<br>Das dabei generierte/erhaltene Geheimnis (z.B. JBSWY3DPEHPK3PXP) musst du nun zusammen mit einem frei gewählten Namen in das Formular nach der Anmeldung eingeben und es hinzufügen.<br>Sobald das Geheimnis hinzugefügt wurde, wird alle 30 Sekunden ein neues Einmal-Passwort (Token) in der Liste generiert.<br>Nun kann dieses Einmal-Passwort zum Anmelden oder zum Verifizieren beim anderen Dienst oder Webseite verwendet werden."
            },
            questions: {
                q: "Noch Fragen?",
                a: "Solltest du dennoch Fragen oder einen Fehler gefunden haben, wende dich bitte an den Entwickler per E-Mail <a href=\"mailto:lennart@ltheinrich.de\">lennart@ltheinrich.de</a> oder über einen <a href=\"https://ltheinrich.de/etopa/issues\">Issue auf GitHub</a>."
            }
        },
        rename: "Umbenennen",
        rename_secret_qm: "Geheimnis \"$name\" umbenennen?",
        new_name_for: "Neuer Name für \"$name\"",
        sure_to_delete: "Geheimnis \"$name\" wirklich löschen?",
        delete_timeout: "5s Timeout zum Löschen",
        edit: "Bearbeiten",
        edit_secret: "Geheimnis bearbeiten",
        no_action_selected: "Keine Aktion ausgewählt",
        repeat_secret_name: "Geheimnis-Namen \"$name\" wiederholen",
        repeat_name_incorrect: "Der wiederholte Name ist inkorrekt",
        sure_to_rename_to: "Geheimnis \"$name\" wirklich in \"$new_name\" umbenennen?"
    }
};
