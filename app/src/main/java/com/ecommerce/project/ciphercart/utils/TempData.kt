package com.ecommerce.project.ciphercart.utils

import android.util.Log
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.model.SplOfferData
import com.ecommerce.project.ciphercart.utils.Constants.Companion.CATEGORIES_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.PRODUCTS_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.SPL_OFFERS_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlin.math.log

class TempData {
    val catList = listOf<CategoryData>(
        CategoryData(1, "Shirt", "https://cdn-icons-png.flaticon.com/128/121/121863.png", "A Very Good Shirt"),
        CategoryData(2,"Saree","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Fsaree.png?alt=media&token=0fcca04a-f4ec-49f4-b0df-166cb06a496f","Fabuluous design Saree"),
        CategoryData(3, "Pants", "https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Ftrousers%20(1).png?alt=media&token=58d212fa-64bf-4829-9bd6-afd1839e8ceb", "Durable and Long lasting fabric"),
        CategoryData(4, "Laptops","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Flaptop.png?alt=media&token=9c32cc88-1bda-445a-a4f9-4665af6b1994","Ultra Hd display and Long lasting battery"),
        CategoryData(5,"SmartPhone","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Fsmartphone.png?alt=media&token=03032d3a-f5f4-43e1-88e4-e880360e80bc","Lightweight and cheaper"),
        CategoryData(6,"Appliances","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Felectric-appliance.png?alt=media&token=11f8019a-a196-47b8-84d3-54f47218c937","Big size and Guranteed"),
        CategoryData(7,"Furniture","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Ffurniture.png?alt=media&token=0eddc286-11ff-4ace-b11e-036e3fe841ba","Heavy quality material"),
        CategoryData(8,"Women Kurta","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/category%2Fkurta.png?alt=media&token=befd0603-3dd5-4b11-b903-2da41bae7767","Good Design and Good Fabric")
    )  
    val prodList = listOf<ProductData>(
        ProductData(10,"Men Regular Fit Solid Button Down Collar Formal Shirt",1,1, listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F10%2Fshirt1.png?alt=media&token=b0741c1b-5a35-4689-b484-42d5be9726cf","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F10%2Fshirt2.webp?alt=media&token=1bebe236-35b0-450d-a5cf-a2df38704226"),"This shirt is a versatile option appropriate for daily office wear or formal occasions such as workplace parties. It will let you enter the realm of sophistication. This classic style oozes sophistication and ensures users stand out without effort. The shirt has a covert pocket for extra convenience that is ideal for storing necessities like your wallet or phone. This considerate feature makes sure you can easily reach your equipment while looking professional.",380.0,0.0,3.5,false,false),
        ProductData(11,"Embroidered Bollywood Net, Art Silk Saree",2,1, listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F11%2Fsaree1.png?alt=media&token=846e89ac-2927-4ac8-b88a-fe972c2b0b0b","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F11%2Ffree-lycra-tzo-b-bella-creation-unstitched-original-imag5hwkqdjzngvc.png?alt=media&token=b73099b6-215d-4dc5-95f1-552f0a6fe598"),"Wow amazing \uD83E\uDD29 beautiful saree and quality ❤️❤️❤️",1200.0,100.0,4.0,false,false),
        ProductData(12,"Men Slim Fit Beige Polyester Trousers",3,2, listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F12%2Fpant2%2Cpng.webp?alt=media&token=d5757c86-f224-4d05-aa3c-a5bc564c66f1","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F12%2Fpant.png?alt=media&token=b00bb040-1f3c-4484-b599-b15759497150"),"Owing to their slim-fit design, the COMBRAIDED Men’s Slim-fit Trousers provide you with a modern and stylish look. The trousers hug your legs and give you a sleek silhouette, making them your go-to choice for any formal as well as informal occasion. Moreover, the belt loops on these trousers make it easy to wear a belt and keep them in place. This is especially useful if you are wearing a shirt that needs to be tucked in. Moreover, the belt loops also add a touch of style to the trousers.",1350.0,200.0,4.5,false,false),
        ProductData(13,"HP Celeron Dual Core N4500 - (8 GB/512 GB SSD/Windows 11 Home) 15s- fq3066TU Thin and Light Laptop  (15.6 inch, Jet Black, 1.69 Kg, With MS Office)",4,5,
            listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F13%2Flaptop1.png?alt=media&token=6a3a43e3-9e6b-49e7-a6a7-40a1007051c3","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F13%2Flaptop2.png?alt=media&token=81c21dac-e950-45b2-816e-fc7e44855d2b"),"Heavy duty laptop",25990.0,1200.0,4.5,false,false
        ),
        ProductData(14,"vivo T2x 5G (Glimmer Black, 128 GB)  (8 GB RAM)",5,2, listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F14%2Fphone.png?alt=media&token=60868516-80dd-4f1b-8de0-7baedab1dc25","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F14%2Fphone2.png?alt=media&token=7fd21f6d-4f61-4b9b-bd2e-8cfe6bee783c"),"With the superb Vivo T2x 5G, you can take advantage of great pictures and a flawless user experience. With the Vivo T2x 5G, you can experience exceptional performance owing to its 7 nm 5G CPU, the octa-core Dimensity 6020, with a top clock speed of 2.2 GHz. Additionally, the 50 MP main camera on this smartphone beautifully catches every detail you see. Additionally, Super Night Selfie employs noise cancellation technology in conjunction with an Aura Screen Light to produce a calming light that is effective in low light. The pioneering Extended RAM 3.0 technology also uses ROM to expand RAM with a maximum capacity of 8 GB. This enables smooth app switching and allows up to 27 active applications to run in the background.",14999.0,500.0,5.0,false,false),
        ProductData(15,"PowerChef Premium 500 W Mixer Grinder",6,2, listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F15%2Fgrinder.png?alt=media&token=c95c17fd-4021-4903-aa61-a5e8b36556c4","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F15%2Fgrinder1.png?alt=media&token=87625291-6976-4263-92c9-90a2bcbf0742"), "Whip up chutneys, grind masalas and prepare yummy shakes effortlessly with the Flipkart Smartbuy Classico 500W Mixer Grinder. This mixer grinder, with its multipurpose jars, makes cooking convenient, less time consuming and fun. It also comes with a powerful 500W overload protected motor which is designed to offer a durable performance.",1249.0,100.0,3.5,false,false),
        ProductData(16,"Homes Phonox Engineered Wood TV Entertainment Unit  (Finish Color - Chocolate Wenge, Knock Down)",7,5,
            listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F16%2Ffun.png?alt=media&token=4a4bbaa7-6f68-4ef4-a41f-08fe33fa5a27","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F16%2Ffun2.png?alt=media&token=e9056c47-7cad-471d-8c65-2c2113f03682"),"Number of Drawers: 0, Number of Open Shelves: 6, Number of Closed Shelves: 4",4499.0,0.0,4.5,false,false
        ),
        ProductData(17,"Women Printed Viscose Rayon Ethnic Dress ",8,10, listOf("https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F17%2Fkurta1.png?alt=media&token=6a53b27f-7194-42fe-8678-4df3ef3e2fe7","https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Product%2F17%2Fkurta3.png?alt=media&token=768f8d39-9dab-4bd1-8013-0591baf05bf3"),"Owing to its beautiful mirror work on the neck, the Jaishree Fab Women’s Printed Ethnic Dress exudes an air of elegance and sophistication. Moreover, the intricate mirror work on the neckline is a traditional Indian embroidery technique known as shisha embroidery, which involves attaching small mirrors or reflective pieces onto the fabric to create a dazzling effect. Furthermore, this kurta also has a round neck with a yoke and block print, which adds to its overall charm. Additionally, the print on the borders of this kurta is particularly eye-catching and adds a pop of colour to the outfit. Not to mention, the minimal print on the rest of this kurta gives it a balanced and sophisticated look. Lastly, the two side slits on this kurta make it easy to move around in, while the calf-length design offers a touch of elegance and coverage.",420.0,0.0,4.6,false,false),

    )

    val splOfferList = listOf<SplOfferData>(
        SplOfferData(1, "https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Banner%2FWhatsApp%20Image%202023-12-04%20at%2022.28.18_12e3d830.jpg?alt=media&token=86cb7e2f-b854-412b-aa6e-bdcb2a44d702","5/12/2023","1/1/2024"),
        SplOfferData(2,"https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Banner%2FWhatsApp%20Image%202023-12-04%20at%2022.28.18_38c7f9de.jpg?alt=media&token=a1d6f760-873a-4862-8ee2-5a8874802887","5/12/2023","1/1/2024"),
        SplOfferData(3,"https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Banner%2FWhatsApp%20Image%202023-12-04%20at%2022.28.19_1a2a28c7.jpg?alt=media&token=ac7c4c79-9baa-4641-aaff-2e2565cb20f7","5/12/2023","1/1/2024"),
        SplOfferData(4,"https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Banner%2FWhatsApp%20Image%202023-12-04%20at%2022.28.19_08d3a938.jpg?alt=media&token=c23b6466-6a38-44ed-9b3d-b9e233e1f6db","5/12/2023","1/1/2024"),
        SplOfferData(5,"https://firebasestorage.googleapis.com/v0/b/cipher-cart.appspot.com/o/Banner%2F1.png?alt=media&token=fef5f729-a40e-474b-bd9a-d7cb1fbff8c6","5/12/2023","1/1/2024")
    )

    fun uploadCategory(){
        val fb = FirebaseFirestore.getInstance()
        for(cat in catList)
            fb.collection(CATEGORIES_COLLECTION).document().set(cat)
    }
    fun uploadProduct(){
        val fb = FirebaseFirestore.getInstance()
        for(prod in prodList)
            fb.collection(PRODUCTS_COLLECTION).document().set(prod)
    }
    fun uploadOffer() {
        val fb = FirebaseFirestore.getInstance()
        for(offer in splOfferList)
            fb.collection(SPL_OFFERS_COLLECTION).document().set(offer).addOnCompleteListener {
                if(it.isSuccessful)
                    Log.e("TAG", "success")
                else
                    Log.e("TAG", it.exception!!.localizedMessage)
            }
    }

}