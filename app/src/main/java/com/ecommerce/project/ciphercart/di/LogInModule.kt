package com.ecommerce.project.ciphercart.di

import android.content.Context
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.repositories.LogInRepository
import com.ecommerce.project.ciphercart.repositories.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object LogInModule {

    @Provides
    fun provideLogInRepository(auth: FirebaseAuth, firestore: FirebaseFirestore):LogInRepository{
        return LogInRepository(FirebaseDb(auth, firestore))
    }
}