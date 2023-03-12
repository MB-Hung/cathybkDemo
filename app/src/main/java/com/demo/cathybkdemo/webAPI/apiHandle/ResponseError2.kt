package com.demo.cathybkdemo.webAPI.apiHandle

class ResponseError2 : ErrorAble {

    /**
     * timestamp : 1502428835654
     * status : 500
     * error : Internal Server Error
     * exception : org.springframework.security.access.AccessDeniedException
     * message : bad token: 6830a2bb-7dc0-11e7-bd04-0242ac120005
     * path : /api/users/phoneAuthCode
     */

    var timestamp: Long = 0
    lateinit var status: String
    var error: String? = null
    var exception: String? = null
    lateinit var message: String
    var path: String? = null

    override val errorCode: String
        get() = if (message.startsWith("bad token")) {
            "99999"
        } else {
            status
        }
}