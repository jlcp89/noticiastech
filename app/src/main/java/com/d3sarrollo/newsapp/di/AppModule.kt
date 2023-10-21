package com.d3sarrollo.newsapp.di

import android.app.Application
import androidx.room.Room
import com.d3sarrollo.newsapp.data.local.NewsDao
import com.d3sarrollo.newsapp.data.local.NewsDatabase
import com.d3sarrollo.newsapp.data.local.NewsTypeConvertor
import com.d3sarrollo.newsapp.data.manger.LocalUserMangerImpl
import com.d3sarrollo.newsapp.data.remote.NewsApi
import com.d3sarrollo.newsapp.data.repository.NewsRepositoryImpl
import com.d3sarrollo.newsapp.domain.manger.LocalUserManger
import com.d3sarrollo.newsapp.domain.repository.NewsRepository
import com.d3sarrollo.newsapp.domain.usecases.app_entry.AppEntryUseCases
import com.d3sarrollo.newsapp.domain.usecases.app_entry.ReadAppEntry
import com.d3sarrollo.newsapp.domain.usecases.app_entry.SaveAppEntry
import com.d3sarrollo.newsapp.domain.usecases.news.DeleteArticle
import com.d3sarrollo.newsapp.domain.usecases.news.GetArticle
import com.d3sarrollo.newsapp.domain.usecases.news.GetArticles
import com.d3sarrollo.newsapp.domain.usecases.news.GetNews
import com.d3sarrollo.newsapp.domain.usecases.news.NewsUseCases
import com.d3sarrollo.newsapp.domain.usecases.news.SearchNews
import com.d3sarrollo.newsapp.domain.usecases.news.UpsertArticle
import com.d3sarrollo.newsapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManger = LocalUserMangerImpl(context = application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManger: LocalUserManger,
    ): AppEntryUseCases = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManger),
        saveAppEntry = SaveAppEntry(localUserManger),
    )

    @Provides
    @Singleton
    fun provideApiInstance(): NewsApi {
        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): NewsRepository {
        return NewsRepositoryImpl(newsApi,newsDao)
    }

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository,
        newsDao: NewsDao
    ): NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository),
            searchNews = SearchNews(newsRepository),
            upsertArticle = UpsertArticle(newsDao),
            deleteArticle = DeleteArticle(newsDao),
            getArticles = GetArticles(newsDao),
            getArticle = GetArticle(newsDao)
        )
    }

    @Provides
    @Singleton
    fun provideNewsDatabase(
        application: Application
    ): NewsDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = NewsDatabase::class.java,
            name = "news_db"
        ).addTypeConverter(NewsTypeConvertor())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNewsDao(
        newsDatabase: NewsDatabase
    ): NewsDao = newsDatabase.newsDao

}