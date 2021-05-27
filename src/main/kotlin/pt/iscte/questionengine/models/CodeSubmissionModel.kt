package pt.iscte.questionengine.models

data class CodeSubmissionModel(var code: String, val user: UserModel, val languageCode: String) {}
