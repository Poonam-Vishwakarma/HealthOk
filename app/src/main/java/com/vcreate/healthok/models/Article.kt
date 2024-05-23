package com.vcreate.healthok.models

import com.example.healthok.data.model.Author
import java.util.Date

data class Article(
     val articleId: Long = 0,
     val articleName: String =  "",
     val articleImage: String = "",
     val articleContent: String  = "",
     val articleDate: String  = "",
     val authorName: String  = ""
)
