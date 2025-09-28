package com.aagamshah.jarassignment.data.response

data class OnboardingApiResponse(
    val success: Boolean,
    val data: OnboardingData
)

data class OnboardingData(
    val manualBuyEducationData: ManualBuyEducationResponse
)

data class ManualBuyEducationResponse(
    val toolBarText: String?,
    val introTitle: String?,
    val introSubtitle: String?,
    val educationCardList: List<EducationCardResponse>,
    val saveButtonCta: CtaResponse,
    val ctaLottie: String?,
    val screenType: String?,
    val cohort: String?,
    val combination: String?,
    val collapseCardTiltInterval: Long,
    val collapseExpandIntroInterval: Long,
    val bottomToCenterTranslationInterval: Long,
    val expandCardStayInterval: Long,
    val seenCount: Int?,
    val actionText: String?,
    val shouldShowOnLandingPage: Boolean,
    val toolBarIcon: String?,
    val introSubtitleIcon: String?,
    val shouldShowBeforeNavigating: Boolean
)

data class EducationCardResponse(
    val image: String,
    val collapsedStateText: String,
    val expandStateText: String,
    val backGroundColor: String,
    val strokeStartColor: String,
    val strokeEndColor: String,
    val startGradient: String,
    val endGradient: String
)

data class CtaResponse(
    val text: String?,
    val deeplink: String?,
    val backgroundColor: String?,
    val textColor: String?,
    val strokeColor: String?,
    val icon: String?,
    val order: Int?
)