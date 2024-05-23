package com.vcreate.healthok.ui.main.user.support

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthok.adapters.MessageAdapter
import com.example.healthok.data.model.Message
import com.vcreate.healthok.R
import com.vcreate.healthok.utils.Utility
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException



class SupportFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var welcomeText : TextView
    lateinit var messageEditText: EditText
    lateinit var sendButton: ImageButton
    lateinit var messageList:MutableList<Message>
    lateinit var messageAdapter: MessageAdapter
    val client = OkHttpClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = layoutInflater.inflate(R.layout.fragment_support,container,false)
        messageList = ArrayList()
        recyclerView = view.findViewById(R.id.treatment_recycler_view)
        welcomeText = view.findViewById(R.id.welcome_text)
        messageEditText = view.findViewById(R.id.message_edit_text)
        sendButton = view.findViewById(R.id.send_btn)
        messageAdapter = MessageAdapter(messageList)
        recyclerView.adapter = messageAdapter
        val layoutManger = LinearLayoutManager(requireContext())
        layoutManger.stackFromEnd = true
        recyclerView.layoutManager = layoutManger


        sendButton.setOnClickListener {
            val question = messageEditText.text.toString().trim{ it <= ' '}
            addToChat(question, Message.SEND_BY_ME)
            messageEditText.setText("")
            callAPI(question)
            welcomeText.visibility = View.GONE
        }

        return view
    }

    private fun addToChat(message: String, sentBy: String) {
        requireActivity().runOnUiThread{
            messageList.add(Message(message,sentBy))
            messageAdapter.notifyDataSetChanged()
            recyclerView.smoothScrollToPosition(messageAdapter.itemCount)
        }

    }

    fun addResponse(response:String?){
        messageList.removeAt(messageList.size -1)
        addToChat(response!!, Message.SEND_BY_BOT)

    }

    private fun callAPI(question: String) {
        //call okhttp
        messageList.add(Message("Typing...", Message.SEND_BY_BOT))
        val jsonBody = JSONObject()
        try {
            jsonBody.put("model", "gpt-3.5-turbo-instruct")
            jsonBody.put("prompt", question)
            jsonBody.put("max_tokens", 4000)
            jsonBody.put("temperature", 0)
        }catch (e: JSONException){
            Log.d("myTag", e.message+"\n" +
                    e.localizedMessage + "\n" +
                    e.localizedMessage
            )
            e.printStackTrace()
        }
        val body : RequestBody = RequestBody.create(JSON,jsonBody.toString())
        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer ${Utility.API_KEY}")
            .post(body)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (!responseBody.isNullOrEmpty()) {
                        Log.d("myTag", "Response Body: $responseBody")
                        try {
                            val jsonObject = JSONObject(responseBody)
                            val jsonArray = jsonObject.getJSONArray("choices")
                            val result = jsonArray.getJSONObject(0).getString("text")
                            addResponse(result.trim())
                        } catch (e: JSONException) {
                            Log.d("myTag", "JSONException: ${e.message}")
                            e.printStackTrace()
                        }
                    } else {
                        addResponse("Empty response body")
                    }
                } else {
                    addResponse("Failed to load response due to ${response.code}")
                    Log.d("myTag", "Failed to load response due to ${response.code}: ${response.message}")
                }
            }


        })

    }
    companion object {
        val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
    }
}