package pt.iscte.questionengine.model

data class CodeSubmissionModel(var code: String, val user: UserModel, val languageCode: String) {}
