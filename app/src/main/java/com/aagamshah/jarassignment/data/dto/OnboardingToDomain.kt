// OnboardingManualBuyMapperDTO.kt (Mapper for the new domain structure)

package com.aagamshah.jarassignment.data.dto

import com.aagamshah.jarassignment.data.response.CtaResponse
import com.aagamshah.jarassignment.data.response.EducationCardResponse
import com.aagamshah.jarassignment.data.response.OnboardingApiResponse
import com.aagamshah.jarassignment.domain.model.CtaData
import com.aagamshah.jarassignment.domain.model.EducationCardData
import com.aagamshah.jarassignment.domain.model.OnboardingModel


fun OnboardingApiResponse.toDomain(): OnboardingModel {
    val responseData = this.data.manualBuyEducationData

    return OnboardingModel(
        toolBarText = responseData.toolBarText,
        introTitle = responseData.introTitle,
        introSubtitle = responseData.introSubtitle,
        educationCardList = responseData.educationCardList.map { it.toDomain() },
        saveButtonCta = responseData.saveButtonCta.toDomain(),
        ctaLottie = responseData.ctaLottie,
        screenType = responseData.screenType,
        cohort = responseData.cohort,
        combination = responseData.combination,
        collapseCardTiltInterval = responseData.collapseCardTiltInterval,
        collapseExpandIntroInterval = responseData.collapseExpandIntroInterval,
        bottomToCenterTranslationInterval = responseData.bottomToCenterTranslationInterval,
        expandCardStayInterval = responseData.expandCardStayInterval,
        seenCount = responseData.seenCount,
        actionText = responseData.actionText,
        shouldShowOnLandingPage = responseData.shouldShowOnLandingPage,
        toolBarIcon = responseData.toolBarIcon,
        introSubtitleIcon = responseData.introSubtitleIcon,
        shouldShowBeforeNavigating = responseData.shouldShowBeforeNavigating
    )
}

fun EducationCardResponse.toDomain(): EducationCardData {
    return EducationCardData(
        image = this.image,
        collapsedStateText = this.collapsedStateText,
        expandStateText = this.expandStateText,
        backGroundColor = this.backGroundColor,
        strokeStartColor = this.strokeStartColor,
        strokeEndColor = this.strokeEndColor,
        startGradient = this.startGradient,
        endGradient = this.endGradient
    )
}

fun CtaResponse.toDomain(): CtaData {
    return CtaData(
        text = this.text,
        deeplink = this.deeplink,
        backgroundColor = this.backgroundColor,
        textColor = this.textColor,
        strokeColor = this.strokeColor,
        icon = this.icon,
        order = this.order
    )
}