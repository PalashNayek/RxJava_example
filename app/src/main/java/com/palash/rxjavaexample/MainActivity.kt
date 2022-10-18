package com.palash.rxjavaexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : AppCompatActivity() {
    val myTag: String? = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //simpleObserver()
        createObservable()
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
        val list = listOf<String>("a", "b", "c")

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