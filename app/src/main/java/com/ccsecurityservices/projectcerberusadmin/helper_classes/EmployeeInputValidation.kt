package com.ccsecurityservices.projectcerberusadmin.helper_classes

import java.util.regex.Pattern

class EmployeeInputValidation {
    companion object {
        private const val PHONE_REGEX =
            "^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$"

        private const val FIRST_NAME_REGEX = "[A-ZñÑ][a-zA-Z]*"

        private const val LAST_NAME_REGEX = "[a-zA-zñÑ]+([ '-][a-zA-ZñÑ]+)*"

        private const val EMAIL_REGEX = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

        fun inputValidation(
            firstName: String,
            lastName: String,
            phone: String,
            email: String
        ): Boolean {
            return validateFirstName(firstName) && validateLastName(lastName)
                    && validatePhone(phone) && validateEmail(email)
        }

        private fun validateFirstName(name: String): Boolean {
            if (name.isEmpty()) {
                return false
            }
            return name.matches(FIRST_NAME_REGEX.toRegex())
        }

        private fun validateLastName(name: String): Boolean {
            if (name.isEmpty()) {
                return false
            }
            return name.matches(LAST_NAME_REGEX.toRegex())
        }

        private fun validateEmail(email: String): Boolean {
            if (email.isEmpty()) {
                return false
            }
            return email.matches(EMAIL_REGEX.toRegex())
        }

        private fun validatePhone(phone: String): Boolean {
            if (phone.isEmpty()) {
                return false
            }
            val pattern = Pattern.compile(PHONE_REGEX)
            val matcher = pattern.matcher(phone.trim())
            return matcher.find()
        }
    }
}