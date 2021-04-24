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

              val  RNP8P = "Xiaomi Redmi Note 8 Pro as a phablet features 6.53 inch display afford you a vivid and different visual experience. 5 cameras, 64.0MP + 8.0MP + 2.0MP + 2.0MP back camera and 20.0MP front camera, you can enjoy images with 2340 x 1080 high resolution. It comes with most of the features we've come to expect from a phablet, including 6GB RAM 128GB internal storage equipped with MIUI 10 Helio G90T and 4500mAh big capacity battery that you can play games faster."
                   val  iphone = "The phone is powered by the new Apple A14 Bionic processor. The smartphone comes with a 6.7 inches Super Retina XDR OLED capacitive touchscreen, 2778 x 1284 pixels resolution and HDR display, True Tone and Wide color (P3) gamut.\n" +
                       "\n" +
                       "Additionally, the screen of the device is protected by a unique ceramic shield.  Along with this, the screen features 1200 nits max brightness (advertised), Dolby Vision, HDR10+, Wide color gamut, True-tone, and 120Hz refresh rate.\n" +
                       "\n" +
                       "The rear camera consists of a 12 MP (wide) + 12 MP (telephoto) 5x optical zoom + 12 MP (ultrawide) and also a LiDAR scanner for night mode.\n" +
                       "\n" +
                       "The front camera has a 12 MP (wide) + SL 3D (depth/biometrics sensor) camera sensor. The phone’s sensors include Lidar, Face ID, accelerometer, gyro, proximity, compass, barometer + Siri natural language commands, and dictation.\n" +
                       "\n" +
                       "The smartphone is fueled by a non-removable Li-Ion battery + Fast charging 20W+ USB Power Delivery 2.0 + 7W Qi wireless charging and 15W MagSafe Wireless Charging. The phone runs on iOS 14 operating system."
                val  Samsung21 =
                    "Samsung Galaxy S21 Ultra 5G has a 6.8-inch touchscreen display. A 2.9GHz Octa-core Exynos 2100 processor runs Samsung Galaxy S21 Ultra 5G. Samsung Galaxy S21 Ultra 5G has a 12GB RAM and 256GB of internal storage. Samsung Galaxy S21 Ultra 5G has a 108MP + 10MP + 10MP + 12MP Quad Rear Camera and a 40MP Front camera for selfies."
                val Oneplus9 =
                    "The OnePlus 9 still feels solidly built and the plastic frame does look like metal, even though it's not. The alert slider and buttons have good tactile feedback, similar to the ones on the 9 Pro. The OnePlus 9 has a similar 6.55-inch AMOLED display as the 8T. Software is taken care of by OxygenOS 11, which is based on Android 11"
                val  Mi11ultra =
                    "Xiaomi Mi 11 Ultra smartphone was launched on 29th March 2021. The phone comes with a 6.80-inch touchscreen primary display with a resolution of 1440x3200 pixels at a pixel density of 515 pixels per inch (ppi) and an aspect ratio of 20:9. It also features a 1.10-inch touchscreen as its second display, with a resolution of 126x294 pixels."
                val  pocof3 =
                    "Poco F3 smartphone was launched on 22nd March 2021. The phone comes with a 6.67-inch touchscreen display and an aspect ratio of 20:9. Poco F3 is powered by a 3.2GHz octa-core Qualcomm Snapdragon 870 processor. It comes with 6GB of RAM. The Poco F3 runs Android 11 and is powered by a 4520mAh battery. The Poco F3 supports proprietary fast charging"
                 val infix =   "The dual-SIM (Nano) Infinix Hot 10 runs XOS 7.0 on top of Android 10. The phone features a 6.78-inch HD+ (720x1,640 pixels) IPS display with a hole-punch cut out for the selfie camera. Under the hood, it is powered by the MediaTek Helio G70 processor with up to 3GB of RAM."
                val  Velvet =
                    "LG Velvet (Snapdragon 845) Summary\n" +
                            "LG Velvet (Snapdragon 845) smartphone was launched on 28th October 2020. The phone comes with a 6.80-inch touchscreen primary display with a resolution of 1080x2460 pixels at a pixel density of 395 pixels per inch (ppi) and an aspect ratio of 20.5:9"
                val  Findx3 = "The phone comes with a 6.70-inch touchscreen display with a resolution of 1440x3216 pixels at a pixel density of 525 pixels per inch (ppi). Oppo Find X3 Pro is powered by a 2.8GHz octa-core Qualcomm Snapdragon 888 processor that features 1 core clocked at 2.8GHz, 3 cores clocked at 2.4GHz and 4 cores clocked at 1.7GHz."
                val   xs = "Apple iPhone XS smartphone was launched in September 2018. The phone comes with a 5.80-inch touchscreen display with a resolution of 1125x2436 pixels at a pixel density of 458 pixels per inch (ppi). Apple iPhone XS is powered by a hexa-core Apple A12 Bionic processor."

                val product1 = Product(name = "Iphone 12", pricePerUnit = 1099.00,image = "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-12.jpg", description = iphone)
                val product2 = Product(name = "Redmi Note 8 Pro",pricePerUnit =  220.00,image =  "https://fdn2.gsmarena.com/vv/bigpic/xiaomi-redmi-note-8-pro-.jpg", description = RNP8P)
                val product3 = Product(name = "Samsung S21",pricePerUnit =  300.00, image = "https://fdn2.gsmarena.com/vv/bigpic/samsung-galaxy-s21-ultra-5g-.jpg",description =  Samsung21)
                val product4 = Product(name ="Oneplus 9", pricePerUnit = 600.00,image =  "https://fdn2.gsmarena.com/vv/bigpic/oneplus-9-.jpg",description =  Oneplus9)
                val product5 = Product(name = "Xiaomi Mi 11 Ultra",pricePerUnit =  1000.00,image =  "https://fdn2.gsmarena.com/vv/bigpic/xiaomi-mi11-ultra-5g-k1.jpg", description = Mi11ultra)
                val product6 =
                    Product(name = "Poco F3", pricePerUnit = 200.00, image = "https://fdn2.gsmarena.com/vv/bigpic/xiaomi-poco-f3.jpg",description =  pocof3)
                val product7 = Product(name = "Infinix",pricePerUnit =  50.00,image =  "https://fdn2.gsmarena.com/vv/bigpic/infinix-note8-.jpg",description =  infix)
                val product8 = Product(name = "LG Velvet", pricePerUnit = 400.00, image = "https://fdn2.gsmarena.com/vv/bigpic/lg-velvet-.jpg", description = Velvet)
                val product9 =
                    Product(name = "Iphone XS ",pricePerUnit =  450.00, image = "https://fdn2.gsmarena.com/vv/bigpic/apple-iphone-xs-new.jpg",description =  xs)
                val product10 = Product(name = "Oppo Find x3 Pro", pricePerUnit = 190.00,image =  "https://fdn2.gsmarena.com/vv/bigpic/oppo-find-x3-pro.jpg", description = Findx3)
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
