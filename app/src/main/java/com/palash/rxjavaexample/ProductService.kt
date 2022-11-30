package com.palash.rxjavaexample

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ProductService {//https://fakestoreapi.com/products
    @GET("/products")
    fun getProducts() : Observable<List<ProductItem>>
}