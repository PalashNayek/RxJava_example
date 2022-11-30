package com.palash.rxjavaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val myTag: String? = "MainActivity"
    private lateinit var btnLoad: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnLoad = findViewById(R.id.btnLoad)
        btnLoad.clicks().subscribe {
            implementNetworkCalls()
        }

        //simpleObserver()
        //createObservable()
    }

    private fun implementNetworkCalls(){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        val productService = retrofit.create(ProductService::class.java)
        productService.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("Hello", "${it.toString()}")
            }
    }

    private fun createObservable() {

        val observable = Observable.create<String> {
            it.onNext("One")
            it.onError(IllegalArgumentException("Error in Observable"))
            it.onNext("Two")
            it.onComplete()
        }

        observable.subscribe(object : Observer<String>{
            override fun onSubscribe(d: Disposable) {
                Log.d(myTag,"onSubscribe call")
            }

            override fun onNext(t: String) {
                Log.d(myTag,"onNext call $t")
            }

            override fun onError(e: Throwable) {
                Log.d(myTag,"onError call ${e.message}")
            }

            override fun onComplete() {
                Log.d(myTag,"onComplete call")
            }

        })
    }

    private fun simpleObserver() {
        val list = listOf("a", "b", "c")

        val observable = Observable.fromIterable(list)

        observable.subscribe(object : Observer<String>{

            override fun onSubscribe(d: Disposable) {
                Log.d(myTag,"onSubscribe $d")
            }

            override fun onNext(t: String) {
                Log.d(myTag,"onNext $t")
            }

            override fun onError(e: Throwable) {
                Log.d(myTag,"onError")
            }

            override fun onComplete() {
                Log.d(myTag, "onComplete")
            }

        })
    }
}