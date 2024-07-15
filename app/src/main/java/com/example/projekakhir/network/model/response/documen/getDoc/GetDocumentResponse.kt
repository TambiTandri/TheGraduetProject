package com.example.projekakhir.network.model.response.documen.getDoc

import com.google.gson.annotations.SerializedName

data class GetDocumentResponse(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Data(

	@field:SerializedName("birth_certificate")
	val birthCertificate: String,

	@field:SerializedName("competency_certificate")
	val competencyCertificate: String,

	@field:SerializedName("id_card")
	val idCard: String,

	@field:SerializedName("graduation_certificate")
	val graduationCertificate: String,

	@field:SerializedName("photo")
	val photo: String,

	@field:SerializedName("toeic_certificate")
	val toeicCertificate: String,

	@field:SerializedName("student_card")
	val studentCard: String,

	@field:SerializedName("article")
	val article: String,

	@field:SerializedName("validity_sheet")
	val validitySheet: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("thesis_file")
	val thesisFile: String,

	@field:SerializedName("family_card")
	val familyCard: String,

	@field:SerializedName("temp_graduation_certificate")
	val tempGraduationCertificate: String,

	@field:SerializedName("id_student")
	val idStudent: String
)
