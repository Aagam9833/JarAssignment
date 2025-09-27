package com.aagamshah.jarassignment.data.dto

data class OnboardingApiResponse(
    val success: Boolean,
    val data: ResponseData
)

data class ResponseData(
    val manualBuyEducationData: ManualBuyEducationDataDto
)

data class ManualBuyEducationDataDto(
    val toolBarText: String,
    val introTitle: String,
    val introSubtitle: String,
    val educationCardList: List<EducationCardDto>,
    val saveButtonCta: CtaDto,
    val ctaLottie: String,
    val screenType: String,
    val actionText: String?,
    val toolBarIcon: String,
    val introSubtitleIcon: String,
)

data class EducationCardDto(
    val image: String,
    val collapsedStateText: String,
    val expandStateText: String,
    val backGroundColor: String,
    val startGradient: String,
    val endGradient: String,
)

data class CtaDto(
    val text: String,
    val deeplink: String?,
    val backgroundColor: String,
    val textColor: String,
    val strokeColor: String,
)