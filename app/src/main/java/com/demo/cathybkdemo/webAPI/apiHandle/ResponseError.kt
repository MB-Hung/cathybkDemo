package com.demo.cathybkdemo.webAPI.apiHandle

class ResponseError : ErrorAble {

    /**
     * error : {"code":99999,"message":"Authentication is required"}
     */

    lateinit var error: ErrorBean

    override val errorCode: String
        get() = error.code

    class ErrorBean {
        /**
         * code : 99999
         * message : Authentication is required
         */

        lateinit var code: String
        lateinit var message: String
    }
}
