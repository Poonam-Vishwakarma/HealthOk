package com.example.healthok.data.model

class Message(
    var message:String,
    var sendBy:String

) {


    companion object{
        var SEND_BY_ME = "me"
        var SEND_BY_BOT ="bot"
    }
    }