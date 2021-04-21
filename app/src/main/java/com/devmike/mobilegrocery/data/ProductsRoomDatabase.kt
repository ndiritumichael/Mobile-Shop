package com.devmike.mobilegrocery.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.devmike.mobilegrocery.R
import com.devmike.mobilegrocery.models.OrdersItem
import com.devmike.mobilegrocery.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@Database(entities = [Product::class, OrdersItem::class], version = 1, exportSchema = false)
abstract class ProductsRoomDatabase : RoomDatabase() {
    abstract fun productsDao(): ProductsDao

    companion object {
        @Volatile
        private var INSTANCE: ProductsRoomDatabase? = null
        fun getDatabase(context: Context): ProductsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductsRoomDatabase::class.java,
                    "shopDatabase"
                ).fallbackToDestructiveMigration()
                    .addCallback(ShopRoomCallBack())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }


    private class ShopRoomCallBack : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { RoomDb ->
                GlobalScope.launch(Dispatchers.IO) {
                    populateProducts(RoomDb.productsDao())

                }

            }
        }

        private suspend fun populateProducts(productsDao: ProductsDao) {

              val  banana =
                    "The banana is an anytime fruit of the year as a snack, into your cereals, pancakes, fruit salad & smoothies. Loaded with potassium, good for blood sugar control & digestive health"
               val Kales = """
           Sukuma wiki
           
           Excellent source of Vit E, A, K & C. Can be steamed, used raw, in salads or juiced
           """.trimIndent()
                val  Cabbages =
                    "An all time favourite, firmly packed the green cabbage is loaded with vitamins and antioxidants. Perfect when steamed, fried or raw in salads"
                val Carrots =
                    "High in Vit C, K & Potassuim, our carrots are colourful, crunchy and sweet. Perfect to snack on, cook and juice"
                val   oranges =
                    "Oranges have a significant amount of Vit C & potassium. Very low in acid and perfect for juices, salads and smoothies. Embu county currently has plenty of these in season. Prevents kidney stones and anemia\n"
                val  Onions =
                    "Onions add crunch & pinch to sandwiches, salads and burgers. A cooking favourite for flavour. Has strong anti-inflammatory properties benefiting heart health and cancer fighting compounds"
                val  Spinach =
                    "Farmed widely due to its impressive nutrient profile, spinach is rich in antioxidants and may reduce risk of chronic disease. Use raw, in smoothies and juices"
                val  Sweet_peppers =
                    "Great source of Vit B6 & folate. Often referred to as sweet due flavourful taste. Good in salads to bring out the gorgeous colours"
                val  apples = """
           These are now coming from Nyeri, very sweet and great for both fruit and juice
           
           Local organic apples
           """.trimIndent()
                val   pineapples =
                    "Available all year from our Ugandan farmers, this bold fruit stands out in both sweet and savoury dishes. Very effective in fighting inflammation."
                val product1 = Product(name = "Collard-Greens", pricePerUnit = 50.00,image =  R.drawable.kales, description = Kales)
                val product2 = Product(name = "Bananas",pricePerUnit =  120.00,image =  R.drawable.banana, description = banana)
                val product3 = Product(name = "Cabbage",pricePerUnit =  90.00, image = R.drawable.cabbage,description =  Cabbages)
                val product4 = Product(name ="Carrots", pricePerUnit = 50.00,image =  R.drawable.carrots,description =  Carrots)
                val product5 = Product(name = "Oranges",pricePerUnit =  100.00,image =  R.drawable.oranges, description = oranges)
                val product6 =
                    Product(name = "Sweet-Pepper", pricePerUnit = 100.00, image = R.drawable.pepper,description =  Sweet_peppers)
                val product7 = Product(name = "Onions",pricePerUnit =  100.00,image =  R.drawable.onions,description =  Onions)
                val product8 = Product(name = "Apples", pricePerUnit = 200.00, image = R.drawable.apples, description = apples)
                val product9 =
                    Product(name = "Pineapples",pricePerUnit =  100.00, image = R.drawable.pineapples,description =  pineapples)
                val product10 = Product(name = "Spinach", pricePerUnit = 100.00,image =  R.drawable.spinach, description = Spinach)
           val     productsList =
                    listOf(
                        product1,
                        product2,
                        product3,
                        product4,
                        product5,
                        product6,
                        product7,
                        product8,
                        product9,
                        product10
                    )
                productsList.forEach {product ->
productsDao.insertProducts(product)
                }


        }

    }
}
