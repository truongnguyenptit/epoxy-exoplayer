package com.airbnb.epoxy.kotlinsample.controller

import android.graphics.Color
import android.util.Log
import android.widget.Toast
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.kotlinsample.MainActivity
import com.airbnb.epoxy.kotlinsample.dataBindingItem
import com.airbnb.epoxy.kotlinsample.models.CarouselItemCustomViewModel_
import com.airbnb.epoxy.kotlinsample.models.ItemDataClass
import com.airbnb.epoxy.kotlinsample.models.PostNewsFeedModel_
import com.airbnb.epoxy.kotlinsample.models.itemCustomView
import com.airbnb.epoxy.kotlinsample.models.itemEpoxyHolder
import com.airbnb.epoxy.kotlinsample.models.postNewsFeed
import com.airbnb.epoxy.kotlinsample.views.carouselNoSnap
import com.airbnb.epoxy.toro.CacheManager

class NewsFeedController : TypedEpoxyController<List<Any>>(), CacheManager {

    var dataSet = mutableListOf<Any>()

    override fun getKeyForOrder(order: Int): Any? {
        return dataSet[order]
    }

    override fun getOrderForKey(key: Any): Int? {
        return if (key is Any) dataSet.indexOf(key) else null
    }

    override fun buildModels(data: List<Any>) {
//        val viewModel = PostViewModel2()
        dataSet.clear()
        dataSet.addAll(data)

//        data?.forEach {
////            viewModel.bind(it, PostViewModel.SCREEN_NEWSFEED)
//            PostNewsFeedModel_().post(it).id(it.id).addTo(this)
////            PostItem3BindingModel_().viewModel(viewModel).id(it.id).addTo(this)
//        }


        for (i in 0 until data.size) {
            dataBindingItem {
                id("data binding $i")
                text("this is a data binding model")
                onClick { _ ->
//                    Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG).show()
                }
                onVisibilityStateChanged { model, view, visibilityState ->
//                    Log.d(MainActivity.TAG, "$model -> $visibilityState")
                }
            }

            itemCustomView {
                id("custom view $i")
                color(Color.GREEN)
                title("this is a green custom view item")
                listener { _ ->
//                    Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG).show()
                }
            }

            itemEpoxyHolder {
                id("view holder $i")
                title("this is a View Holder item")
                listener {
//                    Toast.makeText(this@MainActivity, "clicked", Toast.LENGTH_LONG)
//                        .show()
                }
            }

            postNewsFeed {
                id("postNewsFeed $i")
            }

//                postNewsFeedModel {
//
//                }

            carouselNoSnap {
                id("carousel $i")
                models(mutableListOf<CarouselItemCustomViewModel_>().apply {
                    val lastPage = 10
                    for (j in 0 until lastPage) {
                        add(
                            CarouselItemCustomViewModel_()
                                .id("carousel $i-$j")
                                .title("Page $j / $lastPage")
                        )
                    }
                })
            }

            // Since data classes do not use code generation, there's no extension generated here
            ItemDataClass("this is a Data Class Item")
                .id("data class $i")
                .addTo(this)

        }
    }

}