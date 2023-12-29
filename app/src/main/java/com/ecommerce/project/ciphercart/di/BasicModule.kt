package com.ecommerce.project.ciphercart.di

import android.content.Context
import android.content.SharedPreferences
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.repositories.ProductRepository
import com.ecommerce.project.ciphercart.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object BasicModule {

    @Provides
    fun provideSharedPreferences(@ApplicationContext context:Context) : SharedPreferences{
        return  context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

//    @Provides
//    fun provideProductRepository():ProductRepository{
//        return ProductRepository(FirebaseDb(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance()))
//    }
    @Singleton
    @Provides
    fun provideFirebaseDB(auth: FirebaseAuth, firestore: FirebaseFirestore): FirebaseDb {
        return FirebaseDb(auth, firestore)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}