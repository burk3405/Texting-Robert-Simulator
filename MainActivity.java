package com.example.robert;

import android.os.Bundle;

import android.text.TextUtils;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EditText editTextMessage;
    private Button buttonSend;
    private MessageAdapter messageAdapter;
    private List<Message> messages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        recyclerView = findViewById(R.id.recyclerView);
        EditText editTextMessage = findViewById(R.id.editTextMessage);
        Button buttonSend = findViewById(R.id.buttonSend);

        editTextMessage.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                // Perform the button's click action
                buttonSend.performClick();
                return true; // Indicate that the action was handled
            }
            return false; // Let the system handle other actions
        });

        // Initialize message list and adapter
        messages = new ArrayList<>();
        messageAdapter = new MessageAdapter(messages);

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);

        // Set up button click listener
        buttonSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Add user message to the RecyclerView
                messages.add(new Message(messageText, true));
                messageAdapter.notifyItemInserted(messages.size() - 1);
                recyclerView.scrollToPosition(messages.size() - 1);

                // Clear the input field
                editTextMessage.setText("");

                // Simulate bot response
                simulateResponse();
            }
        });
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (TextUtils.isEmpty(messageText)) {
            return; // Don't send empty messages
        }

        // Add user's message to the list
        messages.add(new Message(messageText, true));
        messageAdapter.notifyItemInserted(messages.size() - 1);
        recyclerView.scrollToPosition(messages.size() - 1);

        // Clear the input field
        editTextMessage.setText("");

        // Simulate a response from the app
        simulateResponse();
    }

    private void simulateResponse() {
        String response = getSimulatedResponse();

        // Delay the response for a better user experience
        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                messages.add(new Message(response, false));
                messageAdapter.notifyItemInserted(messages.size() - 1);
                recyclerView.scrollToPosition(messages.size() - 1);
            }
        }, 1000); // 1-second delay
    }

    private String getSimulatedResponse() {
        String userLastMessage = messages.get(messages.size() - 1).getText().toLowerCase();

        // Custom logic for responses
        if (userLastMessage.contains("hello") || userLastMessage.contains("hi") || userLastMessage.contains("hey")) {
            return "Yeah ok pal";
        } else if (userLastMessage.contains("how are you")) {
            return "Im in hawaii and ur not";
        } else if (userLastMessage.contains("bye")) {
            return "Yeah ok";
        } else if (userLastMessage.contains("real")) {
            return "Real";
        } else if (userLastMessage.contains("you should")) {
            return "Skill Issue";
        } else if (userLastMessage.contains("kill yourself")) {
            return "L";
        } else if (userLastMessage.contains("witowy")) {
            return "Witowwwy my shoulder hurts";
        } else if (userLastMessage.contains("yeah okay")) {
            return "Yeah sure";
        } else {
            // Random response for unmatched messages
            String[] randomResponses = {
                    "Real",
                    "Witowy",
                    "Skill Issue",
                    "Kevin Harvick",
                    "Bruh",
                    "Yeah ok bud",
                    "Me frfr",
                    "Smh my head"
            };
            return randomResponses[new Random().nextInt(randomResponses.length)];
        }
    }

}