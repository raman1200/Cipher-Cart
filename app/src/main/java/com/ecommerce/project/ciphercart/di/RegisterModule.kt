package com.ecommerce.project.ciphercart.di

import android.content.Context
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.repositories.RegisterRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object RegisterModule {

    @Provides
    fun provideRegisterRepository(@ApplicationContext context: Context, auth: FirebaseAuth, firestore: FirebaseFirestore):RegisterRepository {
         return RegisterRepository(context, FirebaseDb(auth, firestore))
    }
}