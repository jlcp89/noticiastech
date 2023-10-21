package com.d3sarrollo.newsapp.domain.usecases.app_entry

import com.d3sarrollo.newsapp.domain.manger.LocalUserManger

class SaveAppEntry(
    private val localUserManger: LocalUserManger
) {

    suspend operator fun invoke(){
        localUserManger.saveAppEntry()
    }

}